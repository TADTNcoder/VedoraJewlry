package com.jewelry.jewelryshopbackend.repository;

import com.jewelry.jewelryshopbackend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}