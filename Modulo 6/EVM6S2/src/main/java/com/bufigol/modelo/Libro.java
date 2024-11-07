package com.bufigol.modelo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    @NonNull
    @Min(value = 1, message = "El ID debe ser mayor que 0")
    private int id;

    @NonNull
    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @NonNull
    @NotNull(message = "El autor no puede ser null")
    private Autor autor;

    @NotNull(message = "La editorial no puede ser null")
    private Editorial editorial;

    @Min(value = 1, message = "El número de páginas debe ser mayor que 0")
    private int paginas;

    @NotBlank(message = "El género no puede estar vacío")
    private String genero;

    private boolean disponible;

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", editorial=" + editorial +
                ", paginas=" + paginas +
                ", genero='" + genero + '\'' +
                ", disponible=" + (disponible ? "Disponible" : "No disponible") +
                '}';
    }

    // Este método es útil para comparar libros en las operaciones de búsqueda
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return id == libro.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}