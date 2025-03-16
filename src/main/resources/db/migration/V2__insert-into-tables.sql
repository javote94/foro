-- Insertar registros en la tabla users (MANTENIENDO HASHES ORIGINALES)
INSERT INTO users (id, name, last_name, document, email, password, profile, active)
VALUES
(1, 'Javier', 'Rameri', '38567490', 'javierrameri@mail.com', '$2a$10$ifyMIwja3ycr4Elief9TquXbN53hUDOk5owNeO1yr.KklPHEgFlRS', 'USER', 1),  -- password: 123456
(2, 'Ana', 'Gomez', '24567890', 'anagomez@mail.com', '$2a$10$u4swzoHhCUWSDYSa4dTviuJzvvoKlsdmPCs7Prr1QPtlS.o3ta7By', 'USER', 1), -- password: abcdef
(3, 'Carlos', 'Lopez', '30567891', 'carloslopez@mail.com', '$2a$10$a3AIgq4/DiWTbn450ZQGtObRhNIaw9t00sPadRaNIZbEfA.9uLBCy', 'USER', 1), -- password: 123abc
(4, 'Beatriz', 'Martinez', '38567902', 'beatrizmartinez@mail.com', '$2a$10$qW4VnW3GVLeG9XVjjUw5TOESCJqdUng8vnXkXcZZwZWNZG8XLeTzS', 'USER', 1), -- password: 654321
(5, 'Daniel', 'Suarez', '36567893', 'danielsuarez@mail.com', '$2a$10$0U5tZPRa9DfKYIUCwEd0a.fEmaBVM3Lwc9HaHiInKndUTUx4hCz92', 'USER', 1), -- password: xyz123
(6, 'Elena', 'Fernandez', '38567894', 'elenaf@mail.com', '$2a$10$u8TM.Vm6uUYsDt9zx7DnjedNofBk1g7Qr4MpjTdldn3NQY7r5B9Ju', 'USER', 1), -- password: pass12
(7, 'Fernando', 'Alvarez', '34567895', 'fernandoalvarez@mail.com', '$2a$10$5QBP7NlD.ZRme7ZMlxD7aOSti1vcIe9piYoFHcTQT/E/GtQqEf8ku', 'USER', 1), -- password: qwerty
(8, 'Gabriela', 'Torres', '36567896', 'gabrielatorres@mail.com', '$2a$10$O0Fw6JqZ705NWsk9DISEc.6HNzidt7p5cJ1WqwsM2Q3qB57m6/l2q', 'USER', 1), -- password: zxcvbn
(9, 'Hector', 'Mendez', '38567897', 'hectormendez@mail.com', '$2a$10$.WVZZrtlaGmeVK7UPCv/zO.0kFQVVfuTDrF3O0x8I4Dts8W3xCO0i', 'MODERATOR', 1), -- password: 112233
(10, 'Isabel', 'Ramirez', '30567898', 'isabelramirez@mail.com', '$2a$10$OGu1Futo7Zys6n/hlKnRre05IKk4zSpzwS68ukJd6rxMA49sqDGIi', 'ADMIN', 1); -- password: admin1

-- Insertar cursos
INSERT INTO courses (id, name, moderator_id, active)
VALUES
(1, 'Spring Boot Avanzado', 9, 1),   -- Moderador: Héctor Méndez
(2, 'Java desde Cero', 9, 1),        -- Moderador: Héctor Méndez
(3, 'Arquitectura de Software', 9, 1); -- Moderador: Héctor Méndez

-- Insertar inscripciones de usuarios en cursos (SOLO `USER`)
INSERT INTO users_courses (user_id, course_id)
VALUES
(1, 1), (2, 1), (3, 1), (5, 1), (6, 1), (7, 1), (8, 1),  -- Curso 1: Spring Boot Avanzado
(1, 2), (3, 2), (5, 2), (6, 2), (7, 2), (8, 2),          -- Curso 2: Java desde Cero
(2, 3), (3, 3), (5, 3), (6, 3), (7, 3), (8, 3);          -- Curso 3: Arquitectura de Software

-- Insertar tópicos en los cursos
INSERT INTO topics (id, title, message, creation_date, status, author_id, course_id, active)
VALUES
(1, 'Error con dependencias en Spring Boot', 'No puedo resolver las versiones de las dependencias en mi `pom.xml`.', NOW(), 'UNSOLVED', 1, 1, 1),
(2, '¿Cómo manejar excepciones en Java?', '¿Cuál es la mejor forma de manejar excepciones en un proyecto grande?', NOW(), 'UNSOLVED', 2, 2, 1),
(3, 'Arquitectura Hexagonal vs Clean Architecture', '¿Cuál recomiendan para proyectos grandes?', NOW(), 'UNSOLVED', 3, 3, 1),
(4, 'Problema con inyección de dependencias en Spring', 'Tengo problemas con `@Autowired`, no funciona correctamente.', NOW(), 'UNSOLVED', 5, 1, 1),
(5, 'Dudas sobre JDBC y JPA', '¿Cuál es mejor en términos de rendimiento?', NOW(), 'UNSOLVED', 6, 2, 1),
(6, '¿MVP es recomendable en arquitectura de software?', '¿En qué casos aplicaría mejor MVP que MVC?', NOW(), 'UNSOLVED', 7, 3, 1);

-- Insertar respuestas en los tópicos
INSERT INTO responses (id, message, creation_date, solution, topic_id, author_id, active)
VALUES
-- Respuestas al tópico 1
(1, 'Verifica que las versiones sean compatibles entre sí.', NOW(), 0, 1, 9, 1),  -- Responde el moderador
(2, 'Intenta limpiar el caché de Maven con `mvn clean install`.', NOW(), 1, 1, 6, 1),

-- Respuestas al tópico 2
(3, 'Usa excepciones personalizadas para un mejor control.', NOW(), 0, 2, 5, 1),
(4, 'Evita capturar excepciones genéricas como `Exception`.', NOW(), 1, 2, 7, 1),

-- Respuestas al tópico 3
(5, 'Arquitectura Hexagonal ayuda a desacoplar mejor las capas.', NOW(), 0, 3, 2, 1),
(6, 'Clean Architecture es más completa, pero también más compleja.', NOW(), 1, 3, 8, 1),

-- Respuestas al tópico 4
(7, 'Verifica si tienes múltiples `ApplicationContext` en tu proyecto.', NOW(), 0, 4, 9, 1),  -- Moderador
(8, 'Intenta agregar `@Primary` en la configuración de beans.', NOW(), 1, 4, 6, 1),

-- Respuestas al tópico 5
(9, 'JPA es más fácil de usar, pero JDBC es más rápido en consultas simples.', NOW(), 0, 5, 3, 1),
(10, 'Si necesitas alta escalabilidad, JDBC puede ser mejor.', NOW(), 1, 5, 1, 1),

-- Respuestas al tópico 6
(11, 'MVP es útil si quieres separar mejor la lógica de la UI.', NOW(), 0, 6, 2, 1),
(12, 'En aplicaciones grandes, a veces es mejor usar MVVM.', NOW(), 1, 6, 8, 1);
