import { TestBed } from "@angular/core/testing";
import { HttpRequest, HttpHandlerFn, HttpEvent } from "@angular/common/http";
import { of } from "rxjs";
import { errorInterceptor } from "./error.interceptor";
import { MessageService } from "primeng/api";

describe("ErrorInterceptor", () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MessageService],
    });
  });

  it("should handle request without error", () => {
    TestBed.runInInjectionContext(() => {
      const request = new HttpRequest("GET", "/api/test");
      const handler: HttpHandlerFn = () => of({} as HttpEvent<unknown>);

      errorInterceptor(request, handler).subscribe(
        (event: HttpEvent<unknown>) => {
          expect(event).toBeDefined();
        },
      );
    });
  });
});
