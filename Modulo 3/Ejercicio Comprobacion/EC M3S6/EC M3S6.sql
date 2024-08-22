-- Tabla Empresa
CREATE TABLE empresa (
    rut VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(25),
    direccion VARCHAR(120),
    telefono VARCHAR(15),
    correo VARCHAR(80),
    web VARCHAR(50)
);

-- Tabla Cliente
CREATE TABLE cliente (
    rut VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(120),
    correo VARCHAR(80),
    direccion VARCHAR(120),
    celular VARCHAR(15),
    alta BOOLEAN
);

-- Tabla TipoVehiculo
CREATE TABLE tipovehiculo (
    id_tipo_vehiculo NUMERIC(12) PRIMARY KEY,
    nombre VARCHAR(120)
);

-- Tabla Marca
CREATE TABLE marca (
    IDMarca SERIAL PRIMARY KEY,
    Nombre VARCHAR(20)
);

-- Tabla Vehiculo
CREATE TABLE vehiculo (
    idvehiculo NUMERIC(12) PRIMARY KEY,
    patente VARCHAR(8),
    marca INTEGER,
    modelo VARCHAR(20),
    color VARCHAR(20),
    precio NUMERIC(12),
    frecuenciamantencion NUMERIC(3),
    tipovehiculo NUMERIC(12),
    FOREIGN KEY (marca) REFERENCES Marca(idmarca),
    FOREIGN KEY (tipovehiculo) REFERENCES tipovehiculo(id_tipo_vehiculo)
);

-- Tabla Venta
CREATE TABLE venta (
    folio NUMERIC(12) PRIMARY KEY,
    fecha DATE,
    monto NUMERIC(12),
    vehiculo_idvehiculo NUMERIC(12),
    cliente_rut VARCHAR(10),
    FOREIGN KEY (vehiculo_idvehiculo) REFERENCES vehiculo(idvehiculo),
    FOREIGN KEY (cliente_rut) REFERENCES cliente(rut)
);

-- Tabla Mantencion
CREATE TABLE mantencion (
    idmantencion SERIAL PRIMARY KEY,
    fecha DATE,
    trabajosrealizados VARCHAR(1000),
    idventa NUMERIC(12),
    FOREIGN KEY (idventa) REFERENCES venta(folio)
);

-- Inserciones para la tabla Empresa
INSERT INTO Empresa (RUT, Nombre, Direccion, Telefono, Correo, Web) VALUES
('76123456-7', 'AutoMax', 'Av. Libertador 123, Santiago', '+56912345678', 'contacto@automax.cl', 'www.automax.cl'),
('76234567-8', 'CarrosYa', 'Calle Nueva 456, Valparaíso', '+56922345678', 'info@carrosya.cl', 'www.carrosya.cl'),
('76345678-9', 'MotorExpress', 'Av. Principal 789, Concepción', '+56932345678', 'ventas@motorexpress.cl', 'www.motorexpress.cl'),
('76456789-0', 'AutoDreams', 'Paseo Ahumada 321, Santiago', '+56942345678', 'contacto@autodreams.cl', 'www.autodreams.cl'),
('76567890-1', 'VehículosPro', 'Calle Viña 654, Viña del Mar', '+56952345678', 'info@vehiculospro.cl', 'www.vehiculospro.cl'),
('76678901-2', 'CarCenter', 'Av. Costanera 987, La Serena', '+56962345678', 'ventas@carcenter.cl', 'www.carcenter.cl'),
('76789012-3', 'AutoFast', 'Calle del Sol 147, Antofagasta', '+56972345678', 'contacto@autofast.cl', 'www.autofast.cl'),
('76890123-4', 'MotorMaster', 'Av. OHiggins 258, Rancagua', '+56982345678', 'info@motormaster.cl', 'www.motormaster.cl'),
('76901234-5', 'CarroFácil', 'Paseo Bulnes 369, Santiago', '+56992345678', 'ventas@carrofacil.cl', 'www.carrofacil.cl'),
('77012345-6', 'AutoPlaza', 'Calle Larga 480, Arica', '+56902345678', 'contacto@autoplaza.cl', 'www.autoplaza.cl');

-- Inserciones para la tabla Cliente
INSERT INTO Cliente (rut, nombre, correo, Celular, Alta) VALUES
('12345678-1', 'Juan Pérez', 'juan.perez@email.com', '+56911111111', TRUE),
('90235458-2', 'María López', 'maria.lopez@email.com', '+56922222222', TRUE),
('85274109-3', 'Pedro González', 'pedro.gonzalez@email.com', '+56933333333', FALSE),
('95162378-4', 'Ana Martínez', 'ana.martinez@email.com', '+56944444444', TRUE),
('74812596-5', 'Carlos Rodríguez', 'carlos.rodriguez@email.com', '+56955555555', TRUE),
('96547852-6', 'Laura Sánchez', 'laura.sanchez@email.com', '+56966666666', FALSE),
('88552211-7', 'Diego Fernández', 'diego.fernandez@email.com', '+56977777777', TRUE),
('44665588-8', 'Sofía Castro', 'sofia.castro@email.com', '+56988888888', TRUE),
('88224466-9', 'Andrés Vargas', 'andres.vargas@email.com', '+56999999999', FALSE),
('11667799-1', 'Carmen Muñoz', 'carmen.munoz@email.com', '+56900000000', TRUE);

-- Inserciones para la tabla TipoVehiculo
INSERT INTO TipoVehiculo (id_tipo_vehiculo, nombre) VALUES
(1, 'Sedán'),
(2, 'SUV'),
(3, 'Hatchback'),
(4, 'Pickup'),
(5, 'Coupé'),
(6, 'Minivan'),
(7, 'Crossover'),
(8, 'Convertible'),
(9, 'Station Wagon'),
(10, 'Van');

-- Inserciones para la tabla Marca
INSERT INTO Marca (Nombre) VALUES
('Toyota'),
('Chevrolet'),
('Ford'),
('Nissan'),
('Hyundai'),
('Kia'),
('Mazda'),
('Volkswagen'),
('Honda'),
('Suzuki');

-- Inserciones para la tabla Vehiculo
INSERT INTO Vehiculo (IDVehiculo, Patente, Marca, Modelo, Color, Precio, FrecuenciaMantencion, TipoVehiculo) VALUES
(1, 'ABCD12', 1, 'Corolla', 'Blanco', 15000000, 6, 1),
(2, 'EFGH34', 2, 'Captiva', 'Negro', 18000000, 6, 2),
(3, 'IJKL56', 3, 'Fiesta', 'Rojo', 12000000, 6, 3),
(4, 'MNOP78', 4, 'Frontier', 'Azul', 20000000, 6, 4),
(5, 'QRST90', 5, 'Elantra', 'Gris', 14000000, 6, 1),
(6, 'UVWX12', 6, 'Sportage', 'Verde', 16000000, 6, 2),
(7, 'YZAB34', 7, 'CX-5', 'Plateado', 17000000, 6, 2),
(8, 'CDEF56', 8, 'Golf', 'Amarillo', 13000000, 6, 3),
(9, 'GHIJ78', 9, 'Civic', 'Naranja', 15500000, 6, 1),
(10, 'KLMN90', 10, 'Swift', 'Morado', 11000000, 6, 3),
(11, 'ABCD23', 1, 'Corolla', 'Blanco', 15000000, 6, 1);

-- Inserciones para la tabla Venta
INSERT INTO Venta (Folio, Fecha, Monto, Vehiculo_IDVehiculo, Cliente_RUT) VALUES
(1, '2023-01-15', 15000000, 1, '88552211-7'),
(2, '2023-02-20', 18000000, 2, '22222222-2'),
(3, '2023-03-25', 12000000, 3, '22222222-2'),
(4, '2023-04-30', 20000000, 4, '44444444-4'),
(5, '2023-05-05', 14000000, 5, '55555555-5'),
(6, '2023-06-10', 16000000, 6, '66666666-6'),
(7, '2023-07-15', 17000000, 7, '77777777-7'),
(8, '2023-08-20', 13000000, 8, '88888888-8'),
(9, '2023-09-25', 15500000, 9, '99999999-9'),
(10, '2023-10-30', 11000000, 10, '95162378-4'),
(11, '2020-01-15', 15000000, 1, '88552211-7'),
(12, '2020-01-20', 18000000, 2, '22222222-2'),
(13, '2020-01-25', 12000000, 3, '22222222-2'),
(14, '2020-01-30', 20000000, 4, '44444444-4'),
(15, '2023-01-15', 15000000, 1, '88552211-7'),
(16, '2023-02-20', 18000000, 2, '22222222-2'),
(17, '2023-03-25', 12000000, 3, '22222222-2'),
(18, '2023-04-30', 20000000, 4, '44444444-4'),
(19 '2023-05-05', 14000000, 5, '55555555-5'),
(20, '2023-06-10', 16000000, 6, '66666666-6'),
(21, '2023-07-15', 17000000, 7, '77777777-7'),
(22, '2023-08-20', 13000000, 8, '88888888-8'),
(23, '2023-09-25', 15500000, 9, '99999999-9'),
(24, '2023-10-30', 11000000, 10, '95162378-4'),
(25, '2020-01-15', 15000000, 1, '88552211-7'),
(26, '2020-01-20', 18000000, 2, '22222222-2'),
(27, '2020-01-25', 12000000, 3, '22222222-2'),
(28, '2020-01-30', 20000000, 4, '44444444-4');

-- Inserciones para la tabla Mantencion
INSERT INTO Mantencion (Fecha, TrabajosRealizados, IDVenta) VALUES
('2023-07-15', 'Cambio de aceite y filtros', 1),
('2023-08-20', 'Revisión de frenos y suspensión', 2),
('2023-09-25', 'Alineación y balanceo', 3),
('2023-10-30', 'Cambio de bujías y revisión eléctrica', 4),
('2023-11-05', 'Cambio de correa de distribución', 5),
('2023-12-10', 'Revisión general y cambio de líquidos', 6),
('2024-01-15', 'Mantenimiento de aire acondicionado', 7),
('2024-02-20', 'Cambio de pastillas de freno', 8),
('2024-03-25', 'Revisión de la transmisión', 9),
('2024-04-30', 'Cambio de amortiguadores', 10);


SELECT v.idvehiculo, v.patente, v.modelo, m.nombre AS Marca, tv.nombre AS TipoVehiculo, v.color, v.precio
FROM vehiculo v
LEFT JOIN venta ven ON v.idvehiculo = ven.vehiculo_idvehiculo
LEFT JOIN marca m ON v.Marca = m.IDMarca
LEFT JOIN tipovehiculo tv ON v.tipovehiculo = tv.id_tipo_vehiculo
WHERE ven.folio IS NULL
ORDER BY v.idvehiculo;

SELECT 
    v.Folio,
    v.Fecha AS FechaVenta,
    v.Monto AS MontoVenta,
    c.Nombre AS NombreCliente,
    c.RUT AS RutCliente,
    ve.Patente,
    m.Nombre AS NombreMarca,
    ve.Modelo
FROM 
    Venta v
JOIN 
    Cliente c ON v.Cliente_RUT = c.RUT
JOIN 
    Vehiculo ve ON v.Vehiculo_IDVehiculo = ve.IDVehiculo
JOIN 
    Marca m ON ve.Marca = m.IDMarca
WHERE 
    v.Fecha BETWEEN '2020-01-01' AND '2020-01-31'
ORDER BY 
    v.Fecha, v.Folio;
	
	
SELECT 
    TO_CHAR(v.Fecha, 'YYYY-MM') AS Mes,
    m.Nombre AS Marca,
    SUM(v.Monto) AS TotalVentas,
    COUNT(*) AS CantidadVentas
FROM 
    Venta v
JOIN 
    Vehiculo ve ON v.Vehiculo_IDVehiculo = ve.IDVehiculo
JOIN 
    Marca m ON ve.Marca = m.IDMarca
WHERE 
    EXTRACT(YEAR FROM v.Fecha) = 2020
GROUP BY 
    TO_CHAR(v.Fecha, 'YYYY-MM'),
    m.Nombre
ORDER BY 
    Mes, 
    TotalVentas DESC;
	
SELECT nombre,rut FROM cliente UNION SELECT nombre,rut FROM empresa

SELECT 
    TO_CHAR(ar.fecha, 'YYYY-MM') AS Mes,
    COUNT(*) AS CantidadArriendos
FROM 
    arriendo AS ar
GROUP BY 
    TO_CHAR(ar.fecha, 'YYYY-MM')

ORDER BY 
    Mes DESC;