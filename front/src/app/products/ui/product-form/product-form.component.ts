import {
  Component,
  EventEmitter,
  input,
  OnChanges,
  Output,
  signal,
  SimpleChanges,
  ViewEncapsulation,
} from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Product } from "app/products/data-access/product.model";
import { SelectItem } from "primeng/api";
import { ButtonModule } from "primeng/button";
import { DropdownModule } from "primeng/dropdown";
import { InputNumberModule } from "primeng/inputnumber";
import { InputTextModule } from "primeng/inputtext";
import { InputTextareaModule } from "primeng/inputtextarea";

@Component({
  selector: "app-product-form",
  templateUrl: "./product-form.component.html",
  styleUrls: ["./product-form.component.scss"],
  standalone: true,
  imports: [
    FormsModule,
    ButtonModule,
    InputTextModule,
    InputNumberModule,
    InputTextareaModule,
    DropdownModule,
  ],
  encapsulation: ViewEncapsulation.None,
})
export class ProductFormComponent implements OnChanges {
  public readonly product = input.required<Product>();

  @Output() productCancel = new EventEmitter<void>();
  @Output() productSave = new EventEmitter<Product>();

  public productForm = signal<Product>({} as Product);

  ngOnChanges(changes: SimpleChanges): void {
    if (changes["product"] && this.product()) {
      this.productForm.set({
        ...this.product(),
        id: this.product().id, // Explicitly ensure ID is copied
      });
    }
  }

  public readonly categories: SelectItem[] = [
    { value: "Accessories", label: "Accessories" },
    { value: "Fitness", label: "Fitness" },
    { value: "Clothing", label: "Clothing" },
    { value: "Electronics", label: "Electronics" },
  ];

  onProductFormChange(field: keyof Product, value: string | number) {
    this.productForm.update((currentProduct) => {
      return { ...currentProduct, [field]: value };
    });
  }

  onCancel() {
    this.productCancel.emit();
  }

  onSave() {
    this.productSave.emit(this.productForm());
  }
}
