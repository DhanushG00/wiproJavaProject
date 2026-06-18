package com.wipro.order_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.order_service.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUserId(Long userId);
}