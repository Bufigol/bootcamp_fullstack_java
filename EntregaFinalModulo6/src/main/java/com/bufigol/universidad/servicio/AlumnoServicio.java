package com.bufigol.universidad.servicio;

import com.bufigol.universidad.dtos.mappers.AlumnoMapper;
import com.bufigol.universidad.dtos.modelo.AlumnoRequestDTO;
import com.bufigol.universidad.dtos.modelo.AlumnoResponseDTO;
import com.bufigol.universidad.interfaces.servicio.INT_AlumnoServicio;
import com.bufigol.universidad.modelo.Alumno;
import com.bufigol.universidad.repositorio.AlumnoRepository;
import com.bufigol.universidad.utils.Comprobadores;
import com.bufigol.universidad.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AlumnoServicio implements INT_AlumnoServicio {

    @Autowired
    private final AlumnoRepository alumnoRepository;
    private final AlumnoMapper alumnoMapper;

    public AlumnoServicio() {
        this.alumnoRepository = new AlumnoRepository();
        this.alumnoMapper = new AlumnoMapper();
    }

    @Override
    public AlumnoResponseDTO create(AlumnoRequestDTO alumnoDTO) {
        Alumno alumno = alumnoMapper.toEntity(alumnoDTO);
        if( !alumnoRepository.existsByRut( alumno.getRut() ) ){
            alumno.setPassword( PasswordUtils.generarPasswordSegura( alumno.getPassword() ) );
            return this.alumnoMapper.toDto( alumnoRepository.save(alumno) );
        }else{
            System.out.println("El alumno ya existe");
            Optional<Alumno> optAlumno = alumnoRepository.findByRut( alumnoDTO.getRut() );
            if (optAlumno.isPresent()) {
                alumno = optAlumno.get();
                return this.alumnoMapper.toDto( alumno );
            }else{
                // TODO: Agregar manejo de excepciones
                return null;
            }
        }
    }

    @Override
    public AlumnoResponseDTO update(Long id, AlumnoRequestDTO alumnoDTO) {
        Alumno alumno = this.alumnoMapper.toEntity(alumnoDTO);
        if( this.alumnoRepository.existsById(id) ){
            alumno.setPassword( PasswordUtils.generarPasswordSegura(alumnoDTO.getPassword()) );
            return this.alumnoMapper.toDto( this.alumnoRepository.save(alumno) );
        }else{
            // TODO añadir manejo de error de id no encontrado
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        if( !this.alumnoRepository.existsById(id) ){
            this.alumnoRepository.deleteById(id);
        }else{
            // TODO: Agregar manejo de excepciones
            System.out.println("ID no encontrado");
        }
       
    }

    @Override
    public Optional<AlumnoResponseDTO> findById(Long id) {
        return this.alumnoRepository.findById(id).map(this.alumnoMapper::toDto);
    }

    @Override
    public Optional<AlumnoResponseDTO> findByRut(String rut) {
        if(Comprobadores.isValidRUT(rut)){
            Alumno alumno = this.alumnoRepository.findByRut(rut).orElse(null);
            assert alumno != null;
            return Optional.of(this.alumnoMapper.toDto(alumno));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public List<AlumnoResponseDTO> findByNombreContaining(String nombre) {
        try {
            List<Alumno> alumnos = this.alumnoRepository.findByNombreContainingIgnoreCase(nombre);
            return alumnos.stream().map(this.alumnoMapper::toDto).toList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return List.of();
    }

    @Override
    public Page<AlumnoResponseDTO> findAll(Pageable pageable) {
        if( pageable != null ){
            return this.alumnoRepository.findAll(pageable).map(this.alumnoMapper::toDto);
        }
        else {
            return null;
        }

    }

    @Override
    public boolean existsByRut(String rut) {
        if(Comprobadores.isValidRUT(rut)){
            return this.alumnoRepository.existsByRut(rut);
        }else{
            return false;
        }
    }
}
