
INSERT INTO users (id, name, last_name, document, email, password, profile, active)
VALUES
(1, 'Michael', 'Smith', '38567490', 'michaelsmith@mail.com', '$2a$10$ifyMIwja3ycr4Elief9TquXbN53hUDOk5owNeO1yr.KklPHEgFlRS', 'USER', 1),  -- password: 123456
(2, 'Emily', 'Johnson', '24567890', 'emilyjohnson@mail.com', '$2a$10$u4swzoHhCUWSDYSa4dTviuJzvvoKlsdmPCs7Prr1QPtlS.o3ta7By', 'USER', 1),  -- password: abcdef
(3, 'James', 'Williams', '30567891', 'jameswilliams@mail.com', '$2a$10$a3AIgq4/DiWTbn450ZQGtObRhNIaw9t00sPadRaNIZbEfA.9uLBCy', 'USER', 1),  -- password: 123abc
(4, 'Olivia', 'Brown', '38567902', 'oliviabrown@mail.com', '$2a$10$qW4VnW3GVLeG9XVjjUw5TOESCJqdUng8vnXkXcZZwZWNZG8XLeTzS', 'USER', 1),  -- password: 654321
(5, 'William', 'Jones', '36567893', 'williamjones@mail.com', '$2a$10$0U5tZPRa9DfKYIUCwEd0a.fEmaBVM3Lwc9HaHiInKndUTUx4hCz92', 'USER', 1),  -- password: xyz123
(6, 'Sophia', 'Garcia', '38567894', 'sophiagarcia@mail.com', '$2a$10$u8TM.Vm6uUYsDt9zx7DnjedNofBk1g7Qr4MpjTdldn3NQY7r5B9Ju', 'USER', 1),  -- password: pass12
(7, 'Daniel', 'Martinez', '34567895', 'danielmartinez@mail.com', '$2a$10$5QBP7NlD.ZRme7ZMlxD7aOSti1vcIe9piYoFHcTQT/E/GtQqEf8ku', 'USER', 1),  -- password: qwerty
(8, 'Grace', 'Davis', '36567896', 'gracedavis@mail.com', '$2a$10$O0Fw6JqZ705NWsk9DISEc.6HNzidt7p5cJ1WqwsM2Q3qB57m6/l2q', 'USER', 1),  -- password: zxcvbn
(9, 'Henry', 'Wilson', '38567897', 'henrywilson@mail.com', '$2a$10$.WVZZrtlaGmeVK7UPCv/zO.0kFQVVfuTDrF3O0x8I4Dts8W3xCO0i', 'MODERATOR', 1),  -- password: 112233
(10, 'Alice', 'Taylor', '30567898', 'alicetaylor@mail.com', '$2a$10$OGu1Futo7Zys6n/hlKnRre05IKk4zSpzwS68ukJd6rxMA49sqDGIi', 'ADMIN', 1);  -- password: admin1

INSERT INTO courses (id, name, moderator_id, active)
VALUES
(1, 'Java Course', 9, 1),
(2, 'Python Course', 9, 1);

INSERT INTO users_courses (user_id, course_id)
VALUES
(1, 1), (1, 2),
(2, 1),
(3, 2),
(4, 1), (4, 2),
(5, 2),
(6, 1),
(7, 2),
(8, 1), (8, 2);

INSERT INTO topics (id, title, message, creation_date, status, author_id, course_id, active)
VALUES
(1, 'What is the purpose of the JVM?', 'Can someone explain how the JVM works in Java?', NOW(), 'UNSOLVED', 1, 1, 1),
(2, 'How to install Python packages?', 'I am having trouble using pip to install packages.', NOW(), 'UNSOLVED', 3, 2, 1),
(3, 'Java Streams vs Loops', 'When should I use Streams instead of traditional loops?', NOW(), 'UNSOLVED', 2, 1, 1),
(4, 'What is PEP8 in Python?', 'Why is PEP8 important and how can I follow it?', NOW(), 'UNSOLVED', 7, 2, 1),
(5, 'Abstract classes in Java', 'What are the key differences between abstract classes and interfaces?', NOW(), 'UNSOLVED', 4, 1, 1),
(6, 'How to handle exceptions in Python?', 'What is the proper way to use try-except blocks?', NOW(), 'UNSOLVED', 5, 2, 1);

INSERT INTO responses (id, message, creation_date, solution, topic_id, author_id, active)
VALUES
-- Topic 1
(1, 'The JVM allows Java code to be platform-independent.', NOW(), 1, 1, 9, 1),
(2, 'Think of JVM as a virtual machine that executes bytecode.', NOW(), 0, 1, 4, 1),
-- Topic 2
(3, 'Use pip install package-name in your terminal.', NOW(), 1, 2, 7, 1),
-- Topic 3
(4, 'Streams are great for functional-style code and chaining.', NOW(), 1, 3, 6, 1),
-- Topic 4
(5, 'PEP8 is a style guide; using linters helps you follow it.', NOW(), 1, 4, 8, 1),
-- Topic 5
(6, 'Abstract classes can have implementations; interfaces cannot.', NOW(), 0, 5, 1, 1),
(7, 'Interfaces support multiple inheritance, abstract classes do not.', NOW(), 1, 5, 9, 1),
-- Topic 6
(8, 'Use try-except-finally to manage errors cleanly.', NOW(), 1, 6, 3, 1);