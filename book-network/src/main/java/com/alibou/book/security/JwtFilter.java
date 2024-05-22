package com.alibou.book.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (isAuthPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = extractJwtFromRequest(request);
        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            setAuthentication(jwt,request);
        } catch (Exception e) {
            // Log the exception and continue with the filter chain
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAuthPath(HttpServletRequest request) {
        final String authPath = "/api/v1/auth";
        return request.getServletPath().contains(authPath);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String bearerPrefix = "Bearer ";

        if (authHeader != null && authHeader.startsWith(bearerPrefix)) {
            return authHeader.substring(bearerPrefix.length());
        }

        return null;
    }

    private void setAuthentication(String jwt, HttpServletRequest request) {
        String userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }
}
