package com.security.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${spring.application.url}")
    private String applicationUrl;

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl(applicationUrl);
        return new OpenAPI().servers(List.of(server)).info((new Info()
                        .title(applicationName.toUpperCase())
                        .description(applicationName.toLowerCase() + " application")
                        .contact((new Contact()
                                .email("bollambharath99@gmail.com")
                                .url("https://www.linkedin.com/in/bollambharathpatel/")
                                .name("Bollam Bharath")))
                        .license((new License()
                                .name("Apache")))
                        .version("V0.1")
                        .summary("CURD REST_END-POINTS")
                ));
    }
}
