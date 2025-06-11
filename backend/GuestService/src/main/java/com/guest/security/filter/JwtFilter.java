package com.guest.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
//import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;

@Component 
public class JwtFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = "swIQ63HsdHIBaWnt1KmFXBOJhLJ9hElxsv/xzyqYkTQ213efaefwer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            // Create a Key object from the secret key

            final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key) 
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            String role = claims.get("role", String.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role)));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            System.out.println("Token received: " + token);
            System.out.println("Expected Secret: " + SECRET_KEY);

        }
        
        
        chain.doFilter(request, response);
    }

}
