package com.wipro.order_service.controller;

import com.wipro.order_service.dto.PaymentRequestDTO;
import com.wipro.order_service.dto.PaymentResponseDTO;
import com.wipro.order_service.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public PaymentResponseDTO createPayment(@RequestBody PaymentRequestDTO request) {
        return paymentService.createPaymentOrder(request);
    }
}