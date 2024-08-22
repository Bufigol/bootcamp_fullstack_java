SELECT cl.nombre, cl.rut, ar.folio, ar.fecha, ar.dias, ar.valor_dia
FROM arriendo AS ar
INNER JOIN cliente AS cl
ON cl.rut = ar.cliente_rut 

SELECT * FROM cliente
WHERE rut NOT IN (SELECT DISTINCT cliente_rut FROM arriendo)

SELECT nombre,rut FROM cliente
UNION
SELECT nombre,rut FROM empresa