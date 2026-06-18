package com.wipro.delivery_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateLocationRequest {

    @NotNull
    private Long orderId;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
