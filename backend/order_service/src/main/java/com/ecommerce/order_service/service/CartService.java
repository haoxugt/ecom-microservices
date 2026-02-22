package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dto.CartItemRequest;
import com.ecommerce.order_service.model.CartItem;
//import com.ecommerce.order_service.model.Product;
//import com.ecommerce.order_service.model.User;
import com.ecommerce.order_service.repository.CartItemRepository;
//import com.ecommerce.order_service.repository.ProductRepository;
//import com.ecommerce.order_service.repository.UserRepository;
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
//    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
//    private final UserRepository userRepository;

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
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
        if (existingCartItem != null) {
            // Update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000).multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
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
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
        return true;

    }

    public List<CartItem> getCart(Long userId) {
//        return userRepository.findById(Long.valueOf(userId))
//                .map(cartItemRepository::findByUser)
//                .orElseGet(List::of);
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(Long userId) {
//        userRepository.findById(Long.valueOf(userId))
//                .ifPresent(cartItemRepository::deleteByUser);
        cartItemRepository.deleteByUserId(userId);

    }
}
