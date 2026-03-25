package com.jewelry.jewelryshopbackend.repository;

import com.jewelry.jewelryshopbackend.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}