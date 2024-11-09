-- Crear tabla de productos
CREATE TABLE IF NOT EXISTS products (
                                        id_producto SERIAL PRIMARY KEY,
                                        modelo VARCHAR(100) NOT NULL,
                                        marca VARCHAR(100) NOT NULL,
                                        descripcion TEXT
);

-- Insertar datos iniciales
INSERT INTO products (modelo, marca, descripcion) VALUES ('Tent-001', 'Coleman', 'Carpa para 4 personas resistente al agua');
INSERT INTO products (modelo, marca, descripcion) VALUES ('Tent-002', 'North Face', 'Carpa de alta montaña 2 personas');
INSERT INTO products (modelo, marca, descripcion) VALUES ('SB-100', 'Coleman', 'Saco de dormir -5°C');
INSERT INTO products (modelo, marca, descripcion) VALUES ('Light-001', 'Petzl', 'Linterna frontal recargable');
INSERT INTO products (modelo, marca, descripcion) VALUES ('Mat-001', 'Thermarest', 'Colchoneta autoinflable');
INSERT INTO products (modelo, marca, descripcion) VALUES ('Stove-001', 'MSR', 'Cocinilla a gas portátil');
INSERT INTO products (modelo, marca, descripcion) VALUES ('Back-001', 'Osprey', 'Mochila 60L para trekking');
INSERT INTO products (modelo, marca, descripcion) VALUES ('Pole-001', 'Black Diamond', 'Bastones de trekking ajustables');
INSERT INTO products (modelo, marca, descripcion) VALUES ('Cook-001', 'GSI', 'Set de cocina camping 4 personas');
INSERT INTO products (modelo, marca, descripcion) VALUES ('Chair-001', 'Helinox', 'Silla plegable ultraligera');

-- Crear índices para mejorar el rendimiento de las búsquedas
CREATE INDEX idx_products_marca ON products(marca);
CREATE INDEX idx_products_modelo ON products(modelo);
CREATE INDEX idx_products_descripcion ON products USING gin(to_tsvector('spanish', descripcion));