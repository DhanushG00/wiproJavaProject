package com.wipro.delivery_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.delivery_service.entity.DeliveryAgent;

@Repository
public interface DeliveryAgentRepo extends JpaRepository<DeliveryAgent, Long> {
}