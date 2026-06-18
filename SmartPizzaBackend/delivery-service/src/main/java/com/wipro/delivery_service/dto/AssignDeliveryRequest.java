package com.wipro.delivery_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignDeliveryRequest {

    @NotNull
    private Long orderId;

    @NotNull
    private Long agentId;
}
