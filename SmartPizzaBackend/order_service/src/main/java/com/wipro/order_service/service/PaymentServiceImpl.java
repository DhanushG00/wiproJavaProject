package com.wipro.order_service.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.wipro.order_service.dto.PaymentRequestDTO;
import com.wipro.order_service.dto.PaymentResponseDTO;
import com.wipro.order_service.entity.OrderEntity;
import com.wipro.order_service.entity.PaymentEntity;
import com.wipro.order_service.repository.OrderRepository;
import com.wipro.order_service.repository.PaymentRepo;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepo paymentRepository;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Override
    public PaymentResponseDTO createPaymentOrder(PaymentRequestDTO request) {

        try {
            OrderEntity order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            RazorpayClient client = new RazorpayClient(keyId, keySecret);

            JSONObject options = new JSONObject();
            options.put("amount", request.getAmount() * 100);
            options.put("currency", "INR");
            options.put("receipt", "order_" + order.getId());

            com.razorpay.Order razorpayOrder = client.orders.create(options);

            // Save in DB
            PaymentEntity payment = new PaymentEntity();

            payment.setOrderId(order.getId());
            payment.setUserId(order.getUserId());
            payment.setRazorpayOrderId((String) razorpayOrder.get("id"));
            payment.setAmount(request.getAmount());
            payment.setStatus("CREATED");

            paymentRepository.save(payment);

            return PaymentResponseDTO.builder()
                    .razorpayOrderId(razorpayOrder.get("id"))
                    .orderId(order.getId())
                    .userId(order.getUserId())
                    .amount(request.getAmount())
                    .status("CREATED")
                    .build();

        } catch (Exception e) {

            return PaymentResponseDTO.builder()
                    .razorpayOrderId("FAILED")
                    .orderId(request.getOrderId())
                    .amount(request.getAmount())
                    .status("ERROR: " + e.getMessage())
                    .build();
        }
    }
}