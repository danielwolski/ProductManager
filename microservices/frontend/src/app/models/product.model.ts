export interface Product {
    id: string;
    name: string;
    price: string
    categoryName: string;
}

export interface ProductDetails {
    id: string;
    name: string;
    description: string;
    price: string
    categoryName: string;
}

export interface UpdateProductRequest {
    id: string;
    name: string;
    description: string;
    price: string
    categoryName: string;
}

export interface CreateProductRequest {
    name: string;
    description: string;
    price: string
    categoryName: string;
}
  