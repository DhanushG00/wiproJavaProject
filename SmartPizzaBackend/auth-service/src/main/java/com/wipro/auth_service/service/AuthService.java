package com.wipro.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.auth_service.entity.User;
import com.wipro.auth_service.repository.UserRepo;

@Service
public class AuthService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder encoder;

	public void registerUser(String username, String password, String roleName) {

		if (userRepo.findByUsernameIgnoreCase(username).isPresent()) {
			throw new RuntimeException("Username already exists");
		}

		//Role role = roleRepo.findByName(roleName).orElseThrow(() -> new RoleNotFoundException("Role not found"));

		User user = new User();
		user.setUsername(username.toLowerCase());
		user.setPassword(encoder.encode(password));
		user.setRoles(roleName);

		userRepo.save(user);
	}
}