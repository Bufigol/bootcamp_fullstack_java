CREATE TABLE IF NOT EXISTS pelicula (
                                        id SERIAL PRIMARY KEY,
                                        titulo VARCHAR(100),
                                        director VARCHAR(100),
                                        anio INTEGER,
                                        duracion INTEGER
);