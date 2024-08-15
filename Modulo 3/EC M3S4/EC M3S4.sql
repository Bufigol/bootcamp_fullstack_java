// https://pastebin.com/AsQFAzD3 
CREATE TABLE "empresa" (
  "RUT" varchar2(10) PRIMARY KEY,
  "nombre" varchar2(120),
  "direccion" varchar2(120),
  "telefono" varchar2(15),
  "correo" varchar2(80),
  "web" varchar2(50)
);

CREATE TABLE "cliente" (
  "RUT" varchar2(10) PRIMARY KEY,
  "nombre" varchar2(120),
  "correo" varchar2(80),
  "direccion" varchar2(120),
  "celular" varchar2(15),
  "alta" CHAR(1) NOT NULL
);

CREATE TABLE "tipoVehiculo" (
  "idTipoVehiculo" SERIAL PRIMARY KEY,
  "nombre" VARCHAR2(120)
);

CREATE TABLE "venta" (
  "folio" SERIAL PRIMARY KEY,
  "fecha" date,
  "monto" NUMBER(12),
  "vehiculo_idVehiculo" NUMBER(12) NOT NULL,
  "cliente_rut" varchar2(10)
);

CREATE TABLE "vehiculo" (
  "idVehiculo" SERIAL PRIMARY KEY,
  "patente" VARCHAR2(10),
  "marca" VARCHAR2(20),
  "modelo" VARCHAR2(20),
  "color" VARCHAR2(15),
  "precio" NUMBER(12) NOT NULL,
  "frecuenciaMantencion" NUMBER(5),
  "marca_idMarca" NUMBER(12) NOT NULL,
  "tipoVehiculo_idTipoVehiculo" NUMBER(12) NOT NULL
);

CREATE TABLE "mantencion" (
  "idMantencion" SERIAL PRIMARY KEY,
  "fecha" DATE DEFAULT 'now()',
  "trabajosRealizados" VARCHAR2(1000),
  "ventaFolio" NUMBER(12) NOT NULL
);

CREATE TABLE "marca" (
  "idMarca" SERIAL PRIMARY KEY,
  "Nombre" VARCHAR2(120) NOT NULL
);

CREATE INDEX "vehiculo_IDX" ON "venta" ("vehiculo_idVehiculo");

CREATE INDEX "tipoVehiculo" ON "vehiculo" ("tipoVehiculo_idTipoVehiculo");

CREATE INDEX "Marca_IDMarca" ON "vehiculo" ("marca_idMarca");

CREATE INDEX "Mantencion_Venta_FK" ON "mantencion" ("ventaFolio");

ALTER TABLE "venta" ADD FOREIGN KEY ("cliente_rut") REFERENCES "cliente" ("RUT");

ALTER TABLE "venta" ADD FOREIGN KEY ("vehiculo_idVehiculo") REFERENCES "vehiculo" ("idVehiculo");

ALTER TABLE "vehiculo" ADD FOREIGN KEY ("tipoVehiculo_idTipoVehiculo") REFERENCES "tipoVehiculo" ("idTipoVehiculo");

ALTER TABLE "mantencion" ADD FOREIGN KEY ("idMantencion") REFERENCES "venta" ("folio");

ALTER TABLE "vehiculo" ADD FOREIGN KEY ("marca_idMarca") REFERENCES "marca" ("idMarca");
