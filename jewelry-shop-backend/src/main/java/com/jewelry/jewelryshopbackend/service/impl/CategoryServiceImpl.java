package com.jewelry.jewelryshopbackend.service.impl;

import com.jewelry.jewelryshopbackend.dto.request.category.CreateCategoryRequest;
import com.jewelry.jewelryshopbackend.dto.request.category.UpdateCategoryRequest;
import com.jewelry.jewelryshopbackend.dto.response.category.CategoryResponse;
import com.jewelry.jewelryshopbackend.entity.Category;
import com.jewelry.jewelryshopbackend.exception.BadRequestException;
import com.jewelry.jewelryshopbackend.exception.ResourceNotFoundException;
import com.jewelry.jewelryshopbackend.repository.CategoryRepository;
import com.jewelry.jewelryshopbackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        String name = request.getName().trim();
        String slug = request.getSlug().trim().toLowerCase();

        if (categoryRepository.existsByName(name)) {
            throw new BadRequestException("Category name already exists");
        }

        if (categoryRepository.existsBySlug(slug)) {
            throw new BadRequestException("Category slug already exists");
        }

        Category category = Category.builder()
                .name(name)
                .slug(slug)
                .description(request.getDescription())
                .status(true)
                .build();

        Category savedCategory = categoryRepository.save(category);
        return mapToResponse(savedCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAllByStatusTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = findCategoryById(id);
        return mapToResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = findCategoryById(id);

        String name = request.getName().trim();
        String slug = request.getSlug().trim().toLowerCase();

        if (categoryRepository.existsByNameAndIdNot(name, id)) {
            throw new BadRequestException("Category name already exists");
        }

        if (categoryRepository.existsBySlugAndIdNot(slug, id)) {
            throw new BadRequestException("Category slug already exists");
        }

        category.setName(name);
        category.setSlug(slug);
        category.setDescription(request.getDescription());

        if (request.getStatus() != null) {
            category.setStatus(request.getStatus());
        }

        Category updatedCategory = categoryRepository.save(category);
        return mapToResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = findCategoryById(id);

        // Soft delete
        category.setStatus(false);
        categoryRepository.save(category);
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    private CategoryResponse mapToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .status(category.getStatus())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}