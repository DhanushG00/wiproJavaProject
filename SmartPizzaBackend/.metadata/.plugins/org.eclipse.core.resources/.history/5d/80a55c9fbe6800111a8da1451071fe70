package com.wipro.order_service.service;



import java.util.List;

import com.wipro.order_service.dto.OrderRequestDTO;
import com.wipro.order_service.dto.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO placeOrder(OrderRequestDTO request);

    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO getOrderById(Long id);

    void deleteOrder(Long id);
}