package com.wipro.order_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.order_service.dto.OrderDTO;
import com.wipro.order_service.entity.Cart;
import com.wipro.order_service.entity.MenuEntity;
import com.wipro.order_service.entity.OrderEntity;
import com.wipro.order_service.entity.OrderItemEntity;
import com.wipro.order_service.repository.CartRepository;
import com.wipro.order_service.repository.OrderRepository;
import com.wipro.order_service.service.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Cart cart;
    private MenuEntity menu;

    @BeforeEach
    void setUp() {
        menu = new MenuEntity();
        menu.setId(1L);
        menu.setName("Pizza");
        menu.setPrice(200);
        menu.setCategory("Veg");

        cart = new Cart();
        cart.setId(10L);
        cart.setUserId(100L);
        cart.setMenu(menu);
        cart.setQuantity(2);
    }

    //  placeOrder - SUCCESS
    @Test
    void testPlaceOrder_Success() {
        when(cartRepository.findByUserId(100L)).thenReturn(List.of(cart));

        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(invocation -> {
            OrderEntity order = invocation.getArgument(0);

            // simulate DB adding IDs
            order.setId(1L);
            for (OrderItemEntity item : order.getOrderItems()) {
                item.setId(1L);
            }
            return order;
        });

        OrderDTO result = orderService.placeOrder(100L);

        assertNotNull(result);
        assertEquals(100L, result.getUserId());
        assertEquals("PLACED", result.getStatus());
        assertEquals(400, result.getTotalPrice()); // 200 * 2
        assertEquals(1, result.getItems().size());
    }

    //  placeOrder - EMPTY CART
    @Test
    void testPlaceOrder_EmptyCart() {
        when(cartRepository.findByUserId(100L)).thenReturn(List.of());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            orderService.placeOrder(100L);
        });

        assertEquals("Cart is empty", ex.getMessage());
    }

    // getAllOrders
    @Test
    void testGetAllOrders() {
        OrderEntity order = createOrderEntity();

        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<OrderDTO> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        assertEquals("PLACED", result.get(0).getStatus());
    }

    //  getOrdersByUser
    @Test
    void testGetOrdersByUser() {
        OrderEntity order = createOrderEntity();

        when(orderRepository.findByUserId(100L)).thenReturn(List.of(order));

        List<OrderDTO> result = orderService.getOrdersByUser(100L);

        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getUserId());
    }

    // getOrderById - SUCCESS
    @Test
    void testGetOrderById_Success() {
        OrderEntity order = createOrderEntity();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderDTO result = orderService.getOrderById(1L);

        assertEquals(1L, result.getId());
        assertEquals("PLACED", result.getStatus());
    }

    //getOrderById - NOT FOUND
    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(1L);
        });

        assertEquals("Order not found", ex.getMessage());
    }

    // helper method
    private OrderEntity createOrderEntity() {
        OrderEntity order = new OrderEntity();
        order.setId(1L);
        order.setUserId(100L);
        order.setStatus("PLACED");
        order.setTotalPrice(400);

        OrderItemEntity item = new OrderItemEntity();
        item.setId(1L);
        item.setQuantity(2);
        item.setMenu(menu);

        order.setOrderItems(List.of(item));

        return order;
    }
}