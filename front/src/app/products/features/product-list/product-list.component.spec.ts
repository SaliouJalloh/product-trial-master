import { ComponentFixture, TestBed } from "@angular/core/testing";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { ProductListComponent } from "./product-list.component";
import { ProductsService } from "../../data-access/products.service";
import { CartService } from "../../data-access/cart.service";
import { MessageService } from "primeng/api";
import { of } from "rxjs";
import { signal } from "@angular/core";

describe("ProductListComponent", () => {
  let component: ProductListComponent;
  let fixture: ComponentFixture<ProductListComponent>;
  let productsService: jasmine.SpyObj<ProductsService>;
  let cartService: jasmine.SpyObj<CartService>;

  beforeEach(async () => {
    const productsServiceSpy = jasmine.createSpyObj(
      "ProductsService",
      ["get", "save", "delete"],
      {
        products: signal([]),
      },
    );
    const cartServiceSpy = jasmine.createSpyObj(
      "CartService",
      ["addToCart", "getTotalItems", "getTotalPrice"],
      {
        cartItems: signal([]),
      },
    );

    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
        ProductListComponent,
      ],
      providers: [
        { provide: ProductsService, useValue: productsServiceSpy },
        { provide: CartService, useValue: cartServiceSpy },
        MessageService,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductListComponent);
    component = fixture.componentInstance;
    productsService = TestBed.inject(
      ProductsService,
    ) as jasmine.SpyObj<ProductsService>;
    cartService = TestBed.inject(CartService) as jasmine.SpyObj<CartService>;

    // Mock the get method to return an empty array
    productsService.get.and.returnValue(of([]));
    cartService.getTotalItems.and.returnValue(0);
    cartService.getTotalPrice.and.returnValue(0);

    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it("should load products on init", () => {
    expect(productsService.get).toHaveBeenCalled();
  });
});
