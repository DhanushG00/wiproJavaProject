package com.wipro.order_service.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.wipro.order_service.dto.PaymentRequestDTO;
import com.wipro.order_service.dto.PaymentResponseDTO;
import com.wipro.order_service.entity.OrderEntity;
import com.wipro.order_service.repository.OrderRepository;



@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public PaymentServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public PaymentResponseDTO createPaymentOrder(PaymentRequestDTO request) {

        try {
            //  Fetch Order from DB
            OrderEntity order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            // Validate Razorpay keys
            if (keyId == null || keySecret == null) {
                throw new RuntimeException("Razorpay keys not configured");
            }

            // Create Razorpay Client 
            RazorpayClient client = new RazorpayClient(keyId, keySecret);

            //  Create payment request
            JSONObject options = new JSONObject();
            options.put("amount", request.getAmount() * 100); 
            options.put("currency", "INR");
            options.put("receipt", "order_" + order.getId());

            //Call Razorpay API
            com.razorpay.Order razorpayOrder = client.orders.create(options);

            //  Build response with userId 
            return PaymentResponseDTO.builder()
                    .razorpayOrderId(razorpayOrder.get("id"))
                    .orderId(order.getId())
                    .userId(order.getUserId())  
                    .amount(request.getAmount())
                    .status("CREATED")
                    .build();

        } catch (Exception e) {

            // Fail-safe response 
            return PaymentResponseDTO.builder()
                    .razorpayOrderId("FAILED")
                    .orderId(request.getOrderId())
                    .userId(null)
                    .amount(request.getAmount())
                    .status("ERROR: " + e.getMessage())
                    .build();
        }
    }
}