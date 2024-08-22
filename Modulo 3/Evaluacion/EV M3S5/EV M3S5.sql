CREATE TABLE "empresa" (
  "rut" VARCHAR(10) PRIMARY KEY,
  "nombre" VARCHAR(120),
  "direccion" VARCHAR(120),
  "telefono" VARCHAR(15),
  "correo" varchar(80),
  "web" varchar(50)
);

CREATE TABLE "cliente" (
  "rut" VARCHAR(10) PRIMARY KEY,
  "nombre" VARCHAR(120),
  "correo" varchar(80),
  "celular" VARCHAR(15)
);

CREATE TABLE "herramientas" (
  "id_herramienta" INT PRIMARY KEY,
  "nombre" VARCHAR(120),
  "precio_dia" int
);

CREATE TABLE "arriendo" (
  "folio" int PRIMARY KEY,
  "fecha" date,
  "dias" int,
  "valor_dia" int,
  "garantia" VARCHAR(30),
  "herramienta_id_herramienta" int,
  "cliente_rut" varchar(10)
);

ALTER TABLE "arriendo" ADD FOREIGN KEY ("cliente_rut") REFERENCES "cliente" ("rut");

ALTER TABLE "arriendo" ADD FOREIGN KEY ("herramienta_id_herramienta") REFERENCES "herramientas" ("id_herramienta");


-- Inserciones para la tabla "empresa"
INSERT INTO empresa (rut, nombre, direccion, telefono, correo, web) VALUES
('7612345-6', 'Herramientas Express', 'Av. Principal 123, Santiago', '+56223456789', 'contacto@herramientasexpress.cl', 'www.herramientasexpress.cl'),
('7723456-7', 'ToolRent Chile', 'Calle Comercio 456, Valparaíso', '+56329876543', 'info@toolrent.cl', 'www.toolrent.cl'),
('7834567-8', 'Arrienda Fácil', 'Av. Libertad 789, Concepción', '+56412765432', 'contacto@arriendafacil.cl', 'www.arriendafacil.cl'),
('7945678-9', 'Construye Ya', 'Calle Nueva 321, La Serena', '+56512345678', 'info@construyeya.cl', 'www.construyeya.cl'),
('8056789-0', 'Herramientas Pro', 'Av. Central 654, Antofagasta', '+56552987654', 'contacto@herramientaspro.cl', 'www.herramientaspro.cl'),
('8167890-1', 'ToolMaster', 'Calle Mayor 987, Temuco', '+56452765432', 'info@toolmaster.cl', 'www.toolmaster.cl'),
('8278901-2', 'Arriendos Sur', 'Av. Costanera 159, Puerto Montt', '+56652345678', 'contacto@arriendossur.cl', 'www.arriendossur.cl'),
('8389012-3', 'Herramientas 24/7', 'Calle Principal 753, Rancagua', '+56722987654', 'info@herramientas247.cl', 'www.herramientas247.cl'),
('8490123-4', 'Construye Ahora', 'Av. Los Héroes 951, Iquique', '+56572765432', 'contacto@construyeahora.cl', 'www.construyeahora.cl'),
('8501234-5', 'ToolRent Express', 'Calle del Sol 357, Viña del Mar', '+56322345678', 'info@toolrentexpress.cl', 'www.toolrentexpress.cl');

-- Inserciones para la tabla "cliente"
INSERT INTO cliente (rut, nombre, correo, celular) VALUES
('11111111-1', 'Juan Pérez', 'juan.perez@email.com', '+56912345678'),
('22222222-2', 'María González', 'maria.gonzalez@email.com', '+56923456789'),
('33333333-3', 'Pedro Rodríguez', 'pedro.rodriguez@email.com', '+56934567890'),
('44444444-4', 'Ana Martínez', 'ana.martinez@email.com', '+56945678901'),
('55555555-5', 'Carlos López', 'carlos.lopez@email.com', '+56956789012'),
('66666666-6', 'Laura Sánchez', 'laura.sanchez@email.com', '+56967890123'),
('77777777-7', 'Diego Fernández', 'diego.fernandez@email.com', '+56978901234'),
('88888888-8', 'Sofía Castro', 'sofia.castro@email.com', '+56989012345'),
('99999999-9', 'Andrés Vargas', 'andres.vargas@email.com', '+56990123456'),
('10101010-1', 'Carmen Muñoz', 'carmen.munoz@email.com', '+56901234567');

-- Inserciones para la tabla "herramientas"
INSERT INTO herramientas (id_herramienta, nombre, precio_dia) VALUES
(1, 'Taladro eléctrico', 5000),
(2, 'Sierra circular', 6000),
(3, 'Amoladora angular', 4500),
(4, 'Martillo demoledor', 8000),
(5, 'Lijadora orbital', 3500),
(6, 'Compresor de aire', 7000),
(7, 'Soldadora eléctrica', 9000),
(8, 'Pistola de calor', 4000),
(9, 'Esmeriladora de banco', 5500),
(10, 'Cortadora de cerámica', 6500);

-- Inserciones para la tabla "arriendo"
INSERT INTO arriendo (folio, fecha, dias, valor_dia, garantia, herramienta_id_herramienta, cliente_rut) VALUES
(1001, '2024-05-01', 3, 5000, 'Depósito en efectivo', 1, '11111111-1'),
(1002, '2024-05-02', 2, 6000, 'Tarjeta de crédito', 2, '22222222-2'),
(1003, '2024-05-03', 5, 4500, 'Cheque', 3, '33333333-3'),
(1004, '2024-05-04', 1, 8000, 'Depósito en efectivo', 4, '44444444-4'),
(1005, '2024-05-05', 4, 3500, 'Tarjeta de crédito', 5, '55555555-5'),
(1006, '2024-05-06', 2, 7000, 'Cheque', 6, '66666666-6'),
(1007, '2024-05-07', 3, 9000, 'Depósito en efectivo', 7, '77777777-7'),
(1008, '2024-05-08', 1, 4000, 'Tarjeta de crédito', 8, '88888888-8'),
(1009, '2024-05-09', 6, 5500, 'Cheque', 9, '99999999-9'),
(1010, '2024-05-10', 2, 6500, 'Depósito en efectivo', 10, '10101010-1');



DELETE FROM cliente WHERE rut = (SELECT rut FROM cliente ORDER BY rut DESC LIMIT 1)
DELETE FROM herramientas WHERE id_herramienta=1
UPDATE cliente SET correo = "update@testing.cl" WHERE rut = (SELECT rut FROM cliente ORDER BY rut DESC LIMIT 1)
SELECT * FROM herramientas
SELECT * FROM arriendo WHERE cliente_rut = (SELECT rut FROM cliente WHERE rut <>(SELECT rut FROM cliente ORDER BY rut LIMIT 1) ORDER BY rut LIMIT 1)
SELECT nombre FROM cliente WHERE nombre LIKE '%a%'
SELECT nombre FROM herramientas WHERE id_herramienta=2
UPDATE arriendo SET fecha = '2020-01-15' WHERE folio <1003 	
SELECT folio, fecha, valor_dia FROM arriendo WHERE fecha BETWEEN '2020-01-01' AND '2020-01-31';