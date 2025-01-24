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
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable()) // Desactivar CSRF (porque usamos JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin estado
                .authorizeRequests()

                // Rutas públicas (accesibles sin autenticación)
                .requestMatchers(HttpMethod.POST, "/login", "/users").permitAll()
                .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()

                // Rutas protegidas según roles
                // Usuarios autenticados (USER, MODERATOR, ADMIN) pueden crear/responder tópicos
                .requestMatchers(HttpMethod.POST, "/topics/**").hasAnyRole("USER", "MODERATOR", "ADMIN")

                // Solo MODERATOR o ADMIN pueden modificar un tópico (por ejemplo, cambiar su estado)
                .requestMatchers(HttpMethod.PUT, "/topics/**").hasAnyRole("MODERATOR", "ADMIN")

                // Solo ADMIN puede eliminar un tópico
                .requestMatchers(HttpMethod.DELETE, "/topics/**").hasRole("ADMIN")

                // Solo ADMIN puede gestionar cursos
                .requestMatchers("/courses/**").hasRole("ADMIN")

                // Cualquier otra solicitud necesita autenticación
                .anyRequest().authenticated()

                // Configurar filtro JWT antes del filtro de autenticación por defecto
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
