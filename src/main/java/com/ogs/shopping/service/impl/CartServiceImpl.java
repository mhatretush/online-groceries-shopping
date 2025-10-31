package com.ogs.shopping.service.impl;

import com.ogs.shopping.custom_exception.ApiException;
import com.ogs.shopping.custom_exception.ResourceNotFoundException;
import com.ogs.shopping.dto.request.AddToCartDto;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.CartItemResponseDto;
import com.ogs.shopping.dto.response.CartResponseDto;
import com.ogs.shopping.entity.*;
import com.ogs.shopping.repository.*;
import com.ogs.shopping.service.CartService;
import com.ogs.shopping.utils.CartMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;
    private final OfferRepository offerRepository;
    private final OfferClaimRepository offerClaimRepository;

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
        return cartMapper.toCartResponseDto(cart);
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

        CartResponseDto response =  cartMapper.toCartResponseDto(cart);

        return  new ApiResponse("success");
    }

    @Override
    public CartResponseDto viewCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return  cartMapper.toCartResponseDto(cart);

    }

    @Override
    public CartResponseDto applyDiscount(Long userId, String offerCode) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("User not found");
                });

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("Cart not found");
                });

        Offer offer = offerRepository.findByCode(offerCode)
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("Offer not found");
                });

        LocalDate today = LocalDate.now();
        if(!offer.isValid() || today.isBefore(offer.getValidFrom()))
        {
            throw new ApiException("Offer is not valid");
        }

        boolean alreadyClaimed = offerClaimRepository.existsByUserAndOffer(user, offer);
        if(alreadyClaimed){
            throw new ApiException("Offer is already claimed");
        }

        double totalAmt = cart.getItems().stream()
                .mapToDouble(item->item.getQuantity()*item.getProduct().getProductPrice())
                .sum();

        double discountAmount = 0.0;
        if(offer.getDiscountType() == DiscountType.FLAT){
            discountAmount = offer.getDiscount();
        }else if(offer.getDiscountType()==DiscountType.PERCENTAGE){
            discountAmount = totalAmt * (offer.getDiscount()/100.0);
        }

        double finalAmount = Math.max(0, totalAmt-discountAmount);

        CartResponseDto response = cartMapper.toCartResponseDto(cart);
        response.setTotalAmount(totalAmt);
        response.setDiscountApplied(true);
        response.setDiscountAmount(discountAmount);
        response.setFinalAmount(finalAmount);
        response.setOfferCode(offerCode);

        return response;
    }
}