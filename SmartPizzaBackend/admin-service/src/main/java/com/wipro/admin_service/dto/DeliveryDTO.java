package com.wipro.admin_service.dto;

import lombok.Data;

@Data
public class DeliveryDTO {

    private Long id;

    private Long orderId;
    private Long agentId;

    private double latitude;
    private double longitude;

    private String status; // ASSIGNED, PICKED, DELIVERED
}
