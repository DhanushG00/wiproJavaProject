package com.wipro.order_service.dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartRequestDTO {

    @NotNull(message = "UserId is required")
    private Long userId;

    @NotNull(message = "MenuId is required")
    private Long menuId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
