package com.jewelry.jewelryshopbackend.repository;

import com.jewelry.jewelryshopbackend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}