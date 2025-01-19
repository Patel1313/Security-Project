package com.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static java.lang.System.*;

@SpringBootApplication
public class SecurityProjectApplication {
	private static final String applicationUrl = "http://localhost:1313";
	public static void main(String[] args) {
		SpringApplication.run(SecurityProjectApplication.class, args);
		out.println("SecurityProjectApplication Started..........");
		out.println("Application Url:- "+applicationUrl);
		out.printf("Swagger-ui \tUrl:- %s/actuator%n", applicationUrl);
		out.printf("Actuator \tUrl:- %s/swagger-ui/index.html%n", applicationUrl);
	}

	@Bean
	public ObjectMapper objectMapper(){return new ObjectMapper();}

}
