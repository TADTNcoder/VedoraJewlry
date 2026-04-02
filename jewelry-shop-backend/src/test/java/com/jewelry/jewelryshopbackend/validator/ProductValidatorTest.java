package com.jewelry.jewelryshopbackend.validator;

import com.jewelry.jewelryshopbackend.entity.Product;
import com.jewelry.jewelryshopbackend.exception.BadRequestException;
import com.jewelry.jewelryshopbackend.repository.CategoryRepository;
import com.jewelry.jewelryshopbackend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductValidatorTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductValidator productValidator;

    @Test
    void validateCreateDuplicateSlug_shouldThrowWhenExists() {
        when(productRepository.existsBySlugIgnoreCase("gold-ring")).thenReturn(true);
        assertThrows(BadRequestException.class, () -> productValidator.validateCreateDuplicateSlug("gold-ring"));
    }

    @Test
    void validateUpdateDuplicateSlug_shouldSkipWhenUnchanged() {
        Product product = Product.builder().slug("gold-ring").build();
        product.setId(1L);
        assertDoesNotThrow(() -> productValidator.validateUpdateDuplicateSlug(product, "Gold-Ring"));
    }
}
