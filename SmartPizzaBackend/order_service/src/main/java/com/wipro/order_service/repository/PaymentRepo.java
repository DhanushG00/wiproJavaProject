package com.wipro.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.order_service.entity.PaymentEntity;
@Repository
public interface PaymentRepo extends JpaRepository<PaymentEntity, Long> {

}
