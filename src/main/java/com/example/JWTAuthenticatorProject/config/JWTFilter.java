package com.example.JWTAuthenticatorProject.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.JWTAuthenticatorProject.dao.LoginDetailsDAO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter{

	@Autowired
	private JWTTokenUtil jWTTokenUtil;
	
	@Autowired
	private LoginDetailsDAO loginDetails;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestToken = request.getHeader("Authorization");
		if(requestToken != null) {
			if(requestToken.startsWith("Bearer ")) {
				String token = requestToken.substring(7);
				String username = jWTTokenUtil.getUsername(token);
				UserDetails lC = loginDetails.getUserDetail(username);
				
				if(lC != null && lC.getUsername()!= null) {
					if(jWTTokenUtil.validateToken(token, lC)) {
						UsernamePasswordAuthenticationToken tok = new UsernamePasswordAuthenticationToken(lC, null, lC.getAuthorities());
						tok.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(tok);
					}
				}
			}	
		}
		filterChain.doFilter(request, response);
	}

}
