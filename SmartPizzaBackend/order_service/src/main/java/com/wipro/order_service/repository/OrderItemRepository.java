package com.wipro.order_service.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.order_service.entity.OrderItemEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    List<OrderItemEntity> findByOrderId(Long orderId);
}