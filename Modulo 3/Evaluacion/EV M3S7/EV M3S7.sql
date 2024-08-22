SELECT * FROM cliente WHERE rut NOT IN (SELECT DISTINCT rut from arriendo)

SELECT ar.folio, ar.fecha, ar.dias, ar.valor_dia, cl.nombre, cl.rut
FROM arriendo AS ar
JOIN cliente AS cl ON cl.rut = ar.cliente_rut


WITH ArriendosPorCliente AS (
    SELECT 
        Cliente_RUT,
        COUNT(*) AS NumeroArriendos
    FROM 
        Arriendo
    GROUP BY 
        Cliente_RUT
)

SELECT 
    c.RUT,
    c.Nombre AS NombreCliente,
    h.Nombre AS NombreHerramienta,
    COALESCE(apc.NumeroArriendos, 0) AS NumeroArriendos,
    CASE 
        WHEN COALESCE(apc.NumeroArriendos, 0) = 0 THEN 'bajo'
        WHEN COALESCE(apc.NumeroArriendos, 0) BETWEEN 1 AND 3 THEN 'medio'
        ELSE 'alto'
    END AS ClasificacionCliente
FROM 
    Cliente c
LEFT JOIN 
    ArriendosPorCliente apc ON c.RUT = apc.Cliente_RUT
LEFT JOIN 
    Arriendo a ON c.RUT = a.Cliente_RUT
LEFT JOIN 
    Herramienta h ON a.Herramienta_ID_Herramienta = h.IDHerramienta
WHERE 
    h.IDHerramienta = (SELECT Herramienta_IDHerramienta FROM HerramientaMasArrendada)
    OR h.IDHerramienta IS NULL
ORDER BY 
    COALESCE(apc.NumeroArriendos, 0) DESC, c.Nombre;