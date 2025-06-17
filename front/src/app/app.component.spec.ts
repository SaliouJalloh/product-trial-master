import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { AppComponent } from "./app.component";
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { ToastModule } from "primeng/toast";
import { SplitterModule } from "primeng/splitter";
import { ToolbarModule } from "primeng/toolbar";
import { MessageService } from "primeng/api";

describe("AppComponent", () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        PanelMenuComponent,
        ToastModule,
        SplitterModule,
        ToolbarModule,
      ],
      providers: [MessageService],
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it('should have title "altenshop"', () => {
    expect(component.title).toEqual("altenshop");
  });
});
