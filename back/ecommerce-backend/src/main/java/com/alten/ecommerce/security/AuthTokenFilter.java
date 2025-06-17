package com.alten.ecommerce.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alten.ecommerce.service.jwt.JwtProviderService;
import com.alten.ecommerce.service.userdetail.CustomerUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

  private final JwtProviderService jwtProviderService;

  private final CustomerUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    String requestPath = request.getRequestURI();
    log.debug("Processing request for path: {}", requestPath);

    if (requestPath.startsWith("/api/v1/auth/")
        || requestPath.startsWith("/error")
        || requestPath.startsWith("/swagger-ui")
        || requestPath.startsWith("/v3/api-docs")) {
      log.debug("Path {} is public, skipping JWT validation.", requestPath);
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String jwt = parseJwt(request);
      if (jwt != null) {
        log.debug("JWT found: {}", jwt);
        if (jwtProviderService.validateJwtToken(jwt)) {
          String email = jwtProviderService.getEmailFromJwtToken(jwt);
          log.debug("JWT validated. Email: {}", email);

          UserDetails userDetails = userDetailsService.loadUserByUsername(email);
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());

          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authentication);
          log.debug("Authentication set for user: {}", email);
        } else {
          log.warn("Invalid JWT token for path: {}", requestPath);
        }
      } else {
        log.debug("No JWT token found in request for path: {}", requestPath);
      }
    } catch (Exception e) {
      log.error("Cannot set user authentication for path {}: {}", requestPath, e.getMessage());
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }
}
