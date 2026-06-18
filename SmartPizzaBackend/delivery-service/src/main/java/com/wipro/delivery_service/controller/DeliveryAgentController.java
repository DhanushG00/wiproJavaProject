package com.wipro.delivery_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.delivery_service.entity.DeliveryAgent;
import com.wipro.delivery_service.service.IDeliveryAgent;

@RestController
@RequestMapping("/deliveryagents")
public class DeliveryAgentController {
	
	@Autowired
	IDeliveryAgent serv;
	
	/*
	 * @PostMapping("/register") public UsersDTO registerUser(@RequestBody UsersDTO
	 * user) { return }
	 */
	
	@GetMapping("/getallagents") 
	public List<DeliveryAgent> getAllDeliveryAgents(){
		return serv.getAllDeliveryAgents();
		}
	@GetMapping("/getagentbyid/{id}")
	public Optional<DeliveryAgent> getDeliveryAgentById(@PathVariable Long id) {
		return serv.getDeliveryAgentById(id);
	}
	@DeleteMapping("/delete/{id}")
	public void deleteAgentById(@PathVariable Long id) {
		serv.deleteAgentById(id);
	}

}
