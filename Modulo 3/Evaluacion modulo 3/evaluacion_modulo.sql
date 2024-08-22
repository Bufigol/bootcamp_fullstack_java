CREATE TYPE "categoria" AS ENUM (
  'A',
  'B',
  'C',
  'D',
  'E',
  'F'
);

CREATE TABLE "libro" (
  "id_libro" SERIAL PRIMARY KEY,
  "titulo" VARCHAR(20) NOT NULL,
  "categoria_libro" categoria DEFAULT 'F',
  "autor" INT NOT NULL,
  "estado" INT NOT NULL,
  "ubicacion" INT NOT NULL,
  "tema" VARCHAR(15) DEFAULT 1,
  "editorial" INT,
  "alta" BOOLEAN DEFAULT true
);

CREATE TABLE "editorial" (
  "id_editorial" SERIAL PRIMARY KEY,
  "nombre" VARCHAR(20) NOT NULL,
  "direccion" VARCHAR(20),
  "web" VARCHAR(20)
);

CREATE TABLE "estado" (
  "id_estado" SERIAL PRIMARY KEY,
  "nombre" VARCHAR(15) NOT NULL
);

CREATE TABLE "ubicaciones" (
  "id_ubicacion" SERIAL PRIMARY KEY,
  "nombre" VARCHAR(15) NOT NULL
);

CREATE TABLE "tema" (
  "id_tema" SERIAL PRIMARY KEY,
  "nombre" VARCHAR(20) NOT NULL
);

CREATE TABLE "autor" (
  "id_autor" SERIAL PRIMARY KEY,
  "nombre" VARCHAR(30) NOT NULL,
  "nacionalidad" VARCHAR(30)
);

CREATE TABLE "socio" (
  "id_socio" SERIAL PRIMARY KEY,
  "nombre" VARCHAR(15) NOT NULL,
  "categoria_socio" categoria DEFAULT 'F'
);

CREATE TABLE "prestamo" (
  "libro" INT,
  "socio" INT,
  "fecha_prestamo" DATE,
  "fecha_devolucion" DATE,
  "maximo_dias" INT DEFAULT 5,
  "perdido" BOOLEAN DEFAULT false,
  PRIMARY KEY ("libro", "socio", "fecha_prestamo")
);

ALTER TABLE "libro" ADD FOREIGN KEY ("estado") REFERENCES "estado" ("id_estado");

ALTER TABLE "libro" ADD FOREIGN KEY ("ubicacion") REFERENCES "ubicaciones" ("id_ubicacion");

ALTER TABLE "libro" ADD FOREIGN KEY ("editorial") REFERENCES "editorial" ("id_editorial");

ALTER TABLE "libro" ADD FOREIGN KEY ("categoria_libro") REFERENCES "tema" ("id_tema");

ALTER TABLE "prestamo" ADD FOREIGN KEY ("socio") REFERENCES "socio" ("id_socio");

ALTER TABLE "prestamo" ADD FOREIGN KEY ("libro") REFERENCES "libro" ("id_libro");

ALTER TABLE "libro" ADD FOREIGN KEY ("autor") REFERENCES "autor" ("id_autor");
