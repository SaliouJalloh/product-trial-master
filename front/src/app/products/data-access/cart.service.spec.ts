import { TestBed } from "@angular/core/testing";
import { CartService } from "./cart.service";
import { ProductsService } from "./products.service";

describe("CartService", () => {
  let service: CartService;

  beforeEach(() => {
    const productsServiceSpy = jasmine.createSpyObj("ProductsService", [], {
      productDeleted$: { subscribe: () => {} },
    });

    TestBed.configureTestingModule({
      providers: [
        CartService,
        { provide: ProductsService, useValue: productsServiceSpy },
      ],
    });
    service = TestBed.inject(CartService);
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });

  it("should have cartItems signal initialized", () => {
    expect(service.cartItems).toBeDefined();
  });

  it("should have getTotalItems method", () => {
    expect(service.getTotalItems).toBeDefined();
  });

  it("should have getTotalPrice method", () => {
    expect(service.getTotalPrice).toBeDefined();
  });
});
