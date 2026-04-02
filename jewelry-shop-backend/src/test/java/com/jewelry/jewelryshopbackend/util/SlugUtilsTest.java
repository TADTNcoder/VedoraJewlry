package com.jewelry.jewelryshopbackend.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SlugUtilsTest {

    @Test
    void toSlug_shouldNormalizeVietnameseAndWhitespace() {
        String slug = SlugUtils.toSlug("  Nhẫn Cưới Kim Cương  ");
        assertEquals("nhan-cuoi-kim-cuong", slug);
    }

    @Test
    void toSlug_shouldReturnEmptyWhenInputBlank() {
        assertEquals("", SlugUtils.toSlug("   "));
    }

    @Test
    void toSlug_shouldRemoveSpecialCharacters() {
        String slug = SlugUtils.toSlug("Ring @ 18K !!!");
        assertEquals("ring-18k", slug);
    }
}
