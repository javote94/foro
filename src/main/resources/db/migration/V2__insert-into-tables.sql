
-- Insertar registros en la tabla users
INSERT INTO users (id, name, document, email, password, profile, active)
VALUES
(1, 'Javier Rameri', '38567490', 'javierrameri@mail.com', '$2a$10$ifyMIwja3ycr4Elief9TquXbN53hUDOk5owNeO1yr.KklPHEgFlRS', 'USER', 1),  -- password: 123456
(2, 'Ana Gomez', '24567890', 'anagomez@mail.com', '$2a$10$u4swzoHhCUWSDYSa4dTviuJzvvoKlsdmPCs7Prr1QPtlS.o3ta7By', 'USER', 1), -- password: abcdef
(3, 'Carlos Lopez', '30567891', 'carloslopez@mail.com', '$2a$10$a3AIgq4/DiWTbn450ZQGtObRhNIaw9t00sPadRaNIZbEfA.9uLBCy', 'USER', 1), -- password: 123abc
(4, 'Beatriz Martinez', '38567902', 'beatrizmartinez@mail.com', '$2a$10$qW4VnW3GVLeG9XVjjUw5TOESCJqdUng8vnXkXcZZwZWNZG8XLeTzS', 'USER', 1), -- password: 654321
(5, 'Daniel Suarez', '36567893', 'danielsuarez@mail.com', '$2a$10$0U5tZPRa9DfKYIUCwEd0a.fEmaBVM3Lwc9HaHiInKndUTUx4hCz92', 'USER', 1), -- password: xyz123
(6, 'Elena Fernandez', '38567894', 'elenaf@mail.com', '$2a$10$u8TM.Vm6uUYsDt9zx7DnjedNofBk1g7Qr4MpjTdldn3NQY7r5B9Ju', 'USER', 1), -- password: pass12
(7, 'Fernando Alvarez', '34567895', 'fernandoalvarez@mail.com', '$2a$10$5QBP7NlD.ZRme7ZMlxD7aOSti1vcIe9piYoFHcTQT/E/GtQqEf8ku', 'USER', 1), -- password: qwerty
(8, 'Gabriela Torres', '36567896', 'gabrielatorres@mail.com', '$2a$10$O0Fw6JqZ705NWsk9DISEc.6HNzidt7p5cJ1WqwsM2Q3qB57m6/l2q', 'USER', 1), -- password: zxcvbn
(9, 'Hector Mendez', '38567897', 'hectormendez@mail.com', '$2a$10$.WVZZrtlaGmeVK7UPCv/zO.0kFQVVfuTDrF3O0x8I4Dts8W3xCO0i', 'MODERATOR', 1), -- password: 112233
(10, 'Isabel Ramirez', '30567898', 'isabelramirez@mail.com', '$2a$10$OGu1Futo7Zys6n/hlKnRre05IKk4zSpzwS68ukJd6rxMA49sqDGIi', 'ADMIN', 1); -- password: admin1

-- Insertar registros en la tabla courses
INSERT INTO courses (id, name, active)
VALUES
(1, 'Curso Java', 1),
(2, 'Curso Python', 1),
(3, 'Curso Spring Boot', 1);

-- Insertar registros en la tabla topics
INSERT INTO topics (id, title, message, creation_date, status, author_id, course_id, active)
VALUES
(1, 'Consulta acerca del if condicional', 'Tengo dudas sobre la forma en que debo escribir el condicional if-else', '2024-06-27 23:28:50', 'UNSOLVED', 1, 1, 1),
(2, 'Problemas con bucles en Python', 'No entiendo cómo funcionan los bucles for y while en Python', '2023-05-15 14:22:30', 'UNSOLVED', 2, 2, 1),
(3, 'Errores al compilar en Java', 'Estoy obteniendo errores de compilación que no comprendo', '2023-08-12 09:45:10', 'UNSOLVED', 3, 1, 1),
(4, 'Dudas sobre herencia en Java', 'No entiendo cómo implementar herencia en Java', '2024-01-10 16:30:25', 'UNSOLVED', 4, 1, 1),
(5, 'Cómo instalar Spring Boot', 'No sé cómo instalar Spring Boot en mi máquina', '2023-12-20 11:10:45', 'UNSOLVED', 5, 3, 1),
(6, 'Configuración de entorno en Python', 'Tengo problemas para configurar mi entorno de desarrollo en Python', '2023-03-22 19:35:50', 'UNSOLVED', 6, 2, 1),
(7, 'Errores en el uso de listas en Python', 'Estoy obteniendo errores al manipular listas en Python', '2024-04-05 10:15:35', 'UNSOLVED', 7, 2, 1),
(8, 'Problemas con interfaces en Java', 'No comprendo cómo funcionan las interfaces en Java', '2023-07-18 08:50:20', 'UNSOLVED', 8, 1, 1),
(9, 'Errores en la configuración de Spring Boot', 'Tengo problemas para configurar Spring Boot', '2024-05-25 13:45:55', 'UNSOLVED', 9, 3, 1),
(10, 'Cómo utilizar streams en Java', 'Necesito ayuda para entender el uso de streams en Java', '2024-02-14 17:05:10', 'UNSOLVED', 10, 1, 1),
(11, 'Consultas sobre bases de datos en Python', 'No sé cómo realizar consultas a bases de datos en Python', '2023-11-02 15:25:40', 'UNSOLVED', 1, 2, 1),
(12, 'Dudas sobre anotaciones en Spring Boot', 'Quisiera saber más sobre las anotaciones en Spring Boot', '2024-03-19 20:50:30', 'UNSOLVED', 2, 3, 1),
(13, 'Problemas con multithreading en Java', 'No entiendo cómo implementar multithreading en Java', '2023-09-21 09:10:05', 'UNSOLVED', 3, 1, 1),
(14, 'Cómo manejar excepciones en Python', 'Tengo dudas sobre el manejo de excepciones en Python', '2024-01-30 12:40:20', 'UNSOLVED', 4, 2, 1),
(15, 'Consultas sobre inyección de dependencias en Spring Boot', 'Necesito entender mejor la inyección de dependencias en Spring Boot', '2024-06-05 18:35:15', 'UNSOLVED', 5, 3, 1);
