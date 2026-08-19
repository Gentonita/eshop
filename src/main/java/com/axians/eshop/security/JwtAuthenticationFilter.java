package com.axians.eshop.security;

import java.io.IOException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.axians.eshop.entity.User;
import com.axians.eshop.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            String email = jwtService.extractEmail(token);

            User user = userRepository
                    .findByEmailValueAndDeletedAtIsNull(email)
                    .orElse(null);

            if (user != null && jwtService.isTokenValid(token, email)) {

                SimpleGrantedAuthority authority =
                        new SimpleGrantedAuthority("ROLE_" + user.getRole().name());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                java.util.List.of(authority)
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}