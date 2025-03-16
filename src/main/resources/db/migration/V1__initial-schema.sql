CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    document VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(300) NOT NULL,
    profile VARCHAR(50) NOT NULL,
    active BOOLEAN
);

CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    moderator_id BIGINT,
    active BOOLEAN,
    FOREIGN KEY (moderator_id) REFERENCES users(id)
);

CREATE TABLE users_courses (
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, course_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE topics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    message TEXT NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(100) NOT NULL,
    author_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    active BOOLEAN,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE responses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    message TEXT NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    solution BOOLEAN,
    topic_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    active BOOLEAN,
    FOREIGN KEY (topic_id) REFERENCES topics(id),
    FOREIGN KEY (author_id) REFERENCES users(id)
);

-- Agregar índices en claves foráneas para optimizar consultas
CREATE INDEX idx_topics_course ON topics(course_id);
CREATE INDEX idx_topics_author ON topics(author_id);
CREATE INDEX idx_responses_topic ON responses(topic_id);
CREATE INDEX idx_responses_author ON responses(author_id);
CREATE INDEX idx_users_courses_user ON users_courses(user_id);
CREATE INDEX idx_users_courses_course ON users_courses(course_id);