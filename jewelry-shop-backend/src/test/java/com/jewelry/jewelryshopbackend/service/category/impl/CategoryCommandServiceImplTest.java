package com.jewelry.jewelryshopbackend.service.category.impl;

import com.jewelry.jewelryshopbackend.dto.request.category.CategoryCreateRequest;
import com.jewelry.jewelryshopbackend.dto.response.category.CategoryResponse;
import com.jewelry.jewelryshopbackend.entity.Category;
import com.jewelry.jewelryshopbackend.enums.CategoryStatus;
import com.jewelry.jewelryshopbackend.exception.BadRequestException;
import com.jewelry.jewelryshopbackend.mapper.CategoryMapper;
import com.jewelry.jewelryshopbackend.repository.CategoryRepository;
import com.jewelry.jewelryshopbackend.validator.CategoryValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryCommandServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryValidator categoryValidator;

    @InjectMocks
    private CategoryCommandServiceImpl categoryCommandService;

    @Test
    void create_shouldNormalizeAndSave() {
        CategoryCreateRequest request = new CategoryCreateRequest();
        request.setName("  Nhan   Cuoi  ");
        request.setSlug(null);
        request.setDescription("  Mo ta   ");

        CategoryResponse response = new CategoryResponse();
        response.setName("Nhan Cuoi");
        when(categoryMapper.toResponse(any(Category.class))).thenReturn(response);

        CategoryResponse result = categoryCommandService.create(request);

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(captor.capture());
        Category saved = captor.getValue();
        assertEquals("Nhan Cuoi", saved.getName());
        assertEquals("nhan-cuoi", saved.getSlug());
        assertEquals("Mo ta", saved.getDescription());
        assertEquals(CategoryStatus.ACTIVE, saved.getStatus());
        assertEquals("Nhan Cuoi", result.getName());
    }

    @Test
    void create_shouldThrowWhenNameBlank() {
        CategoryCreateRequest request = new CategoryCreateRequest();
        request.setName("   ");

        assertThrows(BadRequestException.class, () -> categoryCommandService.create(request));
    }
}
