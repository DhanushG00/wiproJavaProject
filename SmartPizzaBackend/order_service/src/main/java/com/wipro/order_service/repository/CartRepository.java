package com.wipro.order_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.order_service.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}