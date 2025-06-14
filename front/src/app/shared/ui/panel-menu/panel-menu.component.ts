import { Component } from "@angular/core";
import { MenuItem } from "primeng/api";
import { PanelMenuModule } from "primeng/panelmenu";

@Component({
  selector: "app-panel-menu",
  standalone: true,
  imports: [PanelMenuModule],
  template: ` <p-panelMenu [model]="items" styleClass="w-full" /> `,
})
export class PanelMenuComponent {
  public readonly items: MenuItem[] = [
    {
      label: "Accueil",
      icon: "pi pi-home",
      routerLink: ["/home"],
    },
    {
      label: "Connexion",
      icon: "pi pi-sign-in",
      routerLink: ["/auth"],
    },
    {
      label: "Produits",
      icon: "pi pi-barcode",
      routerLink: ["/products/list"],
    },
    {
      label: "Contact",
      icon: "pi pi-envelope",
      routerLink: ["/contact"],
    },
  ];
}
