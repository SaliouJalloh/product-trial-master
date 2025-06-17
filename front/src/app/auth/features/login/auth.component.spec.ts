import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { AuthComponent } from "./auth.component";
import { AuthService } from "../../data-access/auth.service";
import { MessageService } from "primeng/api";

describe("AuthComponent", () => {
  let component: AuthComponent;
  let fixture: ComponentFixture<AuthComponent>;

  beforeEach(async () => {
    const authServiceSpy = jasmine.createSpyObj("AuthService", ["login"]);

    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, AuthComponent],
      providers: [
        { provide: AuthService, useValue: authServiceSpy },
        MessageService,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AuthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
