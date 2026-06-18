package com.wipro.order_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.order_service.dto.OrderItemDTO;
import com.wipro.order_service.dto.OrderRequestDTO;
import com.wipro.order_service.dto.OrderResponseDTO;
import com.wipro.order_service.entity.CartEntity;
import com.wipro.order_service.entity.MenuEntity;
import com.wipro.order_service.entity.OrderEntity;
import com.wipro.order_service.entity.OrderItemEntity;
import com.wipro.order_service.exception.ResourceNotFoundException;
import com.wipro.order_service.repository.CartRepository;
import com.wipro.order_service.repository.MenuRepository;
import com.wipro.order_service.repository.OrderItemRepository;
import com.wipro.order_service.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CartRepository cartRepository,
                            MenuRepository menuRepository,
                            OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.menuRepository = menuRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // PLACE ORDER (FULL FINAL IMPLEMENTATION)
    @Override
    public OrderResponseDTO placeOrder(OrderRequestDTO request) {

        //  Fetch cart items
        List<CartEntity> cartItems = cartRepository.findByUserId(request.getUserId());

        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty");
        }

        double total = 0;
        List<OrderItemDTO> itemList = new ArrayList<>();

        // Create order initially
        OrderEntity order = OrderEntity.builder()
                .userId(request.getUserId())
                .totalPrice(0)
                .status("CREATED")
                .build();

        OrderEntity savedOrder = orderRepository.save(order);

        // Process each cart item
        for (CartEntity item : cartItems) {

            MenuEntity menu = menuRepository.findById(item.getMenuId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));

            double itemTotal = menu.getPrice() * item.getQuantity();
            total += itemTotal;

            // Save item into DB
            OrderItemEntity orderItem = OrderItemEntity.builder()
                    .orderId(savedOrder.getId())
                    .pizzaId(item.getMenuId()) // menuId → pizzaId
                    .quantity(item.getQuantity())
                    .build();

            orderItemRepository.save(orderItem);

            // Add item to response
            itemList.add(OrderItemDTO.builder()
                    .pizzaId(item.getMenuId())
                    .quantity(item.getQuantity())
                    .build());
        }

        //  Update total amount
        savedOrder.setTotalPrice(total);
        orderRepository.save(savedOrder);

        //  Clear cart
        cartRepository.deleteAll(cartItems);

        // Return response
        return OrderResponseDTO.builder()
                .id(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .totalAmount(savedOrder.getTotalPrice())
                .status(savedOrder.getStatus())
                .items(itemList)
                .build();
    }

    //GET ALL ORDERS (WITH ITEMS)
    @Override
    public List<OrderResponseDTO> getAllOrders() {

        List<OrderEntity> orders = orderRepository.findAll();
        List<OrderResponseDTO> responseList = new ArrayList<>();

        for (OrderEntity order : orders) {

            List<OrderItemEntity> orderItems =
                    orderItemRepository.findByOrderId(order.getId());

            List<OrderItemDTO> itemList = new ArrayList<>();

            for (OrderItemEntity item : orderItems) {
                itemList.add(OrderItemDTO.builder()
                        .pizzaId(item.getPizzaId())
                        .quantity(item.getQuantity())
                        .build());
            }

            responseList.add(OrderResponseDTO.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .totalAmount(order.getTotalPrice())
                    .status(order.getStatus())
                    .items(itemList)
                    .build());
        }

        return responseList;
    }

    
    @Override
    public OrderResponseDTO getOrderById(Long id) {

        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        List<OrderItemEntity> orderItems =
                orderItemRepository.findByOrderId(id);

        List<OrderItemDTO> itemList = new ArrayList<>();

        for (OrderItemEntity item : orderItems) {
            itemList.add(OrderItemDTO.builder()
                    .pizzaId(item.getPizzaId())
                    .quantity(item.getQuantity())
                    .build());
        }

        return OrderResponseDTO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .totalAmount(order.getTotalPrice())
                .status(order.getStatus())
                .items(itemList)
                .build();
    }

    
    @Override
    public void deleteOrder(Long id) {

        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        
        List<OrderItemEntity> items = orderItemRepository.findByOrderId(id);
        orderItemRepository.deleteAll(items);

      
        orderRepository.delete(order);
    }
}