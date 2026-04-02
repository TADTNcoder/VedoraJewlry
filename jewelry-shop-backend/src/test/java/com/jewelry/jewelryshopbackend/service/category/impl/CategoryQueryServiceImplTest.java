package com.jewelry.jewelryshopbackend.service.category.impl;

import com.jewelry.jewelryshopbackend.dto.response.category.CategoryResponse;
import com.jewelry.jewelryshopbackend.entity.Category;
import com.jewelry.jewelryshopbackend.enums.CategoryStatus;
import com.jewelry.jewelryshopbackend.exception.BadRequestException;
import com.jewelry.jewelryshopbackend.mapper.CategoryMapper;
import com.jewelry.jewelryshopbackend.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryQueryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryQueryServiceImpl categoryQueryService;

    @Test
    void getBySlugForPublic_shouldTrimAndLowercaseSlug() {
        Category category = Category.builder()
                .name("Ring")
                .slug("ring")
                .status(CategoryStatus.ACTIVE)
                .build();
        CategoryResponse response = new CategoryResponse();
        response.setSlug("ring");

        when(categoryRepository.findBySlugIgnoreCaseAndStatus("ring", CategoryStatus.ACTIVE))
                .thenReturn(Optional.of(category));
        when(categoryMapper.toResponse(category)).thenReturn(response);

        CategoryResponse result = categoryQueryService.getBySlugForPublic("  RING  ");
        assertEquals("ring", result.getSlug());
    }

    @Test
    void getAllForAdmin_shouldThrowWhenStatusDeleted() {
        assertThrows(
                BadRequestException.class,
                () -> categoryQueryService.getAllForAdmin(0, 10, "createdAt", "desc", null, "DELETED")
        );
    }
}
