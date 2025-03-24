import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CategoryService } from '../services/category.service';
import { Category, CreateCategoryRequest } from '../models/category.model';
import { ModalComponent } from './productlist/modal.component';
import { AuthService } from '../authorization/auth.service';

@Component({
  selector: 'app-categories',
  imports: [CommonModule, FormsModule, ModalComponent],
  templateUrl: './categories.component.html',
  styleUrl: './categories.component.css'
})
export class CategoriesComponent implements OnInit {

  categories: Category[] = [];

  isModalVisible: boolean = false;
  selectedCategoryName: string = '';

  categoryRequest: CreateCategoryRequest = {
    name: ''
  };
  errorMessage = '';

  constructor(
    private categoryService: CategoryService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadCategories();

    this.categoryService.categoriesUpdated$.subscribe(() => {
      this.loadCategories();
    });
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe((data: Category[]) => {
      this.categories = data;
    });
  }

  confirmCreateCategory(): void {
    this.categoryService.addCategory(this.categoryRequest).subscribe({
      next: (response) => {
        console.log('Category created:', response);
      },
      error: (err) => {
        this.showError(err.error.message);
        console.error('Error during creating category:', err);
      },
    });
    this.clearAddCategoryFormInput();
  }

  clearAddCategoryFormInput(): void {
    this.categoryRequest.name = '';
  }

  removeCategory(categoryId: string) {
    this.categoryService.removeCategory(categoryId).subscribe({
      next: (response) => {
        console.log('Category removed:', response);
      },
      error: (err) => {
        this.showError(err.error.message);
        console.error('Error during removing category:', err);
      },
    });
  }

  showCategoryProducts(name: string): void {
    console.log(name)
    this.selectedCategoryName = name;
    this.isModalVisible = true;
  }

  closeModal(): void {
    this.isModalVisible = false;
  }

  showError(message: string): void {
    this.errorMessage = message;
    setTimeout(() => {
      this.errorMessage = '';
    }, 5000);
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }
}
