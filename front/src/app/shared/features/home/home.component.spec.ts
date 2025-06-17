import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { HomeComponent } from "./home.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";

describe("HomeComponent", () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HomeComponent, ButtonModule, CardModule],
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it('should have appTitle "ALTEN SHOP"', () => {
    expect(component.appTitle).toEqual("ALTEN SHOP");
  });
});
