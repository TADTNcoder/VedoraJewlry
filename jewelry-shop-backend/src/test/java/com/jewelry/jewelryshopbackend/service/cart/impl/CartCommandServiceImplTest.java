package com.jewelry.jewelryshopbackend.service.cart.impl;

import com.jewelry.jewelryshopbackend.dto.request.cart.CartItemUpdateRequest;
import com.jewelry.jewelryshopbackend.dto.request.cart.CartItemUpsertRequest;
import com.jewelry.jewelryshopbackend.dto.response.cart.CartResponse;
import com.jewelry.jewelryshopbackend.entity.Cart;
import com.jewelry.jewelryshopbackend.entity.CartItem;
import com.jewelry.jewelryshopbackend.entity.Product;
import com.jewelry.jewelryshopbackend.entity.ProductVariant;
import com.jewelry.jewelryshopbackend.entity.User;
import com.jewelry.jewelryshopbackend.enums.UserStatus;
import com.jewelry.jewelryshopbackend.exception.ResourceNotFoundException;
import com.jewelry.jewelryshopbackend.mapper.CartMapper;
import com.jewelry.jewelryshopbackend.repository.CartItemRepository;
import com.jewelry.jewelryshopbackend.repository.ProductVariantRepository;
import com.jewelry.jewelryshopbackend.service.cart.CartDomainService;
import com.jewelry.jewelryshopbackend.service.user.CurrentUserService;
import com.jewelry.jewelryshopbackend.validator.CartValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartCommandServiceImplTest {

    @Mock
    private CurrentUserService currentUserService;

    @Mock
    private CartDomainService cartDomainService;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductVariantRepository productVariantRepository;

    @Mock
    private CartValidator cartValidator;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartCommandServiceImpl cartCommandService;

    @Test
    void addItem_shouldIncreaseExistingQuantity() {
        User user = User.builder().email("u@example.com").status(UserStatus.ACTIVE).build();
        user.setId(1L);
        Cart cart = Cart.builder().user(user).build();
        cart.setId(10L);

        ProductVariant variant = buildVariant();
        CartItem existing = CartItem.builder()
                .cart(cart)
                .productVariant(variant)
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(100))
                .build();
        existing.setId(100L);

        CartItemUpsertRequest request = new CartItemUpsertRequest();
        request.setProductVariantId(variant.getId());
        request.setQuantity(2);

        when(currentUserService.requireCurrentUser()).thenReturn(user);
        when(cartDomainService.getOrCreateCart(user)).thenReturn(cart);
        when(productVariantRepository.findByIdAndStatusTrueAndDeletedFalse(variant.getId())).thenReturn(Optional.of(variant));
        when(cartItemRepository.findByCartIdAndProductVariantId(cart.getId(), variant.getId())).thenReturn(Optional.of(existing));
        when(cartDomainService.getItems(cart.getId())).thenReturn(List.of(existing));
        when(cartMapper.toCartResponse(cart, List.of(existing))).thenReturn(CartResponse.builder().cartId(10L).build());

        CartResponse response = cartCommandService.addItem(request);

        ArgumentCaptor<CartItem> captor = ArgumentCaptor.forClass(CartItem.class);
        verify(cartItemRepository).save(captor.capture());
        CartItem saved = captor.getValue();
        assertEquals(3, saved.getQuantity());
        assertEquals(BigDecimal.valueOf(120), saved.getUnitPrice());
        assertEquals(10L, response.getCartId());
    }

    @Test
    void updateItem_shouldThrowWhenItemNotFound() {
        User user = User.builder().email("u@example.com").status(UserStatus.ACTIVE).build();
        user.setId(1L);
        Cart cart = Cart.builder().user(user).build();
        cart.setId(10L);

        CartItemUpdateRequest request = new CartItemUpdateRequest();
        request.setQuantity(2);

        when(currentUserService.requireCurrentUser()).thenReturn(user);
        when(cartDomainService.getOrCreateCart(user)).thenReturn(cart);
        when(cartItemRepository.findByIdAndCartId(999L, cart.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartCommandService.updateItem(999L, request));
    }

    private ProductVariant buildVariant() {
        Product product = Product.builder()
                .name("Ring")
                .slug("ring")
                .basePrice(BigDecimal.valueOf(100))
                .status(true)
                .build();
        ProductVariant variant = ProductVariant.builder()
                .product(product)
                .sku("SKU-1")
                .price(BigDecimal.valueOf(120))
                .stockQuantity(50)
                .status(true)
                .deleted(false)
                .build();
        variant.setId(5L);
        return variant;
    }
}
