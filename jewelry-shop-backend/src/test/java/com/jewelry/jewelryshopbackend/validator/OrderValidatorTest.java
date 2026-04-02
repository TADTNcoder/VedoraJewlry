package com.jewelry.jewelryshopbackend.validator;

import com.jewelry.jewelryshopbackend.entity.Order;
import com.jewelry.jewelryshopbackend.enums.OrderStatus;
import com.jewelry.jewelryshopbackend.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderValidatorTest {

    private final OrderValidator orderValidator = new OrderValidator();

    @Test
    void validateAmount_shouldThrowWhenDiscountExceedsTotal() {
        assertThrows(
                BadRequestException.class,
                () -> orderValidator.validateAmount(BigDecimal.ZERO, BigDecimal.valueOf(101), BigDecimal.valueOf(100))
        );
    }

    @Test
    void parseOrderStatus_shouldReturnNullWhenBlank() {
        assertNull(orderValidator.parseOrderStatus("  "));
    }

    @Test
    void parseOrderStatus_shouldParseCaseInsensitive() {
        assertEquals(OrderStatus.PENDING, orderValidator.parseOrderStatus("pending"));
    }

    @Test
    void validateStatusTransition_shouldThrowWhenFinalized() {
        Order order = Order.builder().orderStatus(OrderStatus.DELIVERED).build();
        assertThrows(
                BadRequestException.class,
                () -> orderValidator.validateStatusTransition(order, OrderStatus.PENDING)
        );
    }
}
