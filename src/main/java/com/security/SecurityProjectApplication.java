package com.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityProjectApplication {
	private final static String applicationUrl = "http://localhost:1313";

	public static void main(String[] args) {
		SpringApplication.run(SecurityProjectApplication.class, args);
		System.out.println("SecurityProjectApplication Started..........");
		System.out.println("Application Url:- "+applicationUrl);
		System.out.println("Swagger-ui 	Url:- "+applicationUrl+"/actuator");
		System.out.println("Actuator 	Url:- "+applicationUrl+"/swagger-ui/index.html");
	}

	@Bean
	public ObjectMapper objectMapper(){return new ObjectMapper();}

}
