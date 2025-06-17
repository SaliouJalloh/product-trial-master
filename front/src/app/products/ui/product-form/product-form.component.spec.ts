import { ComponentFixture, TestBed } from "@angular/core/testing";
import { ProductFormComponent } from "./product-form.component";
import { MessageService } from "primeng/api";

describe("ProductFormComponent", () => {
  let component: ProductFormComponent;
  let fixture: ComponentFixture<ProductFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductFormComponent],
      providers: [MessageService],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
