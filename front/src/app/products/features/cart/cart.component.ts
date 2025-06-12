import { Component, inject } from "@angular/core";
import { CommonModule, CurrencyPipe } from "@angular/common";
import { ButtonModule } from "primeng/button";
import { CartService } from "../../data-access/cart.service";
import { CartItem } from "../../data-access/cart.model";

@Component({
  selector: "app-cart",
  standalone: true,
  imports: [CommonModule, ButtonModule, CurrencyPipe],
  templateUrl: "./cart.component.html",
  styleUrls: ["./cart.component.scss"],
})
export class CartComponent {
  protected readonly cartService = inject(CartService);

  protected updateQuantity(item: CartItem, change: number): void {
    this.cartService.updateQuantity(item.product, item.quantity + change);
  }

  protected removeItem(item: CartItem): void {
    this.cartService.removeFromCart(item.product);
  }

  protected clearCart(): void {
    this.cartService.clearCart();
  }
}
