-- Eliminar tablas si existen (en orden inverso debido a las dependencias)
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS materias CASCADE;
DROP TABLE IF EXISTS alumnos CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Tabla de usuarios
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de roles
CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(20) NOT NULL UNIQUE
);

-- Tabla de relación usuarios-roles
CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Tabla de alumnos
CREATE TABLE alumnos (
                         id BIGSERIAL PRIMARY KEY,
                         rut VARCHAR(12) NOT NULL UNIQUE,
                         nombre VARCHAR(100) NOT NULL,
                         direccion VARCHAR(255),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de materias
CREATE TABLE materias (
                          id BIGSERIAL PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          alumno_id BIGINT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (alumno_id) REFERENCES alumnos(id) ON DELETE CASCADE
);

-- Inserción de roles básicos
INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'), ('ROLE_CLIENT')
ON CONFLICT (name) DO NOTHING;

-- Crear usuario admin por defecto (password: admin123)
INSERT INTO users (name, username, email, password)
VALUES (
           'Patricio Clase',
           'admin',
           'admin@universidad.com',
           'RDAwVFdqcWE3dDFBRUM5Wmgxb2M4QT09xdrQhr/AycAMyPQkduicpx1IcA+lUVuiYqC4llUt2f5WkwN/zVyEKTTnxUTwfiQw5CE='
       ) ON CONFLICT (username) DO NOTHING;
INSERT INTO users (name, username, email, password)
VALUES (
           'Patricio objeto',
           'paofilia',
           'paofilia@universidad.com',
           'RDAwVFdqcWE3dDFBRUM5Wmgxb2M4QT09xdrQhr/AycAMyPQkduicpx1IcA+lUVuiYqC4llUt2f5WkwN/zVyEKTTnxUTwfiQw5CE='
       ) ON CONFLICT (username) DO NOTHING;
-- Asignar rol admin al usuario admin
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         CROSS JOIN roles r
WHERE u.username = 'admin'
  AND r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;