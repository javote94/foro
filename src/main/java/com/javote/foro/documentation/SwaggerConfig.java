package com.javote.foro.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .info(new Info()
                        .title("Academic Forum API")
                        .description("""
                                Academic Forum API is a RESTful service designed to manage an academic discussion forum within the context of virtual courses.
                                It enables users to create topics and post responses, promoting collaboration and learning among participants.
                                The system implements role-based access control (User, Moderator, Administrator), ensuring that each role has appropriate permissions and a secure, streamlined experience tailored to their responsibilities.
                                """)
                        .contact(new Contact()
                                .name("Javier Rameri")
                                .url("https://github.com/javote94")
                        )
                );
    }

    @Bean
    public String message() {
        return "bearer is working";
    }


}
