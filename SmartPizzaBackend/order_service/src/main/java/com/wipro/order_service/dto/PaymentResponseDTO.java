package com.wipro.order_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponseDTO {
    private String razorpayOrderId;
    private String status;
    private Long orderId;
    private Long userId;
    private Double amount;
}