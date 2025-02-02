package com.aluracursos.forohub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable()) // Desactivar CSRF (porque usamos JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin estado
                .authorizeRequests()

                // Rutas públicas
                .requestMatchers(HttpMethod.POST, "/login", "/users").permitAll()
                .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()

                // Rutas protegidas
                // Tópicos y respuestas
                .requestMatchers(HttpMethod.POST, "/topics/**").hasAnyRole("USER", "MODERATOR", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/topics/{topicId}/status").hasAnyRole("MODERATOR", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/topics/{topicId}/active").hasAnyRole("MODERATOR", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/topics/{topicId}/content").hasRole("USER")
                .requestMatchers(HttpMethod.DELETE, "/topics/**").hasRole("ADMIN")

                // Cursos
                .requestMatchers("/courses/**").hasRole("ADMIN")

                // Cualquier otra solicitud necesita autenticación
                .anyRequest().authenticated()

                // Configurar filtro JWT antes del filtro de autenticación por defecto
                .and().addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
