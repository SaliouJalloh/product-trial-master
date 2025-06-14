import { Component } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { ToastModule } from "primeng/toast";
import { MessageService } from "primeng/api";
import { SplitterModule } from "primeng/splitter";
import { ToolbarModule } from "primeng/toolbar";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [
    RouterOutlet,
    PanelMenuComponent,
    ToastModule,
    SplitterModule,
    ToolbarModule,
  ],
  providers: [MessageService],
})
export class AppComponent {
  title = "altenshop";
}
