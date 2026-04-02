package com.jewelry.jewelryshopbackend.service.product.impl;

import com.jewelry.jewelryshopbackend.exception.BadRequestException;
import com.jewelry.jewelryshopbackend.mapper.ProductMapper;
import com.jewelry.jewelryshopbackend.repository.ProductRepository;
import com.jewelry.jewelryshopbackend.validator.ProductValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ProductQueryServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductValidator productValidator;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductQueryServiceImpl productQueryService;

    @Test
    void getAllActive_shouldThrowWhenMinPriceGreaterThanMaxPrice() {
        assertThrows(
                BadRequestException.class,
                () -> productQueryService.getAllActive(
                        0,
                        20,
                        "createdAt",
                        "desc",
                        null,
                        null,
                        BigDecimal.valueOf(200),
                        BigDecimal.valueOf(100)
                )
        );
    }
}
