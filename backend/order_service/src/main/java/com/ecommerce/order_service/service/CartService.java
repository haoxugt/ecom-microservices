package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dto.CartItemRequest;
import com.ecommerce.order_service.model.CartItem;
import com.ecommerce.order_service.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartItemRepository cartItemRepository;

    public boolean addToCard(Long userId, CartItemRequest request) {
//        // Look for product
//        Optional<Product> productOpt = productRepository.findById(request.getProductId());
//
//        if (productOpt.isEmpty())
//            return false;
//
//        Product product = productOpt.get();
//
//        // Check if the stock quantity is valid
//        if (product.getStockQuantity() < request.getQuantity())
//            return false;
//
//        // Look for user
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//
//        if (userOpt.isEmpty())
//            return false;
//
//        User user = userOpt.get();

        // Check if the product is already in the cart
        Optional<CartItem> existingCartItemOpt = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
        if (existingCartItemOpt.isPresent()) {
            // Update the quantity
            CartItem existingCartItem = existingCartItemOpt.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000));
            cartItemRepository.save(existingCartItem);
        } else {
            // Create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(Long userId, Long productId) {
//        // Look for product
//        Optional<Product> productOpt = productRepository.findById(productId);
//
//        // Look for user
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//
//        if (productOpt.isPresent() && userOpt.isPresent()) {
//            cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
//            return true;
//        }
//        return false;
        Optional<CartItem> cartItemOpt = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if (cartItemOpt.isPresent()) {
            cartItemRepository.delete(cartItemOpt.get());
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);

    }
}
