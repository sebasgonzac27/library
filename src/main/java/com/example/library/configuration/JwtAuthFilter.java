package com.example.library.configuration;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.library.entity.TokenEntity;
import com.example.library.entity.UserEntity;
import com.example.library.repository.TokenRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  private final TokenRepository tokenRepository;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getServletPath().contains("/auth")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String jwt = authHeader.substring(7);
    final String username = jwtService.extractUsername(jwt);
    if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
      return;
    }

    final TokenEntity token = tokenRepository.findByToken(jwt).orElse(null);
    if (token == null || token.isExpired() || token.isRevoked()) {
      filterChain.doFilter(request, response);
      return;
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    final Optional<UserEntity> user = userRepository.findByEmail(userDetails.getUsername());
    if (user.isEmpty()) {
      filterChain.doFilter(request, response);
      return;
    }

    final boolean isTokenValid = jwtService.isTokenValid(jwt, user.get());
    if (!isTokenValid) {
      return;
    }

    final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);
  }

}
