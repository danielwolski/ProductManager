import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CategoryService } from '../services/category.service';
import { Category, CreateCategoryRequest } from '../models/category.model';
import { CreateProductRequest, Product } from '../models/product.model';
import { ProductService } from '../services/product.service';
import { ModalComponent } from './details/modal.component';
import { AuthService } from '../authorization/auth.service';

@Component({
  selector: 'app-products',
  imports: [CommonModule, FormsModule, ModalComponent],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit {

  products: Product[] = [];
  categories: Category[] = [];

  isModalVisible: boolean = false;
  selectedProductId: string = '';
  errorMessage = '';

  productRequest: CreateProductRequest = {
    name: '',
    description: '',
    price: '',
    categoryName: ''
  };

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadProducts();
    this.loadCategories();

    this.productService.productsUpdated$.subscribe(() => {
      this.loadProducts();
    });
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe((data: Product[]) => {
      this.products = data;
    });
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe((data: Category[]) => {
      this.categories = data;
    });
  }

  confirmCreateProduct(): void {
    this.productService.addProduct(this.productRequest).subscribe({
      next: (response) => {
        console.log('Product created:', response);
      },
      error: (err) => {
        this.showError(err.error.message);
        console.error('Error during creating product:', err);
      },
    });
  }

  removeProduct(productId: string) {
    this.productService.removeProduct(productId).subscribe(() => {});
  }

  openProductDetails(id: string): void {
    this.selectedProductId = id;
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
