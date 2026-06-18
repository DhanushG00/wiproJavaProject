package com.wipro.delivery_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.delivery_service.entity.Delivery;

public interface DeliveryRepo extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByOrderId(Long orderId);

    List<Delivery> findByAgentId(Long agentId);
}
