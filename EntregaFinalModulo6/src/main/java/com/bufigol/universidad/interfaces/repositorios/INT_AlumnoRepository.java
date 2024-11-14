package com.bufigol.universidad.interfaces.repositorios;

import com.bufigol.universidad.modelo.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INT_AlumnoRepository  extends JpaRepository<Alumno, Long> {


    Optional<Alumno> findByRut(String rut);
    boolean existsByRut(String rut);
    List<Alumno> findByNombreContainingIgnoreCase(String nombre);
}
