package com.example.JWTAuthenticatorProject.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.JWTAuthenticatorProject.config.JWTTokenUtil;
import com.example.JWTAuthenticatorProject.dao.LoginDetailsDAO;

@RestController
@CrossOrigin
@RequestMapping("/JWT")
public class LoginController {
	
	@Autowired
	private JWTTokenUtil jWTTokenUtil;
	
	@Autowired
	private LoginDetailsDAO loginDetailsDAO;
	
	@GetMapping("/hello")
	public String firstPage() {
		return "Hello World";
	}
	
	@GetMapping("/token")
	public String token() {
		return "Page is verified with JWT Authorization";
	}
	
	
	@RequestMapping(value = "/createToken/{username}/{password}", method = RequestMethod.GET)
	public String createToken(@PathVariable("username") String username, @PathVariable("password") String password ) {
		UserDetails lC = new User(username, password, new ArrayList<>());
		boolean check = loginDetailsDAO.checkUser(username, password);
		return (check)? jWTTokenUtil.generateToken(lC): null;
	}
	
}