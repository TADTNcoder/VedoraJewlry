package com.jewelry.jewelryshopbackend.service;

import com.jewelry.jewelryshopbackend.dto.request.category.CreateCategoryRequest;
import com.jewelry.jewelryshopbackend.dto.request.category.UpdateCategoryRequest;
import com.jewelry.jewelryshopbackend.dto.response.category.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id, UpdateCategoryRequest request);

    void deleteCategory(Long id);
}