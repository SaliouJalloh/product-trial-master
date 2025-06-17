import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { RegisterComponent } from "./register.component";
import { AuthService } from "../../data-access/auth.service";
import { MessageService } from "primeng/api";

describe("RegisterComponent", () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async () => {
    const authServiceSpy = jasmine.createSpyObj("AuthService", ["register"]);

    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, RegisterComponent],
      providers: [
        { provide: AuthService, useValue: authServiceSpy },
        MessageService,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
