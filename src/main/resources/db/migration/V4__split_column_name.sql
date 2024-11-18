-- Realizar el split del campo 'name' en 'name' y 'last_name'
UPDATE users
    SET last_name = SUBSTRING_INDEX(name, ' ', -1),
        name = SUBSTRING_INDEX(name, ' ', 1);