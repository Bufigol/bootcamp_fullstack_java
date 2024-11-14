package com.bufigol.universidad.interfaces.repositorios;

import com.bufigol.universidad.modelo.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INT_MateriaRepository extends JpaRepository<Materia, Long> {

    List<Materia> findByAlumnoId(Long alumnoId);
    List<Materia> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByNombreAndAlumnoId(String nombre, Long alumnoId);
}
