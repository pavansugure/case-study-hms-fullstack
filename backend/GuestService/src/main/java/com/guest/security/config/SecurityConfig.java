package com.guest.security.config;

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

import com.guest.security.filter.JwtFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
        	.cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers(org.springframework.http.HttpMethod.POST,"/guests/add").hasAnyRole("OWNER", "MANAGER","RECEP") // Add guest: OWNER, RECEPTIONIST
            		.requestMatchers(org.springframework.http.HttpMethod.GET,"/guests").hasAnyRole("OWNER","MANAGER","RECEP") // Get all guests: OWNER, MANAGER
            		.requestMatchers(org.springframework.http.HttpMethod.GET,"/guests/{memberCode}").permitAll() // Get guest by ID: OWNER, MANAGER
            		.requestMatchers(org.springframework.http.HttpMethod.PUT,"/guests/update").hasAnyRole("OWNER", "MANAGER","RECEP") // Update guest: OWNER, MANAGER
            		.requestMatchers(org.springframework.http.HttpMethod.DELETE,"/guests/{memberCode}").hasAnyRole("OWNER","RECEP") // Delete guest: OWNER
            		.anyRequest().authenticated()  // Protect all other endpoints
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
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
