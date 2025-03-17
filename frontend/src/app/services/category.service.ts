import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { Category, CreateCategoryRequest } from '../models/category.model';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private apiUrl = `${environment.apiUrl}/api/v1/categories`;

  private categoriesUpdatedSubject = new Subject<void>();

  categoriesUpdated$ = this.categoriesUpdatedSubject.asObservable();

  constructor(private http: HttpClient) {}

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.apiUrl);
  }

  addCategory(request: CreateCategoryRequest): Observable<Category> {
      return this.http.post<Category>(this.apiUrl, request).pipe(
        tap(() => this.categoriesUpdatedSubject.next())
      );
    }

  removeCategory(categoryId: string): Observable<void> {
    const url = `${this.apiUrl}/${categoryId}`;
    return this.http.delete<void>(url).pipe(
      tap(() => this.categoriesUpdatedSubject.next())
    );
  } 
}
