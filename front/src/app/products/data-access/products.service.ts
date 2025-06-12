import { Injectable, signal } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, tap, Subject } from "rxjs";
import { Product } from "./product.model";

@Injectable({
  providedIn: "root",
})
export class ProductsService {
  private readonly _products = signal<Product[]>([]);
  private readonly baseUrl = "http://localhost:8085/api/v1/products";
  private readonly _productDeleted = new Subject<number>();

  public readonly products = this._products.asReadonly();
  public readonly productDeleted$ = this._productDeleted.asObservable();

  constructor(private readonly http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem("token");
    const headers = new HttpHeaders({
      "Content-Type": "application/json",
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
    });
    console.log("Headers envoyés:", headers.keys());
    return headers;
  }

  public get(): Observable<Product[]> {
    return this.http
      .get<Product[]>(this.baseUrl, { headers: this.getHeaders() })
      .pipe(tap((products) => this._products.set(products)));
  }

  public save(product: Product): Observable<Product> {
    const headers = this.getHeaders();
    const now = new Date().toISOString();
    const productToSave = {
      ...product,
      updatedAt: now,
      ...(product.id ? {} : { createdAt: now }),
    };

    const isUpdate = product.id != null && product.id > 0;
    const url = isUpdate ? `${this.baseUrl}/${product.id}` : this.baseUrl;

    console.log("URL de la requête:", url);
    console.log("Méthode:", isUpdate ? "PATCH" : "POST");
    console.log("Données envoyées:", productToSave);

    const request = isUpdate
      ? this.http.patch<Product>(url, productToSave, { headers })
      : this.http.post<Product>(url, productToSave, { headers });

    return request.pipe(
      tap({
        next: (savedProduct) => {
          console.log("Réponse du serveur:", savedProduct);
          const currentProducts = this._products();
          if (!isUpdate) {
            this._products.set([...currentProducts, savedProduct]);
          } else {
            this._products.set(
              currentProducts.map((p) =>
                p.id === savedProduct.id ? savedProduct : p
              )
            );
          }
        },
        error: (error) => {
          console.error("Erreur détaillée:", {
            status: error.status,
            statusText: error.statusText,
            error: error.error,
            headers: error.headers,
            url: error.url,
          });
        },
      })
    );
  }

  public delete(product: Product): Observable<void> {
    return this.http
      .delete<void>(`${this.baseUrl}/${product.id}`, {
        headers: this.getHeaders(),
      })
      .pipe(
        tap(() => {
          const currentProducts = this._products();
          this._products.set(
            currentProducts.filter((p) => p.id !== product.id)
          );
          this._productDeleted.next(product.id!);
        })
      );
  }
}
