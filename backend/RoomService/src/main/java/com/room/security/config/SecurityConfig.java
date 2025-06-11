package com.room.security.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.room.security.filter.JwtFilter;

import feign.Request.HttpMethod;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
        .cors(Customizer.withDefaults()).authorizeHttpRequests(auth -> auth
                    .requestMatchers(org.springframework.http.HttpMethod.POST, "/rooms").hasAnyRole("OWNER", "MANAGER")                // Add room
                    .requestMatchers(org.springframework.http.HttpMethod.PUT,"/rooms").hasAnyRole("OWNER", "MANAGER")                  // Update room
                    .requestMatchers(org.springframework.http.HttpMethod.GET,"/rooms/{id}").permitAll()             // Get room by ID
                    .requestMatchers(org.springframework.http.HttpMethod.GET,"/rooms/available").hasAnyRole("OWNER", "MANAGER")        // Check room availability
                    .requestMatchers(org.springframework.http.HttpMethod.GET,"/rooms/available-rooms").hasAnyRole("OWNER", "MANAGER")  // Get all available rooms
                    .requestMatchers(org.springframework.http.HttpMethod.DELETE,"/rooms/{id}").hasAnyRole("OWNER", "MANAGER") 
                .anyRequest().authenticated()  // Protect all other endpoints
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); // Frontend URL
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}
