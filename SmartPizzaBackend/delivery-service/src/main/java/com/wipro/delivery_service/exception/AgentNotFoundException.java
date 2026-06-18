package com.wipro.delivery_service.exception;

public class AgentNotFoundException extends RuntimeException {
    public AgentNotFoundException(String msg) {
        super(msg);
    }
}
