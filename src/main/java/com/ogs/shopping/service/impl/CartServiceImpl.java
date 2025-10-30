package com.ogs.shopping.service.impl;

import com.ogs.shopping.custom_exception.ApiException;
import com.ogs.shopping.custom_exception.ResourceNotFoundException;
import com.ogs.shopping.dto.request.AddToCartDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.CartItemResponseDto;
import com.ogs.shopping.dto.response.CartResponseDto;
import com.ogs.shopping.entity.Cart;
import com.ogs.shopping.entity.CartItem;
import com.ogs.shopping.entity.Product;
import com.ogs.shopping.entity.User;
import com.ogs.shopping.repository.CartRepository;
import com.ogs.shopping.repository.ProductRepository;
import com.ogs.shopping.repository.UserRepository;
import com.ogs.shopping.service.CartService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public CartResponseDto addToCart(AddToCartDto addToCartDto) {
        User user = userRepository.findById(addToCartDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = productRepository.findById(addToCartDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        CartItem existingCartItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getProductId().equals(product.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            int newQty = existingCartItem.getQuantity() + addToCartDto.getQuantity();
            if(newQty>product.getProductQty()){
                throw new ApiException("Not enough products available");
            }
            existingCartItem.setQuantity(newQty);
        } else {
            if(addToCartDto.getQuantity()>product.getProductQty()){
                throw new ApiException("Not enough products available");
            }
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(addToCartDto.getQuantity());
            newItem.setPriceAtAddition(product.getProductPrice());
            cart.getItems().add(newItem);
        }

        cart = cartRepository.save(cart);

        // Convert to DTO manually
        CartResponseDto response = new CartResponseDto();
        response.setCartId(cart.getCartId());
        response.setUserId(cart.getUser().getUserId());

        List<CartItemResponseDto> itemDtos = cart.getItems().stream().map(item -> {
            CartItemResponseDto dto = new CartItemResponseDto();
            dto.setCartItemId(item.getCartItemId());
            dto.setProductId(item.getProduct().getProductId());
            dto.setProductName(item.getProduct().getProductName());
            dto.setProductPrice(item.getProduct().getProductPrice());
            dto.setQuantity(item.getQuantity());
            dto.setTotalPrice(item.getQuantity() * item.getProduct().getProductPrice());
            return dto;
        }).toList();

        response.setItems(itemDtos);
        response.setTotalAmount(itemDtos.stream().mapToDouble(CartItemResponseDto::getTotalPrice).sum());

        return response;
    }


    @Override
    public ApiResponse removeFromCart(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        CartItem removeCartItem = cart.getItems().stream()
                .filter(i->i.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("Product not found");
                });

        cart.getItems().remove(removeCartItem);
        cart = cartRepository.save(cart);

        //Map to dto
        List<CartItemResponseDto> itemResponseDtos = cart.getItems().stream().map(item->{
            CartItemResponseDto dto = new CartItemResponseDto();
            dto.setCartItemId(item.getCartItemId());
            dto.setProductId(item.getProduct().getProductId());
            dto.setProductName(item.getProduct().getProductName());
            dto.setProductPrice(item.getProduct().getProductPrice());
            dto.setQuantity(item.getQuantity());
            dto.setTotalPrice(item.getQuantity() * item.getProduct().getProductPrice());
            return dto;
        }).toList();

        CartResponseDto response = new CartResponseDto();
        response.setCartId(cart.getCartId());
        response.setUserId(cart.getUser().getUserId());
        response.setItems(itemResponseDtos);
        response.setTotalAmount(itemResponseDtos.stream().mapToDouble(CartItemResponseDto::getTotalPrice).sum());
        return  new ApiResponse("success");
    }

    @Override
    public CartResponseDto viewCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        List<CartItemResponseDto> itemResponseDtos = cart.getItems().stream().map(item ->{
            CartItemResponseDto dto = new CartItemResponseDto();
            dto.setCartItemId(item.getCartItemId());
            dto.setProductId(item.getProduct().getProductId());
            dto.setProductName(item.getProduct().getProductName());
            dto.setProductPrice(item.getProduct().getProductPrice());
            dto.setQuantity(item.getQuantity());
            dto.setTotalPrice(item.getQuantity() * item.getProduct().getProductPrice());
            return dto;
        }).toList();

        CartResponseDto response = new CartResponseDto();
        response.setCartId(cart.getCartId());
        response.setUserId(cart.getUser().getUserId());
        response.setItems(itemResponseDtos);
        response.setTotalAmount(itemResponseDtos.stream().mapToDouble(CartItemResponseDto::getTotalPrice).sum());
        return  response;


    }
}