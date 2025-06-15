import { Component, inject, OnInit, signal, computed } from "@angular/core";
import { CommonModule, CurrencyPipe } from "@angular/common";
import { RouterLink } from "@angular/router";
import { DataViewModule } from "primeng/dataview";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DialogModule } from "primeng/dialog";
import { BadgeModule } from "primeng/badge";
import { ToastModule } from "primeng/toast";
import { MessageService, SelectItem } from "primeng/api";
import { ProductFormComponent } from "../../ui/product-form/product-form.component";
import { Product } from "../../data-access/product.model";
import { ProductsService } from "../../data-access/products.service";
import { CartService } from "../../data-access/cart.service";
import { InputNumberModule } from "primeng/inputnumber";
import { InputTextModule } from "primeng/inputtext";
import { DropdownModule } from "primeng/dropdown";
import { FormsModule } from "@angular/forms";
import { PaginatorModule } from "primeng/paginator";

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
    InputNumberModule,
    InputTextModule,
    DropdownModule,
    FormsModule,
    PaginatorModule,
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

  protected filterText = signal<string>("");
  protected filterCategory = signal<string | null>(null);

  protected first = signal<number>(0); // Current page start index
  protected rows = signal<number>(5); // Number of items per page

  protected readonly categories: SelectItem[] = [
    { value: "Accessories", label: "Accessories" },
    { value: "Fitness", label: "Fitness" },
    { value: "Clothing", label: "Clothing" },
    { value: "Electronics", label: "Electronics" },
  ];

  protected filteredProducts = computed(() => {
    let products = this.products() || []; // Ensure products is always an array
    const text = this.filterText().toLowerCase();
    const category = this.filterCategory();

    if (text) {
      products = products.filter(
        (product) =>
          product.name.toLowerCase().includes(text) ||
          product.description.toLowerCase().includes(text)
      );
    }

    if (category) {
      products = products.filter((product) => product.category === category);
    }

    // Apply pagination
    const first = this.first();
    const rows = this.rows();
    return products.slice(first, first + rows);
  });

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

  // New method to handle quantity changes directly from the list
  protected onProductQuantityChange(
    product: Product,
    newQuantity: number
  ): void {
    if (newQuantity < 0) {
      newQuantity = 0; // Prevent negative quantities
    }
    // Create a copy of the product with the updated quantity
    const updatedProduct = { ...product, quantity: newQuantity };

    // Save the updated product via the service
    this.productsService.save(updatedProduct).subscribe({
      next: () => {
        this.messageService.add({
          severity: "success",
          summary: "Succès",
          detail: `Quantité de ${product.name} mise à jour avec succès.`,
        });
      },
      error: (error) => {
        console.error("Erreur lors de la mise à jour de la quantité:", error);
        this.messageService.add({
          severity: "error",
          summary: "Erreur",
          detail: `Impossible de mettre à jour la quantité de ${product.name}.`,
        });
      },
    });
  }

  protected onPageChange(event: { first?: number; rows?: number }): void {
    this.first.set(event.first ?? 0);
    this.rows.set(event.rows ?? 5);
  }

  protected applyFilters(): void {
    // The `filteredProducts` computed signal will automatically react to changes in `filterText` and `filterCategory`
    // No explicit action needed here, but the method is called to trigger change detection if needed for more complex scenarios.
    // Also, reset first to 0 when filters change to go back to the first page of results.
    this.first.set(0);
  }
}
