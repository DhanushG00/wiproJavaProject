package com.wipro.admin_service.dto;

import lombok.Data;

@Data
public class OrderItemDTO {

    private Long id;
    private MenuDTO menu;
    private int quantity;
}
