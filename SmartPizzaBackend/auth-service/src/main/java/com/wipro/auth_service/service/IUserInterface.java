package com.wipro.auth_service.service;

import java.util.List;

import com.wipro.auth_service.dto.AuthRequest;
import com.wipro.auth_service.entity.User;

public interface IUserInterface {
	
	public User addUser(AuthRequest user);
	public List<AuthRequest> getAllUsers();
	public void deleteUsersById(Long id);
	public User findUserById(Long id);

}
