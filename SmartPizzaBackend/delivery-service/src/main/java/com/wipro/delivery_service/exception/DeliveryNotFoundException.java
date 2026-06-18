package com.wipro.delivery_service.exception;

public class DeliveryNotFoundException extends RuntimeException {
    public DeliveryNotFoundException(String msg) {
        super(msg);
    }
}
