package com.axians.eshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.axians.eshop.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        
        .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // AUTH
                .requestMatchers("/auth/login").permitAll()
                
                .requestMatchers("/uploads/**").permitAll()

                // PRODUCTS
                .requestMatchers(HttpMethod.GET, "/products/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/products/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/products/**")
                    .hasRole("ADMIN")

                // CATEGORIES
                .requestMatchers(HttpMethod.GET, "/categories/**")
                    .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/categories/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/categories/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/categories/**")
                    .hasRole("ADMIN")

                // USERS
                .requestMatchers(HttpMethod.POST, "/users")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/users/change-password")
                    .hasAnyRole("USER", "ADMIN")

                // CARTS
                .requestMatchers(HttpMethod.POST, "/carts")
                    .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/carts/user/**")
                    .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/carts/**")
                    .hasRole("ADMIN")

                // CART ITEMS
                .requestMatchers("/cart-items/**")
                    .hasAnyRole("USER", "ADMIN")

                // ORDERS
                .requestMatchers(HttpMethod.POST, "/orders")
                    .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/orders/user/**")
                    .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/orders")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/orders/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/orders/**")
                    .hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}