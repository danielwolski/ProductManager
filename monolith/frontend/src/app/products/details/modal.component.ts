import { Component, Input, Output, EventEmitter, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductDetails, UpdateProductRequest } from '../../models/product.model';
import { ProductService } from '../../services/product.service';
import { FormsModule } from '@angular/forms';
import { Category } from '../../models/category.model';
import { CategoryService } from '../../services/category.service';
import { AuthService } from '../../authorization/auth.service';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
  imports: [
    CommonModule, FormsModule
  ],
  standalone: true
})
export class ModalComponent implements OnInit, OnChanges {
  @Input() isVisible: boolean = false; 
  @Input() selectedProductId: string = '';  
  @Output() closeModal: EventEmitter<void> = new EventEmitter(); 

  productDetails: ProductDetails = {
    id: '',
    name: '',
    description: '',
    price: '',
    categoryName: ''
  };

  editedProduct: ProductDetails = { ...this.productDetails };
  categories: Category[] = [];
  isEditing: boolean = false;

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
      this.loadCategories();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['isVisible'] && changes['isVisible'].currentValue === true) {
      this.loadProductDetails();
    }
  }

  close(): void {
    this.closeModal.emit();
  }

  loadProductDetails(): void {
    this.productService.getProductDetails(this.selectedProductId)?.subscribe((data: ProductDetails) => {
      this.productDetails = data;
      this.editedProduct = { ...data };
    });
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe((data: Category[]) => {
      this.categories = data;
    });
  }

  toggleEdit(): void {
    this.isEditing = !this.isEditing;
    this.editedProduct = { ...this.productDetails };
  }

  saveChanges(): void {
    const updateRequest: UpdateProductRequest = {
      id: this.productDetails.id,
      name: this.editedProduct.name,
      description: this.editedProduct.description,
      price: this.editedProduct.price,
      categoryName: this.editedProduct.categoryName
    };

    this.productService.updateProduct(updateRequest).subscribe(() => {
      this.productDetails = { ...this.editedProduct };
      this.isEditing = false;
    });
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }
}
