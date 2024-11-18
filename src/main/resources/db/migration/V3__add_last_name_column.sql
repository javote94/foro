-- Agregar la nueva columna 'last_name'
ALTER TABLE users
    ADD COLUMN last_name VARCHAR(100) NOT NULL AFTER name;