package com.binary_winters.photoapp.api.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
// This will make this Spring Boot application a client which will be communicating with the discovery server.
@EnableDiscoveryClient
public class PhotoAppApiUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiUsersApplication.class, args);
	}
	
	@Bean
//	For BCryptPasswordEncoder to be injected into our UserService implementation via constructor, this object needs to exist in 
//	Application Context so from there I create a bean of BCryptPasswordEncoder type. So it can be injected to our users there's implementation.
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
