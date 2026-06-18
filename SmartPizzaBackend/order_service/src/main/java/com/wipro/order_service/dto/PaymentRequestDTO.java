package com.wipro.order_service.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long orderId;
    private Double amount;
}