import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../models/product.model';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
  imports: [
    CommonModule
  ],
  standalone: true
})
export class ModalComponent implements OnChanges {
  @Input() isVisible: boolean = false; 
  @Input() selectedCategoryName: string = '';  
  @Output() closeModal: EventEmitter<void> = new EventEmitter(); 

  categoryProducts: Product[] = [];
  isAddEventFormVisible: boolean = false;

  constructor(private productService: ProductService
  ) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['isVisible'] && changes['isVisible'].currentValue === true) {
      this.loadProductsByCategory();
    }
  }

  close(): void {
    this.closeModal.emit();
  }

  loadProductsByCategory(): void {
    console.log(this.selectedCategoryName)
    this.productService.getProductsByCategory(this.selectedCategoryName)?.subscribe((data: Product[]) => {
      this.categoryProducts = data;
    });
  }
}
