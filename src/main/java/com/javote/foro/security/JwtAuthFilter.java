package com.javote.foro.security;

import com.javote.foro.entity.User;
import com.javote.foro.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

// La clase JwtAuthFilter es un filtro personalizado que se encarga de interceptar
// todas las solicitudes HTTP entrantes para validar el token JWT.
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Se captura el valor del encabezado ‘Authorization’ de la solicitud HTTP del cliente.
        // Este encabezado debería contener el token JWT.
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extrae el token JWT eliminando el prefijo "Bearer " que llega por defecto.
            String jwtToken = authHeader.replace("Bearer ", "");
            // Decodifica el token JWT para extraer el email que opera como el nombre de usuario
            // Si retorna un valor significa que el JWT es válido
            String userEmail = jwtService.extractUsername(jwtToken);
            if (userEmail != null) {
                // Busca el usuario en la base de datos usando el email
                Optional<User> userOptional = userRepository.findByEmail(userEmail);
                if (userOptional.isPresent()) {
                    // Crea un token de autenticación con el usuario y sus autoridades
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userOptional.get(),
                            null,
                            userOptional.get().getAuthorities()
                    );
                    // Asignar el usuario autenticado al contexto de seguridad del hilo actual
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        // Pasa la solicitud y la respuesta al siguiente filtro en la cadena de filtros de Spring Security
        // que procesan la solicitud antes de que llegue al controlador
        filterChain.doFilter(request, response);
    }

}
