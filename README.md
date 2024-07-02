# 🗨️ ForoHub API

![Status](https://img.shields.io/badge/status-en%20preparación-yellow)
![Last Commit](https://img.shields.io/badge/last%20commit-Julio%202024-blue)
![Java Version](https://img.shields.io/badge/Java-JDK%2017-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.1-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-v8.0-blue)
![IDE](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-purple)

## Índice
1. [Descripción del proyecto](#descripción-del-proyecto)
2. [Funcionalidades](#funcionalidades)
3. [Dependencias de Maven](#dependencias-de-maven)
4. [Estructura del proyecto](#estructura-del-proyecto)
5. [Cómo correr la aplicación](#cómo-correr-la-aplicación)
6. [Contribuciones](#contribuciones)
7. [Agradecimientos](#agradecimientos)

## Descripción del proyecto
ForoHub API es una aplicación backend desarrollada con el framework Spring que permite la gestión de un foro de preguntas y respuestas. Los usuarios pueden registrarse e iniciar sesión mediante autenticación JWT para crear, actualizar, listar y eliminar tópicos, así como realizar consultas específicas a través de endpoints protegidos.

## Funcionalidades
- **Registro de usuarios**: Permite registrar nuevos usuarios.

- **Autenticación de usuarios**: Genera y valida tokens JWT para autenticación.

- **Gestión de tópicos**: Crear, actualizar, listar y eliminar tópicos del foro.

- **Búsqueda de tópicos**: Consultar tópicos por nombre de curso y año específico.

- **Documentación de API**: Integración con Swagger para documentación interactiva

## Dependencias de Maven
- **spring-boot-starter-data-jpa**: Proporciona integración con Spring Data JPA para la gestión de datos y operaciones CRUD.

- **spring-boot-starter-security**: Añade funcionalidades de seguridad, incluyendo autenticación y autorización.

- **spring-boot-starter-validation**: Ofrece soporte para la validación de datos de entrada con Hibernate Validator.

- **spring-boot-starter-web**: Facilita la creación de aplicaciones web RESTful con Spring MVC.

- **flyway-core**: Herramienta de migración de base de datos para gestionar cambios en el esquema.

- **flyway-mysql**: Extensión de Flyway para soporte específico de MySQL.

- **mysql-connector-j**: Conector JDBC para conectarse a una base de datos MySQL.

- **lombok**: Biblioteca para reducir el boilerplate en el código Java a través de anotaciones.

- **spring-boot-starter-test**: Proporciona dependencias necesarias para pruebas unitarias y de integración en Spring Boot.

- **spring-security-test**: Añade soporte para pruebas de aplicaciones Spring Security.

- **java-jwt**: Biblioteca para crear y verificar JSON Web Tokens (JWT).

- **springdoc-openapi-starter-webmvc-ui**: Integra Swagger UI para generar documentación interactiva de la API.


## Estructura del proyecto
El proyecto está estructurado en varios paquetes, diseñados para separar las responsabilidades y facilitar el mantenimiento y la escalabilidad:

- **`com.aluracursos.forohub`**: Contiene la clase principal `ForohubApplication` que sirve como punto de entrada del programa.

- **`model`**: Contiene las entidades que representan los datos almacenados en la base de datos. Ejemplos: `Course`, `Response`, `Topic`, `User`.

- **`repository`**: Define los repositorios para la interacción con la base de datos. Estos repositorios extienden `JpaRepository` para proporcionar operaciones CRUD y consultas personalizadas. Ejemplos: `CourseRepository`, `ResponseRepository`, `TopicRepository`, `UserRepository`.

- **`service`**: Contiene las clases de servicios que encapsulan la lógica de negocio. Ejemplos: `CourseService`, `TopicService`, `UserService`, `JwtService`.

- **`controller`**: Contiene las clases de controladores que gestionan las solicitudes HTTP y devuelven las respuestas adecuadas. Ejemplos: `AuthenticationController`, `CourseController`, `TopicController`, `UserController`.

- **`dtos`**: Define los Data Transfer Objects (DTOs) utilizados para transferir datos entre la aplicación y los clientes. Ejemplos: `AuthUserDTO`, `JwtTokenDTO`, `SaveCourseDTO`, `TopicInfoDTO`.

- **`security`**: Contiene las configuraciones y filtros de seguridad para manejar la autenticación y autorización en la API. Ejemplos: `AuthSecurityConfig`, `HttpSecurityConfig`, `SecurityFilter`.

- **`enums`**: Define enumeraciones utilizadas en la aplicación para representar valores constantes. Ejemplos: `Profile`, `Status`.

- **`exceptions`**: Define las excepciones personalizadas utilizadas en la aplicación para manejar errores específicos. Ejemplos: `JwtInvalidException`, `TopicNotFoundException`, `GlobalExceptionHandler`.

- **`springdoc`**: Contiene la configuración de Springdoc OpenAPI para la generación de la documentación de la API.

- **`resources`**: 
  - **`application.properties`**: Archivo de configuración de la aplicación.

  - **`db.migration`**: Carpeta que contiene los scripts de migración de Flyway, por ejemplo, `V1__initial-schema.sql`, `V2__insert-into-tables.sql`.


## Cómo correr la aplicación
Para correr la aplicación en tu computadora, sigue estos pasos:

1. **Clonar el repositorio**
    - Usa Git para clonar el repositorio en tu entorno local:
    ```bash
    git clone https://github.com/javote94/forohub.git
    ```

2. **Configuración del archivo `application.properties`**
    - Crear un archivo `application.properties` en el directorio `src/main/resources`.
    
    - Configurar las propiedades de la base de datos y la clave secreta JWT según tu entorno local.

    ```properties
    spring.application.name=forohub
    spring.datasource.url=jdbc:mysql://localhost:3306/forohub
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.flyway.enabled=true
    spring.flyway.locations=classpath:db/migration
    spring.mvc.throw-exception-if-no-handler-found=true
    spring.web.resources.add-mappings=false
    api.security.secret=your_secret_key
    ```

3. **Preparación del entorno de trabajo**
   - Asegúrate de tener instalado Java JDK 17. Si no, puedes descargarlo e instalarlo desde el sitio web de [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

   - Crea una base de datos MySQL llamada `forohub`. Si no tienes MySQL instalado, puedes descargarlo desde el sitio web oficial de [MySQL](https://dev.mysql.com/downloads/installer/).

   - También se recomienda utilizar IntelliJ IDEA para abrir y ejecutar el proyecto. Puedes descargarlo desde el sitio web de [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).

4. **Ejecución del proyecto**
    - Abre el proyecto en IntelliJ IDEA.
    - Ejecuta la clase `ForohubApplication` para iniciar la aplicación.


### Consideraciones adicionales
- **Documentación interactiva**: Puedes acceder a la documentación interactiva de la API en `http://localhost:8080/swagger-ui.html`. Esta documentación generada automáticamente por Swagger proporciona una interfaz fácil de usar para explorar los diferentes endpoints disponibles, así como los campos necesarios para completar las solicitudes HTTP. Es una herramienta útil para comprender mejor cómo interactuar con la API y probar diferentes operaciones.



## Contribuciones
Este proyecto está en preparación. Cualquier feedback es bienvenido y si estás interesado en contribuir, estamos abiertos a pull requests o puedes [abrir un issue](https://github.com/javote94/forohub/issues) para discutir posibles cambios.

## Agradecimientos
Mis agradecimientos a las organizaciones Alura Latam y Oracle Next Education por proporcionar el contexto educativo para el desarrollo del proyecto. El apoyo y recursos de formación brindados han sido fundamentales en la realización de esta aplicación.

