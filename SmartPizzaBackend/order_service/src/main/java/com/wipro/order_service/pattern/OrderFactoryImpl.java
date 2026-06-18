package com.wipro.order_service.pattern;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wipro.order_service.entity.Cart;
import com.wipro.order_service.entity.OrderEntity;
import com.wipro.order_service.entity.OrderItemEntity;

@Component
public class OrderFactoryImpl implements OrderFactory {

    @Override
    public OrderEntity createOrder(List<Cart> cartItems, Long userId) {

        OrderEntity order = new OrderEntity();
        order.setUserId(userId);
        order.setStatus("PLACED");

        List<OrderItemEntity> orderItems = new ArrayList<>();
        double total = 0;

        for (Cart cart : cartItems) {

            OrderItemEntity item = new OrderItemEntity();
            item.setOrder(order);                 //  relation
            item.setMenu(cart.getMenu());        //  relation
            item.setQuantity(cart.getQuantity());

            orderItems.add(item);

            //  calculate total
            total += cart.getMenu().getPrice() * cart.getQuantity();
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(total);

        return order;
    }
}
