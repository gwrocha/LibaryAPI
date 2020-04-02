package com.gwrocha.libary.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwrocha.libary.models.User;
import com.gwrocha.libary.repositories.UserRepository;

@RestController
@RequestMapping("/users")
@Secured("ROLE_ADMIN")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping
	public List<User> getAll(){
		return userRepo.findAll();
	}
	
}
