package com.bufigol.s13.main;

import com.bufigol.s13.modelo.Alumno;
import com.bufigol.s13.modelo.Curso;
import com.bufigol.s13.utiles.Auxiliar;
import com.bufigol.utils.EntradaPorTeclado;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EVM4S13 {

    public static void main(String[] args) {
        ejecicionPrograma();
    }

    private static void ejecicionPrograma() {
        ArrayList<Alumno> alumnosList = Auxiliar.cargarAlumnos();
        ArrayList<Curso> cursoList = Auxiliar.cargarCursos();
        Map<Curso, ArrayList<Alumno>> mapAlumnosPorCurso = generarMapAlumnosPorCurso(cursoList);
        int opc = 0;
        do{
            opc = mostrarMenu();
            switch (opc) {
                case 1:
                    for (Curso curso : cursoList) {
                        System.out.println(curso);
                    }
                    break;
                case 2:
                    String nombre = EntradaPorTeclado.pedirCadena("Ingrese el Nombre");
                    String rut = EntradaPorTeclado.pedirCadena("Ingrese el Rut (con guion):");
                    String fechaNacimiento = EntradaPorTeclado.pedirCadena("Fecha de nacimiento (dd-mm-yyyy)");
                    Alumno nuevoAlumno = new Alumno(nombre, rut, fechaNacimiento);
                    alumnosList.add(nuevoAlumno);
                    Auxiliar.añadirAlumnoAlArchivo(nuevoAlumno);
                    break;
                case 3:
                    Curso nuevoCurso = new Curso(EntradaPorTeclado.pedirCadena("Ingrese el Nombre del Curso"),
                            EntradaPorTeclado.pedirCadena("Ingrese el Codigo del curso"),
                            EntradaPorTeclado.pedirCadena("Ingrese la descripocion del curso"));
                    cursoList.add(nuevoCurso);
                    Auxiliar.añadirCursoAlArchivo(nuevoCurso);
                    mapAlumnosPorCurso.put(nuevoCurso, new ArrayList<>());
                    break;
                case 4:
                    Curso cursoSeleccionado = cursoList.get(mostrarCursosDisponibles(cursoList));
                    Alumno alumnoSeleccionado = alumnosList.get(selecionarAlumno(alumnosList));
                    mapAlumnosPorCurso.get(cursoSeleccionado).add(alumnoSeleccionado);
                    System.out.println("Alumno asignado");
                    break;
                case 5:
                    mostrarCursosConAlumnos(mapAlumnosPorCurso);
                    break;
                case 6:
                    System.out.println("Hasta pronto!");
                    EntradaPorTeclado.cerrarScanner();
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }while(opc != 6);
    }

    private static void mostrarCursosConAlumnos(Map<Curso, ArrayList<Alumno>> mapAlumnosPorCurso) {
        mapAlumnosPorCurso.entrySet().stream()
                .forEach(entry -> {
                    Curso curso = entry.getKey();
                    ArrayList<Alumno> alumnos = entry.getValue();
                    System.out.println("Curso: " + curso.getNombre());
                    System.out.println("Alumnos:");
                    alumnos.stream()
                            .map(Alumno::getNombre)
                            .forEach(nombreAlumno -> System.out.println("  - " + nombreAlumno));

                    System.out.println(); // Línea en blanco para separar los cursos
                });
    }

    private static int selecionarAlumno(ArrayList<Alumno> alumnosList) {
        int out = 0;
        for (int i = 0; i < alumnosList.size(); i++) {
            System.out.println( (i + 1) + ". " + alumnosList.get(i).getNombre());
        };
        out = EntradaPorTeclado.pedirEntero("Elija el Alumno: ") - 1;
        if( out >= 0 && out < alumnosList.size()){
            return out;
        }else{
            System.out.println("Opcion no valida:");
            return selecionarAlumno(alumnosList);
        }

    }

    private static int mostrarCursosDisponibles(ArrayList<Curso> cursoList) {
        int out = 0;
        for(int i=0; i < cursoList.size(); i++){
            System.out.println( (i + 1) + ". " + cursoList.get(i).getNombre());
        }
        out = EntradaPorTeclado.pedirEntero("Elija el curso: ") - 1 ;
        if ( out >=0 && out < cursoList.size()){
            return out;
        }else{
            System.out.println("Opcion no valida:");
            return mostrarCursosDisponibles(cursoList);
        }

    }

    private static int mostrarMenu() {
        String menu = """
                1. Mostrar cursos disponibles
                2. Matricular nuevo alumno
                3. Agregar nuevo curso
                4. Asignar curso a un alumno
                5. Ver la lista completa de los cursos y los alumnos que tomaron ese curso
                6. Cerrar la aplicación.
                """;
        return EntradaPorTeclado.pedirEntero(menu);
    }

    private static Map<Curso, ArrayList<Alumno>> generarMapAlumnosPorCurso(ArrayList<Curso> cursoList) {
        HashMap<Curso, ArrayList<Alumno>> out = new HashMap<>();
        for (Curso curso : cursoList) {
            out.put(curso, new ArrayList<>());
        }
        return out;
    }
}
