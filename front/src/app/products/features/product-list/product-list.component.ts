import { Component, inject, OnInit, signal } from "@angular/core";
import { CommonModule, CurrencyPipe } from "@angular/common";
import { RouterLink } from "@angular/router";
import { DataViewModule } from "primeng/dataview";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DialogModule } from "primeng/dialog";
import { BadgeModule } from "primeng/badge";
import { ToastModule } from "primeng/toast";
import { MessageService } from "primeng/api";
import { ProductFormComponent } from "../../ui/product-form/product-form.component";
import { Product } from "../../data-access/product.model";
import { ProductsService } from "../../data-access/products.service";
import { CartService } from "../../data-access/cart.service";

@Component({
  selector: "app-product-list",
  standalone: true,
  imports: [
    CommonModule,
    DataViewModule,
    ButtonModule,
    CardModule,
    DialogModule,
    ProductFormComponent,
    BadgeModule,
    RouterLink,
    CurrencyPipe,
    ToastModule,
  ],
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  providers: [MessageService],
})
export class ProductListComponent implements OnInit {
  protected readonly productsService = inject(ProductsService);
  protected readonly cartService = inject(CartService);
  protected readonly messageService = inject(MessageService);
  protected products = this.productsService.products;
  protected isDialogVisible = false;
  protected editedProduct = signal<Product | null>(null); // Local signal for edited product

  ngOnInit(): void {
    this.loadProducts();
  }

  private loadProducts(): void {
    this.productsService.get().subscribe({
      error: (error) => {
        console.error("Erreur lors du chargement des produits:", error);
        this.messageService.add({
          severity: "error",
          summary: "Erreur",
          detail: "Impossible de charger les produits",
        });
      },
    });
  }

  protected onCreate(): void {
    const now = new Date().toISOString();
    this.editedProduct.set({
      code: "PROD-" + Date.now(),
      name: "",
      description: "",
      image: "https://via.placeholder.com/150",
      category: "",
      price: 0,
      quantity: 0,
      internalReference: "REF-" + Date.now(),
      shellId: 1,
      inventoryStatus: "INSTOCK",
      rating: 0,
      createdAt: now,
      updatedAt: now,
    });
    this.isDialogVisible = true;
  }

  protected onUpdate(product: Product): void {
    console.log(
      "ProductListComponent: Product received in onUpdate - ID:",
      product.id
    );
    console.log(
      "ProductListComponent: Product received in onUpdate - Object:",
      product
    );
    this.editedProduct.set({ ...product }); // Set existing product for editing
    this.isDialogVisible = true;
  }

  protected onDelete(product: Product): void {
    this.productsService.delete(product).subscribe({
      error: (error) => {
        console.error("Erreur lors de la suppression:", error);
        this.messageService.add({
          severity: "error",
          summary: "Erreur",
          detail: "Impossible de supprimer le produit",
        });
      },
    });
  }

  protected onSave(product: Product): void {
    this.productsService.save(product).subscribe({
      next: () => {
        this.messageService.add({
          severity: "success",
          summary: "Succès",
          detail: "Produit enregistré avec succès",
        });
        this.isDialogVisible = false;
        this.editedProduct.set(null); // Clear edited product after save
      },
      error: (error) => {
        console.error("Erreur lors de l'enregistrement:", error);
        this.messageService.add({
          severity: "error",
          summary: "Erreur",
          detail: "Impossible d'enregistrer le produit",
        });
      },
    });
  }

  protected onCancel(): void {
    this.isDialogVisible = false;
    this.editedProduct.set(null); // Clear edited product on cancel
  }

  protected addToCart(product: Product): void {
    this.cartService.addToCart(product);
    this.messageService.add({
      severity: "success",
      summary: "Succès",
      detail: "Produit ajouté au panier",
    });
  }
}
