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

-- Crear usuario admin por defecto (password: Test1234!)
INSERT INTO users (name, username, email, password)
VALUES (
           'Patricio Clase',
           'admin',
           'admin@universidad.com',
           'cqS0HjfLUss6c0VQ0Oyo1/5qd0YYrRlxgihmmmQBnpe3qR3y29whY72xf/4OrrfT'
       ) ON CONFLICT (username) DO NOTHING;
INSERT INTO users (name, username, email, password)
VALUES (
           'Patricio objeto',
           'paofilia',
           'paofilia@universidad.com',
           'cqS0HjfLUss6c0VQ0Oyo1/5qd0YYrRlxgihmmmQBnpe3qR3y29whY72xf/4OrrfT'
       ) ON CONFLICT (username) DO NOTHING;
-- Asignar rol admin al usuario admin
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         CROSS JOIN roles r
WHERE u.username = 'admin'
  AND r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

insert into user_roles values (2,1);
-- Insertar alumnos de prueba
INSERT INTO alumnos (rut, nombre, direccion, created_at) VALUES ('15487962-5', 'Ana María González', 'Av. Providencia 1234, Santiago', '2024-01-15 08:30:00');
INSERT INTO alumnos (rut, nombre, direccion, created_at) VALUES ('16857324-8', 'Juan Carlos Pérez', 'Los Alerces 567, Viña del Mar', '2024-01-16 09:15:00');
INSERT INTO alumnos (rut, nombre, direccion, created_at) VALUES ('12543876-2', 'María José Rodríguez', 'Manuel Montt 890, Temuco', '2024-01-17 10:45:00');
INSERT INTO alumnos (rut, nombre, direccion, created_at) VALUES ('11987432-1', 'Pedro Antonio Silva', 'O''Higgins 123, Concepción', '2024-01-18 11:20:00');
INSERT INTO alumnos (rut, nombre, direccion, created_at) VALUES ('18432765-9', 'Carolina Andrea Muñoz', 'Los Carrera 456, Rancagua', '2024-01-19 12:00:00');
INSERT INTO alumnos (rut, nombre, direccion, created_at) VALUES ('14327658-3', 'Roberto Andrés Soto', 'Arturo Prat 789, Antofagasta', '2024-01-20 13:30:00');
INSERT INTO alumnos (rut, nombre, direccion, created_at) VALUES ('17654321-4', 'Daniela Paz Vega', 'Bulnes 234, Valdivia', '2024-01-21 14:15:00');
INSERT INTO alumnos (rut, nombre, direccion, created_at) VALUES ('13567432-6', 'Felipe Ignacio Torres', 'Condell 567, Iquique', '2024-01-22 15:45:00');
INSERT INTO alumnos (rut, nombre, direccion, created_at) VALUES ('19876543-5', 'Valentina Belén Castro', 'Maipú 890, Puerto Montt', '2024-01-23 16:20:00');
INSERT INTO alumnos (rut, nombre, direccion, created_at) VALUES ('15234987-k', 'Sebastián Alejandro López', 'Freire 123, La Serena', '2024-01-24 17:00:00');

-- Materias para Ana María González (15487962-5)
INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Cálculo I', id, '2024-01-15 09:00:00'
FROM alumnos WHERE rut = '15487962-5';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Física I', id, '2024-01-15 09:30:00'
FROM alumnos WHERE rut = '15487962-5';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Programación I', id, '2024-01-15 10:00:00'
FROM alumnos WHERE rut = '15487962-5';

-- Materias para Juan Carlos Pérez (16857324-8)
INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Álgebra Lineal', id, '2024-01-16 09:00:00'
FROM alumnos WHERE rut = '16857324-8';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Química General', id, '2024-01-16 09:30:00'
FROM alumnos WHERE rut = '16857324-8';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Inglés Técnico', id, '2024-01-16 10:00:00'
FROM alumnos WHERE rut = '16857324-8';

-- Materias para María José Rodríguez (12543876-2)
INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Cálculo II', id, '2024-01-17 09:00:00'
FROM alumnos WHERE rut = '12543876-2';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Física II', id, '2024-01-17 09:30:00'
FROM alumnos WHERE rut = '12543876-2';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Programación II', id, '2024-01-17 10:00:00'
FROM alumnos WHERE rut = '12543876-2';

-- Materias para Pedro Antonio Silva (11987432-1)
INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Estadística', id, '2024-01-18 09:00:00'
FROM alumnos WHERE rut = '11987432-1';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Base de Datos', id, '2024-01-18 09:30:00'
FROM alumnos WHERE rut = '11987432-1';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Sistemas Operativos', id, '2024-01-18 10:00:00'
FROM alumnos WHERE rut = '11987432-1';

-- Materias para Carolina Andrea Muñoz (18432765-9)
INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Redes', id, '2024-01-19 09:00:00'
FROM alumnos WHERE rut = '18432765-9';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Algoritmos', id, '2024-01-19 09:30:00'
FROM alumnos WHERE rut = '18432765-9';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Ingeniería de Software', id, '2024-01-19 10:00:00'
FROM alumnos WHERE rut = '18432765-9';

-- Materias para Roberto Andrés Soto (14327658-3)
INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Arquitectura de Computadores', id, '2024-01-20 09:00:00'
FROM alumnos WHERE rut = '14327658-3';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Inteligencia Artificial', id, '2024-01-20 09:30:00'
FROM alumnos WHERE rut = '14327658-3';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Desarrollo Web', id, '2024-01-20 10:00:00'
FROM alumnos WHERE rut = '14327658-3';

-- Materias para Daniela Paz Vega (17654321-4)
INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Seguridad Informática', id, '2024-01-21 09:00:00'
FROM alumnos WHERE rut = '17654321-4';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Cloud Computing', id, '2024-01-21 09:30:00'
FROM alumnos WHERE rut = '17654321-4';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'DevOps', id, '2024-01-21 10:00:00'
FROM alumnos WHERE rut = '17654321-4';

-- Materias para Felipe Ignacio Torres (13567432-6)
INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Machine Learning', id, '2024-01-22 09:00:00'
FROM alumnos WHERE rut = '13567432-6';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Big Data', id, '2024-01-22 09:30:00'
FROM alumnos WHERE rut = '13567432-6';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Data Mining', id, '2024-01-22 10:00:00'
FROM alumnos WHERE rut = '13567432-6';

-- Materias para Valentina Belén Castro (19876543-5)
INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Desarrollo Móvil', id, '2024-01-23 09:00:00'
FROM alumnos WHERE rut = '19876543-5';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'UX/UI', id, '2024-01-23 09:30:00'
FROM alumnos WHERE rut = '19876543-5';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Gestión de Proyectos', id, '2024-01-23 10:00:00'
FROM alumnos WHERE rut = '19876543-5';

-- Materias para Sebastián Alejandro López (15234987-k)
INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Metodologías Ágiles', id, '2024-01-24 09:00:00'
FROM alumnos WHERE rut = '15234987-k';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Testing de Software', id, '2024-01-24 09:30:00'
FROM alumnos WHERE rut = '15234987-k';

INSERT INTO materias (nombre, alumno_id, created_at)
SELECT 'Arquitectura de Software', id, '2024-01-24 10:00:00'
FROM alumnos WHERE rut = '15234987-k';