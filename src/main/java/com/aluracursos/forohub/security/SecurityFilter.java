package com.aluracursos.forohub.security;

import com.aluracursos.forohub.exceptions.JwtInvalidException;
import com.aluracursos.forohub.model.User;
import com.aluracursos.forohub.repository.UserRepository;
import com.aluracursos.forohub.service.JwtService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// La clase SecurityFilter es un filtro personalizado que se encarga de interceptar
// las solicitudes HTTP entrantes, extraer y validar el token JWT del encabezado de
// autorización, y configurar la autenticación en el contexto de seguridad de Spring
// si el token es válido.

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Se captura el valor del encabezado ‘Authorization’ de la solicitud HTTP del cliente.
        // Este encabezado debería contener el token JWT.
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extrae el token JWT eliminando el prefijo "Bearer " que llega por defecto.
            String jwtToken = authHeader.replace("Bearer ", "");
            // Decodifica el token JWT para extraer el email que opera como el nombre de usuario
            // Si retorna un valor significa que el JWT es válido
            String emailUser = jwtService.getSubject(jwtToken);
            if (emailUser != null) {
                // Busca el usuario en la base de datos usando el email
                User user = userRepository.findByEmail(emailUser);
                if (user != null) {
                    // Crea un token de autenticación con el usuario y sus autoridades
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );
                    // Configura el contexto de seguridad de Spring para que reconozca al usuario autenticado.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        // Pasa la solicitud y la respuesta al siguiente filtro en la cadena de filtros de Spring Security
        // que procesan la solicitud antes de que llegue al controlador
        filterChain.doFilter(request, response);
    }

}
