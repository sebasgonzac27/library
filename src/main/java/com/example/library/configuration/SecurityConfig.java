package com.example.library.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.library.entity.TokenEntity;
import com.example.library.repository.TokenRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final TokenRepository tokenRepository;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(csrf -> csrf.disable())
        .httpBasic(Customizer.withDefaults())
        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
        .authorizeHttpRequests(req -> req
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers("/auth/**").permitAll()
            .anyRequest().authenticated())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> logout.logoutUrl("/auth/logout")
            .addLogoutHandler((request, response, authentication) -> {
              final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
              logout(authHeader);
            }).logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));
    return httpSecurity.build();
  }

  private void logout(String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new IllegalArgumentException("Invalid token");
    }

    final String jwt = authHeader.substring(7);
    final TokenEntity token = tokenRepository.findByToken(jwt)
        .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
    token.setExpired(true);
    token.setRevoked(true);
    tokenRepository.save(token);
  }

}
