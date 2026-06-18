package com.wipro.order_service.pattern;
import java.util.List;

import com.wipro.order_service.entity.Cart;
import com.wipro.order_service.entity.OrderEntity;

public interface OrderFactory {
    OrderEntity createOrder(List<Cart> cartItems, Long userId);
}