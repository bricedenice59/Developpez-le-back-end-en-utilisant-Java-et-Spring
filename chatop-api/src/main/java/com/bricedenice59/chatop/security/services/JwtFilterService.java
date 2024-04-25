package com.bricedenice59.chatop.security.services;

import com.bricedenice59.chatop.security.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JwtFilterService extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

            //every endpoint of the auth route (login, register...) do not need any filtering
            if(request.getRequestURI().startsWith("/api/auth/register") ||
                    request.getRequestURI().startsWith("/api/auth/login")) {
                filterChain.doFilter(request, response);
                return;
            }
            final String tokenAuthorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String bearerTokenString = "Bearer ";
            final String jwtToken;
            final String userEmail;
            if(tokenAuthorization == null || !tokenAuthorization.startsWith(bearerTokenString)) {
                filterChain.doFilter(request, response);
                return;
            }

            jwtToken = tokenAuthorization.substring(bearerTokenString.length());
            userEmail = jwtService.extractUserName(jwtToken);
            if(userEmail != null && !userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetails = userDetailsService.loadUserByUsername(userEmail);
                if(jwtService.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    request.setAttribute("currentUser_email", userEmail);
                }
            }
            filterChain.doFilter(request, response);
    }
}
