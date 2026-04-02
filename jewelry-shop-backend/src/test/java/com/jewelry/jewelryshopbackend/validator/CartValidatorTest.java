package com.jewelry.jewelryshopbackend.validator;

import com.jewelry.jewelryshopbackend.entity.Product;
import com.jewelry.jewelryshopbackend.entity.ProductVariant;
import com.jewelry.jewelryshopbackend.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartValidatorTest {

    private final CartValidator cartValidator = new CartValidator();

    @Test
    void validateVariantCanAdd_shouldThrowWhenVariantInactive() {
        ProductVariant variant = variant(false, false, 10);
        assertThrows(BadRequestException.class, () -> cartValidator.validateVariantCanAdd(variant, 1));
    }

    @Test
    void validateVariantCanAdd_shouldThrowWhenProductInactive() {
        ProductVariant variant = variant(true, false, 10);
        variant.getProduct().setStatus(false);
        assertThrows(BadRequestException.class, () -> cartValidator.validateVariantCanAdd(variant, 1));
    }

    @Test
    void validateVariantCanAdd_shouldThrowWhenQuantityExceedsStock() {
        ProductVariant variant = variant(true, false, 5);
        assertThrows(BadRequestException.class, () -> cartValidator.validateVariantCanAdd(variant, 6));
    }

    @Test
    void validateVariantCanAdd_shouldPassWhenValid() {
        ProductVariant variant = variant(true, false, 10);
        assertDoesNotThrow(() -> cartValidator.validateVariantCanAdd(variant, 3));
    }

    private ProductVariant variant(Boolean status, Boolean deleted, int stock) {
        Product product = Product.builder()
                .name("Ring")
                .slug("ring")
                .basePrice(BigDecimal.TEN)
                .status(true)
                .build();
        ProductVariant variant = ProductVariant.builder()
                .product(product)
                .sku("SKU-1")
                .price(BigDecimal.TEN)
                .stockQuantity(stock)
                .status(status)
                .deleted(deleted)
                .build();
        variant.setId(1L);
        return variant;
    }
}
