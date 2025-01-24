-- Agregar la columna moderator_id a la tabla courses
ALTER TABLE courses ADD COLUMN moderator_id BIGINT;

-- Establecer la clave foránea entre courses y users
ALTER TABLE courses
ADD CONSTRAINT fk_moderator FOREIGN KEY (moderator_id) REFERENCES users(id);
