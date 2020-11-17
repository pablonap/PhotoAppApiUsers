package com.binary_winters.photoapp.api.users.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.binary_winters.photoapp.api.users.ui.model.LoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {

			LoginRequestModel creds = 
					new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getEmail(), 
							creds.getPassword(), 
							new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
    @Override
//    If authentication is successful Spring calls this method.
/*
      This mehtod will take UserDetails and then use those UserDetails like for example username or user ID to generate a JWT token and then and 
      the JWT token to HTTP responds header and return it back with HTTP response. 

	  The client application will then be able to read this JWT token from HTTP response header and use this JWT token in these subsequent requests 
	  to our application as an authorization header. */
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

    } 

}