package com.seleniumtraining.seleniumapp;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                // .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/public").permitAll()
                                                .requestMatchers("/api/secret")
                                                .hasAnyAuthority("SCOPE_read:yritykset", "SCOPE_admin:all")
                                                .requestMatchers("/api/yritykset")
                                                .hasAnyAuthority("SCOPE_read:yritykset", "SCOPE_admin:all")
                                                .requestMatchers("/api/editYritys/{id}")
                                                .hasAnyAuthority("SCOPE_write:yritykset", "SCOPE_admin:all")
                                                .requestMatchers("/api/deleteYritys/{id}")
                                                .hasAnyAuthority("SCOPE_write:yritykset", "SCOPE_admin:all")
                                                .anyRequest().authenticated())
                                .oauth2ResourceServer(oauth2 -> oauth2
                                                .jwt(Customizer.withDefaults()));
                return http.build();
        }

        // määritellään mistä osoitteista pyyntöjä voidaan tehdä

        // @Bean
        // UrlBasedCorsConfigurationSource corsConfigurationSource() {
        // CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
        // configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        // configuration.setAllowedHeaders(Arrays.asList("Authorization"));
        // UrlBasedCorsConfigurationSource source = new
        // UrlBasedCorsConfigurationSource();
        // source.registerCorsConfiguration("/**", configuration);
        // return source;
        // }
}
