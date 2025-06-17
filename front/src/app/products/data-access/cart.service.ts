import { Injectable, signal } from "@angular/core";
import { Product } from "./product.model";
import { CartItem } from "./cart.model";
import { ProductsService } from "./products.service";

@Injectable({
  providedIn: "root",
})
export class CartService {
  private readonly _cartItems = signal<CartItem[]>([]);
  public readonly cartItems = this._cartItems.asReadonly();

  constructor(private productsService: ProductsService) {
    this.productsService.productDeleted$.subscribe((deletedProductId) => {
      this._cartItems.set(
        this._cartItems().filter(
          (item) => item.product.id !== deletedProductId,
        ),
      );
    });
  }

  public addToCart(product: Product): void {
    const currentItems = this._cartItems();
    const existingItem = currentItems.find(
      (item) => item.product.id === product.id,
    );

    if (existingItem) {
      this.updateQuantity(product, existingItem.quantity + 1);
    } else {
      this._cartItems.set([...currentItems, { product, quantity: 1 }]);
    }
  }

  public removeFromCart(product: Product): void {
    const currentItems = this._cartItems();
    this._cartItems.set(
      currentItems.filter((item) => item.product.id !== product.id),
    );
  }

  public updateQuantity(product: Product, quantity: number): void {
    if (quantity <= 0) {
      this.removeFromCart(product);
      return;
    }

    const currentItems = this._cartItems();
    this._cartItems.set(
      currentItems.map((item) =>
        item.product.id === product.id ? { ...item, quantity } : item,
      ),
    );
  }

  public getTotalItems(): number {
    return this._cartItems().reduce((total, item) => total + item.quantity, 0);
  }

  public getTotalPrice(): number {
    return this._cartItems().reduce(
      (total, item) => total + item.product.price * item.quantity,
      0,
    );
  }

  public clearCart(): void {
    this._cartItems.set([]);
  }
}
