package com.jewelry.jewelryshopbackend.util;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageableUtilsTest {

    @Test
    void build_shouldClampPageAndSizeAndFallbackSort() {
        Pageable pageable = PageableUtils.build(-1, 999, "invalid", "desc");

        assertEquals(0, pageable.getPageNumber());
        assertEquals(100, pageable.getPageSize());
        Sort.Order order = pageable.getSort().getOrderFor("createdAt");
        assertEquals(Sort.Direction.DESC, order.getDirection());
    }

    @Test
    void build_shouldUseAllowedSortAndAscDirection() {
        Pageable pageable = PageableUtils.build(
                2,
                20,
                "name",
                "asc",
                Set.of("name", "createdAt"),
                "createdAt"
        );

        assertEquals(2, pageable.getPageNumber());
        assertEquals(20, pageable.getPageSize());
        Sort.Order order = pageable.getSort().getOrderFor("name");
        assertEquals(Sort.Direction.ASC, order.getDirection());
    }
}
