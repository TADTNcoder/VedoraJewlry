package com.jewelry.jewelryshopbackend.service.product.impl;

import com.jewelry.jewelryshopbackend.dto.request.product.ProductCreateRequest;
import com.jewelry.jewelryshopbackend.dto.response.product.ProductResponse;
import com.jewelry.jewelryshopbackend.entity.Category;
import com.jewelry.jewelryshopbackend.entity.Product;
import com.jewelry.jewelryshopbackend.exception.BadRequestException;
import com.jewelry.jewelryshopbackend.mapper.ProductMapper;
import com.jewelry.jewelryshopbackend.repository.ProductRepository;
import com.jewelry.jewelryshopbackend.validator.ProductValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductCommandServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductValidator productValidator;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductCommandServiceImpl productCommandService;

    @Test
    void create_shouldNormalizeAndSave() {
        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("  Ring  Luxury ")
                .slug(null)
                .description("  Desc  ")
                .material("  Gold  ")
                .basePrice(BigDecimal.valueOf(100))
                .thumbnail("  image.jpg ")
                .status(null)
                .categoryId(1L)
                .build();

        when(productValidator.validateActiveCategory(1L)).thenReturn(Category.builder().name("Cate").build());
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(productMapper.toResponse(any(Product.class))).thenReturn(ProductResponse.builder().name("Ring Luxury").build());

        ProductResponse result = productCommandService.create(request);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());
        Product saved = captor.getValue();
        assertEquals("Ring Luxury", saved.getName());
        assertEquals("ring-luxury", saved.getSlug());
        assertEquals("Desc", saved.getDescription());
        assertEquals("Gold", saved.getMaterial());
        assertEquals("image.jpg", saved.getThumbnail());
        assertEquals(true, saved.getStatus());
        assertEquals("Ring Luxury", result.getName());
    }

    @Test
    void create_shouldThrowWhenNameBlank() {
        ProductCreateRequest request = ProductCreateRequest.builder()
                .name(" ")
                .categoryId(1L)
                .basePrice(BigDecimal.TEN)
                .build();

        assertThrows(BadRequestException.class, () -> productCommandService.create(request));
    }
}
