package com.jewelry.jewelryshopbackend.validator;

import com.jewelry.jewelryshopbackend.entity.Category;
import com.jewelry.jewelryshopbackend.enums.CategoryStatus;
import com.jewelry.jewelryshopbackend.exception.BadRequestException;
import com.jewelry.jewelryshopbackend.repository.CategoryRepository;
import com.jewelry.jewelryshopbackend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryValidatorTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CategoryValidator categoryValidator;

    @Test
    void validateCreateDuplicateName_shouldThrowWhenExists() {
        when(categoryRepository.existsByNameIgnoreCase("Ring")).thenReturn(true);
        assertThrows(BadRequestException.class, () -> categoryValidator.validateCreateDuplicateName("Ring"));
    }

    @Test
    void parseStatus_shouldParseCaseInsensitive() {
        assertEquals(CategoryStatus.ACTIVE, categoryValidator.parseStatus("active"));
    }

    @Test
    void validateStatusChange_shouldThrowWhenInactiveButHasActiveProducts() {
        Category category = Category.builder().status(CategoryStatus.ACTIVE).build();
        category.setId(10L);
        when(productRepository.existsByCategoryIdAndStatusTrue(10L)).thenReturn(true);

        assertThrows(
                BadRequestException.class,
                () -> categoryValidator.validateStatusChange(category, CategoryStatus.INACTIVE)
        );
    }
}
