-- Creación de la base de datos si no existe
CREATE DATABASE  universidad_db;

-- Conexión a la base de datos
\c universidad_db;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL,
                                     username VARCHAR(50) NOT NULL UNIQUE,
                                     email VARCHAR(100) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de roles
CREATE TABLE IF NOT EXISTS roles (
                                     id BIGSERIAL PRIMARY KEY,
                                     name VARCHAR(20) NOT NULL
);

-- Tabla de relación usuarios-roles
CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT NOT NULL,
                                          role_id BIGINT NOT NULL,
                                          PRIMARY KEY (user_id, role_id),
                                          FOREIGN KEY (user_id) REFERENCES users(id),
                                          FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Tabla de alumnos
CREATE TABLE IF NOT EXISTS alumnos (
                                       id BIGSERIAL PRIMARY KEY,
                                       rut VARCHAR(12) NOT NULL UNIQUE,
                                       nombre VARCHAR(100) NOT NULL,
                                       direccion VARCHAR(255),
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de materias
CREATE TABLE IF NOT EXISTS materias (
                                        id BIGSERIAL PRIMARY KEY,
                                        nombre VARCHAR(100) NOT NULL,
                                        alumno_id BIGINT,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        FOREIGN KEY (alumno_id) REFERENCES alumnos(id)
);

-- Inserción de roles básicos
INSERT INTO roles (name) VALUES
                             ('ROLE_ADMIN'),
                             ('ROLE_CLIENT')
ON CONFLICT DO NOTHING;

-- Crear un usuario admin por defecto (password: admin123)
INSERT INTO users (name, username, email, password)
VALUES (
           'Patricio Administrador',
           'admin',
           'admin@universidad.com',
           'RDAwVFdqcWE3dDFBRUM5Wmgxb2M4QT09xdrQhr/AycAMyPQkduicpx1IcA+lUVuiYqC4llUt2f5WkwN/zVyEKTTnxUTwfiQw5CE='
       );

-- Asignar rol admin al usuario admin con pwd admin123
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

-- Asignar rol admin al usuario admin
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;