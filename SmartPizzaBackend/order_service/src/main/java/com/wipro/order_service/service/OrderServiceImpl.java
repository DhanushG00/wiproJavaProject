package com.wipro.order_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.order_service.dto.MenuDTO;
import com.wipro.order_service.dto.OrderDTO;
import com.wipro.order_service.dto.OrderItemDTO;
import com.wipro.order_service.entity.Cart;
import com.wipro.order_service.entity.OrderEntity;
import com.wipro.order_service.entity.OrderItemEntity;
import com.wipro.order_service.repository.CartRepository;
import com.wipro.order_service.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDTO placeOrder(Long userId) {

        List<Cart> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        OrderEntity order = new OrderEntity();
        order.setUserId(userId);
        order.setStatus("PLACED");

        List<OrderItemEntity> orderItems = new ArrayList<>();
        double total = 0;

        for (Cart cart : cartItems) {

            OrderItemEntity item = new OrderItemEntity();
            item.setOrder(order);
            item.setMenu(cart.getMenu());
            item.setQuantity(cart.getQuantity());

            orderItems.add(item);

            total += cart.getMenu().getPrice() * cart.getQuantity();
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(total);

        OrderEntity savedOrder = orderRepository.save(order);

        cartRepository.deleteByUserId(userId);

        //  Convert to DTO
        return convertToDTO(savedOrder);
    }

    @Override
    public List<OrderDTO> getAllOrders() {

        List<OrderEntity> orders = orderRepository.findAll();

        return orders.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<OrderDTO> getOrdersByUser(Long userId) {

        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return convertToDTO(order);
    }

    //  ENTITY → DTO Conversion
    private OrderDTO convertToDTO(OrderEntity order) {

        OrderDTO dto = new OrderDTO();

        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());

        List<OrderItemDTO> itemDTOs = order.getOrderItems().stream().map(item -> {

            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setQuantity(item.getQuantity());

            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setId(item.getMenu().getId());
            menuDTO.setName(item.getMenu().getName());
            menuDTO.setPrice(item.getMenu().getPrice());
            menuDTO.setCategory(item.getMenu().getCategory());

            itemDTO.setMenu(menuDTO);

            return itemDTO;

        }).toList();

        dto.setItems(itemDTOs);

        return dto;
    }
}