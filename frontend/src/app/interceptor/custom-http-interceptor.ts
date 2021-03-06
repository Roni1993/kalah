import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomHttpInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Clone the request to add the new header
    const clonedRequest = req.clone({ headers: 
      req.headers.set('Access-Control-Allow-Origin', environment.basePath)
                 .set('Access-Control-Allow-Credentials','true') });
    
    // Pass the cloned request instead of the original request to the next handle
    return next.handle(clonedRequest);
  }
}