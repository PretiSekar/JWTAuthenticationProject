package com.example.JWTAuthenticatorProject.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginDetailsDAOImpl implements LoginDetailsDAO{
	private List<UserDetails> users;
	
	public LoginDetailsDAOImpl() {
		users = new ArrayList<>();
		users.add(new User("Harry", "password", new ArrayList<>()));
		users.add(new User("Potter", "password", new ArrayList<>()));
	}
	
	@Override
	public List<UserDetails> getAll() {
		return users;
	}
	@Override
	public boolean checkUser(String username, String password) {
		for(UserDetails l:users) {
			if((l.getUsername()).equals(username)) {
				if((l.getPassword()).equals(password)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public UserDetails getUserDetail(String username) {
		UserDetails lc = null;
		for(UserDetails user:users) {
			if(username.equals(user.getUsername())) {
				lc = user;
				break;
			}
		}
		return lc;
	}

}
