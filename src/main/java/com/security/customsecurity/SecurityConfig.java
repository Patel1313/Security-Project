package com.security.customsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig
{

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {this.userDetailsService = userDetailsService;}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception
    {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a->a
                        .requestMatchers("/",
                                "/swagger-ui/**",
                                "/v3/**",
                                "/users/login",
                                "/users/add").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(14);
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration)
            throws Exception
    {
        return configuration.getAuthenticationManager();
    }

}
