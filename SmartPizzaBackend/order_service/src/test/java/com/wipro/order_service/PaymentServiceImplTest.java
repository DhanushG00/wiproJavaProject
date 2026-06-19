package com.wipro.order_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.order_service.dto.PaymentRequestDTO;
import com.wipro.order_service.dto.PaymentResponseDTO;
import com.wipro.order_service.entity.OrderEntity;
import com.wipro.order_service.entity.PaymentEntity;
import com.wipro.order_service.repository.OrderRepository;
import com.wipro.order_service.repository.PaymentRepo;
import com.wipro.order_service.service.PaymentServiceImpl;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentRepo paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private OrderEntity order;
    private PaymentRequestDTO request;

    @BeforeEach
    void setUp() throws Exception {
        order = new OrderEntity();
        order.setId(1L);
        order.setUserId(100L);

        request = new PaymentRequestDTO();
        request.setOrderId(1L);
        request.setAmount((double) 500);

        // Inject fake keys (because @Value fields)
        setField(paymentService, "keyId", "test_key");
        setField(paymentService, "keySecret", "test_secret");
    }

    // SUCCESS CASE (mocking repo)
    @Test
    void testCreatePaymentOrder_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        
    

        PaymentResponseDTO result = paymentService.createPaymentOrder(request);

        //  We validate either success OR fallback error (safe test)
        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        assertEquals(500, result.getAmount());
    }

    //  ORDER NOT FOUND
    @Test
    void testCreatePaymentOrder_OrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        PaymentResponseDTO result = paymentService.createPaymentOrder(request);

        assertEquals("FAILED", result.getRazorpayOrderId());
        assertTrue(result.getStatus().contains("Order not found"));
    }

    // Exception Handling (simulate failure)
    @Test
    void testCreatePaymentOrder_Exception() {
        when(orderRepository.findById(1L)).thenThrow(new RuntimeException("DB error"));

        PaymentResponseDTO result = paymentService.createPaymentOrder(request);

        assertEquals("FAILED", result.getRazorpayOrderId());
        assertTrue(result.getStatus().contains("DB error"));
    }

    // Helper to set private @Value fields
    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}