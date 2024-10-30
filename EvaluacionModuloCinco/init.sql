create table if not exists horoscopo
(
    id           serial
        primary key,
    animal       varchar(30),
    fecha_inicio date,
    fecha_fin    date,
    UNIQUE (animal, fecha_inicio, fecha_fin)
);


-- Modificación de la tabla usuarios
CREATE TABLE usuarios (
                          id SERIAL PRIMARY KEY,
                          nombre VARCHAR(30),
                          username VARCHAR(30) UNIQUE,
                          email VARCHAR(30) UNIQUE,
                          fecha_nacimiento DATE,
                          password VARCHAR(30),
                          horoscopo_id INTEGER,
                          CONSTRAINT fk_horoscopo FOREIGN KEY (horoscopo_id) REFERENCES horoscopo (id)
);

-- Primero, creamos la función que determinará el horóscopo basado en la fecha de nacimiento


INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Rata', '1924-02-05', '1925-01-24');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Buey', '1925-01-25', '1926-02-12');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Tigre', '1926-02-13', '1927-02-01');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Conejo', '1927-02-02', '1928-01-22');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Dragón', '1928-01-23', '1929-02-09');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Serpiente', '1929-02-10', '1930-01-29');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Caballo', '1930-01-30', '1931-02-16');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cabra', '1931-02-17', '1932-02-05');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Mono', '1932-02-06', '1933-01-25');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Gallo', '1933-01-26', '1934-02-13');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Perro', '1934-02-14', '1935-02-03');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cerdo', '1935-02-04', '1936-01-23');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Rata', '1936-01-24', '1937-02-10');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Buey', '1937-02-11', '1938-01-30');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Tigre', '1938-01-31', '1939-02-18');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Conejo', '1939-02-19', '1940-02-07');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Dragón', '1940-02-08', '1941-01-26');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Serpiente', '1941-01-27', '1942-02-14');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Caballo', '1942-02-15', '1943-02-04');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cabra', '1943-02-05', '1944-01-24');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Mono', '1944-01-25', '1945-02-12');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Gallo', '1945-02-13', '1946-02-01');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Perro', '1946-02-02', '1947-01-21');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cerdo', '1947-01-22', '1948-02-09');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Rata', '1948-02-10', '1949-01-28');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Buey', '1949-01-29', '1950-02-16');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Tigre', '1950-02-17', '1951-02-05');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Conejo', '1951-02-06', '1952-01-26');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Dragón', '1952-01-27', '1953-02-13');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Serpiente', '1953-02-14', '1954-02-02');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Caballo', '1954-02-03', '1955-01-23');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cabra', '1955-01-24', '1956-02-11');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Mono', '1956-02-12', '1957-01-30');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Gallo', '1957-01-31', '1958-02-17');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Perro', '1958-02-18', '1959-02-07');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cerdo', '1959-02-08', '1960-01-27');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Rata', '1960-01-28', '1961-02-14');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Buey', '1961-02-15', '1962-02-04');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Tigre', '1962-02-05', '1963-01-24');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Conejo', '1963-01-25', '1964-02-12');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Dragón', '1964-02-13', '1965-02-01');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Serpiente', '1965-02-02', '1966-01-20');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Caballo', '1966-01-21', '1967-02-08');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cabra', '1967-02-09', '1968-01-29');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Mono', '1968-01-30', '1969-02-16');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Gallo', '1969-02-17', '1970-02-05');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Perro', '1970-02-06', '1971-01-26');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cerdo', '1971-01-27', '1972-02-14');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Rata', '1972-02-15', '1973-02-02');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Buey', '1973-02-03', '1974-01-22');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Tigre', '1974-01-23', '1975-02-10');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Conejo', '1975-02-11', '1976-01-30');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Dragón', '1976-01-31', '1977-02-17');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Serpiente', '1977-02-18', '1978-02-06');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Caballo', '1978-02-07', '1979-01-27');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cabra', '1979-01-28', '1980-02-15');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Mono', '1980-02-16', '1981-02-04');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Gallo', '1981-02-05', '1982-01-24');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Perro', '1982-01-25', '1983-02-12');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cerdo', '1983-02-13', '1984-02-01');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Rata', '1984-02-02', '1985-02-19');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Buey', '1985-02-20', '1986-02-08');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Tigre', '1986-02-09', '1987-01-28');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Conejo', '1987-01-29', '1988-02-16');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Dragón', '1988-02-17', '1989-02-05');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Serpiente', '1989-02-06', '1990-01-26');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Caballo', '1990-01-27', '1991-02-14');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cabra', '1991-02-15', '1992-02-03');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Mono', '1992-02-04', '1993-01-22');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Gallo', '1993-01-23', '1994-02-09');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Perro', '1994-02-10', '1995-01-30');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cerdo', '1995-01-31', '1996-02-18');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Rata', '1996-02-19', '1997-02-06');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Buey', '1997-02-07', '1998-01-27');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Tigre', '1998-01-28', '1999-02-15');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Conejo', '1999-02-16', '2000-02-04');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Dragón', '2000-02-05', '2001-01-23');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Serpiente', '2001-01-24', '2002-02-11');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Caballo', '2002-02-12', '2003-01-31');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cabra', '2003-02-01', '2004-01-21');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Mono', '2004-01-22', '2005-02-08');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Gallo', '2005-02-09', '2006-01-28');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Perro', '2006-01-29', '2007-02-17');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cerdo', '2007-02-18', '2008-02-06');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Rata', '2008-02-07', '2009-01-25');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Buey', '2009-01-26', '2010-02-13');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Tigre', '2010-02-14', '2011-02-02');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Conejo', '2011-02-03', '2012-01-22');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Dragón', '2012-01-23', '2013-02-09');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Serpiente', '2013-02-10', '2014-01-30');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Caballo', '2014-01-31', '2015-02-18');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cabra', '2015-02-19', '2016-02-07');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Mono', '2016-02-08', '2017-01-27');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Gallo', '2017-01-28', '2018-02-15');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Perro', '2018-02-16', '2019-02-04');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Cerdo', '2019-02-05', '2020-01-24');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Rata', '2020-01-25', '2021-02-11');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Buey', '2021-02-12', '2022-01-31');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Tigre', '2022-02-01', '2023-01-21');
INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES ('Conejo', '2023-01-22', '2024-02-09');