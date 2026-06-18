package com.wipro.delivery_service.dto;

import lombok.Data;

@Data
public class OrderDTO {

    private Long id;
    private Long userId;
    private Double totalPrice;
    private String status;

}