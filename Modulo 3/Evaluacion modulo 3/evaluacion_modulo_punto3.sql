INSERT INTO proveedor(rut,nombre) VALUES ('12345678-0','Primer Proveedor');
UPDATE proveedor SET nombre = 'Primer Proveedor actualizado' WHERE rut = '12345678-0';

INSERT INTO compra(NumeroFactura,Fecha,Total,Proveedro_RUT) VALUES (10,'2024-08-19',15000,'12345678-0');

INSERT INTO DetCompra(Compra_NumeroFactura, ID, Cantidad, Descripcion, Unitario, Inventario_ID) VALUES
	(10,1,1,'Articulo_1',5,1),
	(10,2,2,'Articulo_1',52,2),
	(10,3,3,'Articulo_1',511,3),
	(10,4,2,'Articulo_1',52,4);
UPDATE DetCompra SET Cantidad = 3 WHERE Compra_NumeroFactura = 10 AND ID <2; 
UPDATE compra SET Total = (SELECT SUM(Cantidad * Unitario) FROM DetCompra WHERE Compra_NumeroFactura = 10 ) WHERE NumeroFactura = 10;

DELETE FROM DetCompra WHERE Compra_NumeroFactura = 10;
DELETE FROM compra WHERE Compra_NumeroFactura = 10;
DELETE FROM proveedor WHERE rut='12345678-0';

SELECT c.NumeroFactura, c.FechaCompra, cl.Nombre, cl.Apellido, dc.Producto_ID, dc.Cantidad, dc.Unitario
FROM Compra c
JOIN Cliente cl ON c.Cliente_ID = cl.ID
JOIN DetCompra dc ON c.NumeroFactura = dc.Compra_NumeroFactura
WHERE YEAR(c.FechaCompra) = [año] AND MONTH(c.FechaCompra) = [mes];

SELECT fecha, total FROM compra UNION SELECT fecha, total FROM Venta;

WITH CecinasMasVendidas AS (
  SELECT c.ID, SUM(dv.Cantidad) AS TotalVendido
  FROM Cecina c
  JOIN DetVenta dv ON c.ID = dv.Cecina_ID
  GROUP BY c.ID
  ORDER BY TotalVendido DESC
  FETCH FIRST 5 ROWS ONLY
)
SELECT p.ID, p.FechaInicio, p.FechaTermino
FROM Produccion p
JOIN DetProduccion dp ON p.ID = dp.Produccion_ID
JOIN CecinasMasVendidas cmv ON dp.Cecina_ID = cmv.ID
WHERE YEAR(p.FechaInicio) = 2020;