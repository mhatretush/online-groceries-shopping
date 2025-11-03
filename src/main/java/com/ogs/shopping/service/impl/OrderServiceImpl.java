package com.ogs.shopping.service.impl;

import com.ogs.shopping.custom_exception.ApiException;
import com.ogs.shopping.custom_exception.ResourceNotFoundException;
import com.ogs.shopping.dto.response.ApiResponse;
import com.ogs.shopping.dto.response.OrderItemResponseDto;
import com.ogs.shopping.dto.response.OrderResponseDto;
import com.ogs.shopping.entity.*;
import com.ogs.shopping.repository.*;
import com.ogs.shopping.service.OrderService;
import com.ogs.shopping.service.PublicHolidayService;
import com.ogs.shopping.utils.OrderMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final PublicHolidayService publicHolidayService;
    private final OfferRepository offerRepository;
    private final OfferClaimRepository offerClaimRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderResponseDto placeOrder(Long userId, String offerCode) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new ApiException("Cart is empty. Cannot place an order.");
        }

        LocalDate today = LocalDate.now();

        if (today.getDayOfWeek() == DayOfWeek.SUNDAY || publicHolidayService.isPublicHoliday(today)) {
            throw new ApiException("Orders cannot be placed on Sunday and on public holidays");
        }

        double totalAmount = cart.getItems().stream()
                .mapToDouble(i -> i.getProduct().getProductPrice() * i.getQuantity())
                .sum();

        if (totalAmount < 99) {
            throw new ApiException("Minimum order amount must be ₹99 or more.");
        }
        if (totalAmount > 4999) {
            throw new ApiException("Maximum order amount cannot exceed ₹4999.");
        }

        double discountAmount = 0.0;
        Offer offer = null;

        if (offerCode != null && !offerCode.isBlank()) {
            offer = offerRepository.findByCode(offerCode)
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid offer code."));

            // Validate offer period & status
            if (!offer.isValid() || today.isBefore(offer.getValidFrom()) || today.isAfter(offer.getValidTill())) {
                throw new ApiException("Offer is expired or inactive.");
            }

            boolean alreadyClaimed = offerClaimRepository.existsByUserAndOffer(user, offer);
            if (alreadyClaimed) {
                throw new ApiException("You have already claimed this offer.");
            }

            if (offer.getDiscountType() == DiscountType.FLAT) {
                discountAmount = offer.getDiscount();
            } else if (offer.getDiscountType() == DiscountType.PERCENTAGE) {
                discountAmount = totalAmount * (offer.getDiscount() / 100.0);
            }

            OfferClaim claim = OfferClaim.builder()
                    .user(user)
                    .offer(offer)
                    .claimedAt(today)
                    .successful(true)
                    .build();
            offerClaimRepository.save(claim);
        }

        double payableAmount = Math.max(0, totalAmount - discountAmount);

        Order order = orderMapper.toOrder(user, cart.getItems());
        order.setTotalAmount(totalAmount);
        order.setDiscountAmount(discountAmount);
        order.setPayableAmount(payableAmount);
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);

        for(OrderItem item : savedOrder.getOrderItems()){
            Product product = item.getProduct();
            int remainingQty = product.getProductQty()-item.getQuantity();

            if(remainingQty < 0){
                throw new ApiException("Insufficient stock for product");
            }

            product.setProductQty(remainingQty);
            productRepository.save(product);
        }

        cart.getItems().clear();
        cartRepository.save(cart);

        OrderResponseDto response = orderMapper.toOrderResponseDto(savedOrder);

        if (offer != null) {
            response.setOfferCode(offer.getCode());
            response.setDiscountType(offer.getDiscountType().name());
            response.setOfferApplied(true);
        } else {
            response.setOfferApplied(false);
        }

        return response;
    }

    @Override
    @Cacheable(value="orders", key="#p0")
    public OrderResponseDto viewOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("Order Not Found");
                });
        return orderMapper.toOrderResponseDto(order);
    }

    @Override
    public List<OrderResponseDto> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("Order Not Found");
                });
        return orderRepository.findByUserOrderByOrderDateDesc(user)
                .stream()
                .map(orderMapper::toOrderResponseDto)
                .toList();
    }

    @Override
    public ApiResponse updateOrderStatus(Long orderId, OrderStatus newStatus) {
        // Fetch the order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        // Optional: Validation logic
        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new ApiException("Order already delivered. Status cannot be changed.");
        }

        if (order.getStatus() == newStatus) {
            throw new ApiException("Order already in status: " + newStatus);
        }

        // Update status
        order.setStatus(newStatus);
        order.setModifiedDate(LocalDate.now());
        orderRepository.save(order);

        return new ApiResponse("Order status updated to: " + newStatus);
    }


}