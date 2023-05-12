package com.example.JWTAuthenticatorProject.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

public interface LoginDetailsDAO {
	public List<UserDetails> getAll();
	public boolean checkUser(String username, String password);
	public UserDetails getUserDetail(String username);
}
