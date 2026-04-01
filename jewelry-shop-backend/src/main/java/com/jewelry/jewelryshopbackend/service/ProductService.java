package com.jewelry.jewelryshopbackend.service;


import com.jewelry.jewelryshopbackend.dto.request.product.CreateProductRequest;
import com.jewelry.jewelryshopbackend.dto.request.product.UpdateProductRequest;
import com.jewelry.jewelryshopbackend.dto.response.product.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, UpdateProductRequest request);

    void deleteProduct(Long id);
}