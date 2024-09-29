import com.bufigol.modelo.Alumno;
import com.bufigol.modelo.Materia;
import com.bufigol.modelo.MateriasEnum;
import com.bufigol.servicios.AlumnoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestAlumnoServicio {
    private AlumnoServicio alumnoServicio;

    @Mock
    private AlumnoServicio alumnoServicioMock;

    private Materia matematicas;
    private Materia lenguaje;
    private Alumno mapu;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        alumnoServicio = new AlumnoServicio();

        matematicas = new Materia(MateriasEnum.MATEMATICAS, new ArrayList<>());
        lenguaje = new Materia(MateriasEnum.LENGUAJE, new ArrayList<>());

        mapu = new Alumno("12345678-9", "Mapu", "Huechuraba", "Calle Principal 123", new ArrayList<>());
    }

    @Test
    void crearAlumnoTest() {
        alumnoServicio.crearAlumno(mapu);
        Alumno alumnoRecuperado = alumnoServicio.buscarAlumnoPorRut("12345678-9");

        assertNotNull(alumnoRecuperado);
        assertEquals(mapu, alumnoRecuperado);
    }

    @Test
    void agregarMateriaTest() {
        alumnoServicio.crearAlumno(mapu);
        alumnoServicio.agregarMateria(mapu, matematicas);

        Alumno alumnoRecuperado = alumnoServicio.buscarAlumnoPorRut("12345678-9");
        assertNotNull(alumnoRecuperado);
        assertTrue(alumnoRecuperado.getMaterias().contains(matematicas));
    }

    @Test
    void materiasPorAlumnosTest() {
        List<Materia> materiasMock = Arrays.asList(matematicas, lenguaje);
        when(alumnoServicioMock.materiasPorAlumno("12345678-9")).thenReturn(materiasMock);

        List<Materia> materias = alumnoServicioMock.materiasPorAlumno("12345678-9");

        assertEquals(2, materias.size());
        assertTrue(materias.contains(matematicas));
        assertTrue(materias.contains(lenguaje));
        verify(alumnoServicioMock, times(1)).materiasPorAlumno("12345678-9");
    }

    @Test
    void listarAlumnosTest() {
        alumnoServicio.crearAlumno(mapu);
        Map<String, Alumno> alumnos = alumnoServicio.listarAlumnos();

        assertFalse(alumnos.isEmpty());
        assertTrue(alumnos.containsKey("12345678-9"));
        assertEquals(mapu, alumnos.get("12345678-9"));
    }

}
