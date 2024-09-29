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
  "celular" varchar2(15)
);

CREATE TABLE "herramienta" (
  "idHerramienta" SERIAL PRIMARY KEY,
  "nombre" VARCHAR2(120) NOT NULL,
  "precioDia" NUMBER(12) NOT NULL
);

CREATE TABLE "arriendo" (
  "folio" NUMBER(12) PRIMARY KEY,
  "fecha" DATE DEFAULT 'now()',
  "dias" NUMBER(5) DEFAULT '1',
  "valorDia" NUMBER(12) NOT NULL,
  "garantia" VARCHAR2(30) NOT NULL,
  "herramienta_idHerramienta" NUMBER(12),
  "clienteRut" varchar2(10) NOT NULL
);

ALTER TABLE "arriendo" ADD FOREIGN KEY ("herramienta_idHerramienta") REFERENCES "herramienta" ("idHerramienta");

ALTER TABLE "arriendo" ADD FOREIGN KEY ("clienteRut") REFERENCES "cliente" ("RUT");

//https://pastebin.com/6rq67q9Y