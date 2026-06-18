package com.wipro.delivery_service.service;

import java.util.List;
import java.util.Optional;

import com.wipro.delivery_service.entity.DeliveryAgent;

public interface IDeliveryAgent {
	//public UsersDTO registerUser(UsersDTO user);
	public List<DeliveryAgent> getAllDeliveryAgents();
	public Optional<DeliveryAgent> getDeliveryAgentById(Long id);
	public void deleteAgentById(Long id);
}
