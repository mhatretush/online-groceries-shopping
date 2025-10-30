package com.ogs.shopping.utils;

import com.ogs.shopping.custom_exception.ApiException;
import com.ogs.shopping.custom_exception.ResourceNotFoundException;
import com.ogs.shopping.dto.request.AddToCartDto;
import com.ogs.shopping.dto.response.CartItemResponseDto;
import com.ogs.shopping.dto.response.CartResponseDto;
import com.ogs.shopping.entity.Cart;
import com.ogs.shopping.entity.CartItem;
import com.ogs.shopping.entity.Product;
import com.ogs.shopping.repository.ProductRepository;
import com.ogs.shopping.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CartMapper {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Cart addToCart(Cart cart, AddToCartDto addToCartDto) {
        Product product = productRepository.findById(addToCartDto.getProductId())
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("Product Not Found");
                });
        CartItem existingCartItem = cart.getItems().stream()
                .filter(i->i.getProduct().getProductId().equals(product.getProductId())).findFirst().orElse(null);

        if(existingCartItem!=null){
            int newQuantity = existingCartItem.getQuantity() + addToCartDto.getQuantity();
            if(newQuantity>existingCartItem.getQuantity()){
                throw new ApiException("Quantity Exceeded");
            }
            existingCartItem.setQuantity(newQuantity);
        }else{
            if(addToCartDto.getQuantity()>product.getProductQty()){
                throw new ApiException("Quantity Exceeded");
            }
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(addToCartDto.getQuantity());
            newCartItem.setPriceAtAddition(product.getProductPrice());
            cart.getItems().add(newCartItem);
        }
        return cart;
    }

    public CartResponseDto toCartResponseDto(Cart cart) {
        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setCartId(cart.getCartId());
        cartResponseDto.setUserId(cart.getUser().getUserId());

        List<CartItemResponseDto> cartItemResponseDtos = cart.getItems().stream()
                .map(this::toCartItemResponseDto) // correct method reference
                .toList();

        cartResponseDto.setItems(cartItemResponseDtos);
        cartResponseDto.setTotalAmount(cartItemResponseDtos.stream().mapToDouble(CartItemResponseDto::getTotalPrice).sum());
        return cartResponseDto;
    }

    private CartItemResponseDto toCartItemResponseDto(CartItem item) {
        CartItemResponseDto dto = new CartItemResponseDto();
        dto.setCartItemId(item.getCartItemId());
        dto.setProductId(item.getProduct().getProductId());
        dto.setProductName(item.getProduct().getProductName());
        dto.setProductPrice(item.getProduct().getProductPrice());
        dto.setQuantity(item.getQuantity());
        dto.setTotalPrice(item.getQuantity() * item.getProduct().getProductPrice());
        return dto;
    }

}