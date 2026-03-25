package com.jewelry.jewelryshopbackend.repository;

import com.jewelry.jewelryshopbackend.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    boolean existsBySku(String sku);
}