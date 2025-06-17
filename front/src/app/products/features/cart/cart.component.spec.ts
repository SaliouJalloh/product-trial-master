import { ComponentFixture, TestBed } from "@angular/core/testing";
import { CartComponent } from "./cart.component";
import { CartService } from "../../data-access/cart.service";
import { MessageService } from "primeng/api";
import { signal } from "@angular/core";

describe("CartComponent", () => {
  let component: CartComponent;
  let fixture: ComponentFixture<CartComponent>;
  let cartService: jasmine.SpyObj<CartService>;

  beforeEach(async () => {
    const cartServiceSpy = jasmine.createSpyObj(
      "CartService",
      [
        "removeFromCart",
        "updateQuantity",
        "getTotalItems",
        "getTotalPrice",
        "clearCart",
      ],
      {
        cartItems: signal([]),
      },
    );

    await TestBed.configureTestingModule({
      imports: [CartComponent],
      providers: [
        { provide: CartService, useValue: cartServiceSpy },
        MessageService,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(CartComponent);
    component = fixture.componentInstance;
    cartService = TestBed.inject(CartService) as jasmine.SpyObj<CartService>;

    cartService.getTotalItems.and.returnValue(0);
    cartService.getTotalPrice.and.returnValue(0);

    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
