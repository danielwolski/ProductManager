import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, tap } from 'rxjs';
import { Product, ProductDetails, CreateProductRequest, UpdateProductRequest } from '../models/product.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProductService {

  private apiUrl = `${environment.apiUrl}/api/v1/products`;

  private productsUpdatedSubject = new Subject<void>();

  productsUpdated$ = this.productsUpdatedSubject.asObservable();

  constructor(private http: HttpClient) {}

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }

  getProductsByCategory(categoryName: string): Observable<Product[]> {
    const url = `${this.apiUrl}/category/${categoryName}`;
    return this.http.get<Product[]>(url);
  }

  getProductDetails(productId: string): Observable<ProductDetails> {
    const url = `${this.apiUrl}/${productId}`;
    return this.http.get<ProductDetails>(url);
  }

  addProduct(request: CreateProductRequest): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, request).pipe(
      tap(() => this.productsUpdatedSubject.next())
    );
  }

  updateProduct(request: UpdateProductRequest): Observable<Product> {
    const url = `${this.apiUrl}/${request.id}`;
    return this.http.put<Product>(url, request).pipe(
      tap(() => this.productsUpdatedSubject.next())
    );
  }

  removeProduct(productId: string): Observable<void> {
    const url = `${this.apiUrl}/${productId}`;
    return this.http.delete<void>(url).pipe(
      tap(() => this.productsUpdatedSubject.next())
    );
  } 
}
