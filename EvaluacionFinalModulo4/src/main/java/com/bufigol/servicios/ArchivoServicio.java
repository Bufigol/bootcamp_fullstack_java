package com.bufigol.servicios;

import com.bufigol.modelo.Alumno;
import com.bufigol.modelo.Materia;
import com.bufigol.modelo.MateriasEnum;
import com.bufigol.utils.ManejoDeArchivos;
import com.bufigol.utils.Validaciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArchivoServicio {
    private List<Alumno> alumnosACargar;
    private PromedioServicioImp promediosServicioImp;
    private AlumnoServicio alumnoServicio;

    public ArchivoServicio() {
        this.promediosServicioImp = new PromedioServicioImp();
        this.alumnoServicio = new AlumnoServicio();
        this.alumnosACargar = new ArrayList<>();
    }

    public List<Alumno> cargarAlumnos(String rutaArchivo) {
        alumnosACargar.clear();
        ArrayList<String[]> lineasCSV = ManejoDeArchivos.readCSV(rutaArchivo, ",");

        for (String[] linea : lineasCSV) {
            if (linea.length < 4) continue; // Ignorar líneas con formato incorrecto

            String rut = linea[0];
            String nombre = linea[1];
            String apellido = linea[2];
            String direccion = linea[3];

            if (!Validaciones.isValidRut(rut)) {
                System.out.println("RUT inválido encontrado: " + rut + ". Se omitirá este alumno.");
                continue;
            }

            Alumno alumno = new Alumno(rut, nombre, apellido, direccion, new ArrayList<>());

            // Procesar materias y notas
            for (int i = 4; i < linea.length; i += 2) {
                String nombreMateria = linea[i];
                String notasStr = linea[i + 1];

                try {
                    MateriasEnum materiaEnum = MateriasEnum.valueOf(nombreMateria.toUpperCase());
                    List<Float> notas = parseNotas(notasStr);
                    Materia materia = new Materia(materiaEnum, notas);
                    alumno.getMaterias().add(materia);
                } catch (IllegalArgumentException e) {
                    System.out.println("Materia inválida encontrada: " + nombreMateria + ". Se omitirá esta materia.");
                }
            }

            alumnosACargar.add(alumno);
        }

        System.out.println("Se han cargado " + alumnosACargar.size() + " alumnos desde el archivo.");
        return alumnosACargar;
    }

    public void guardarAlumnosCargados() {
        for (Alumno alumno : alumnosACargar) {
            alumnoServicio.crearAlumno(alumno);
        }
        System.out.println("Se han guardado " + alumnosACargar.size() + " alumnos en el sistema.");
        alumnosACargar.clear();
    }

    private List<Float> parseNotas(String notasStr) {
        List<Float> notas = new ArrayList<>();
        String[] notasArray = notasStr.split(";");
        for (String nota : notasArray) {
            try {
                notas.add(Float.parseFloat(nota.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Nota inválida encontrada: " + nota + ". Se omitirá esta nota.");
            }
        }
        return notas;
    }

    public void exportarDatos(String ruta) {
        Map<String, Alumno> alumnos = alumnoServicio.listarAlumnos();
        List<String> lineasAEscribir = new ArrayList<>();

        for (Alumno alumno : alumnos.values()) {
            for (Materia materia : alumno.getMaterias()) {
                double promedio = promediosServicioImp.calcularPromedio(materia.getNotas());
                String linea = String.format("Alumno : %s - %s", alumno.getRut(), alumno.getNombre());
                linea += String.format(" - Materia: %s - Promedio: %.2f", materia.getNombre(), promedio);
                lineasAEscribir.add(linea);
            }
        }

        String rutaCompleta = ruta + "/promedios.txt";
        ManejoDeArchivos.writeToFile(rutaCompleta, lineasAEscribir);
        System.out.println("Datos exportados correctamente a: " + rutaCompleta);
    }

    public int contarAlumnosACargar() {
        return this.alumnosACargar.size();
    }

    public void limpiarAlumnosACargar() {
        this.alumnosACargar.clear();
        System.out.println("Se han limpiado los alumnos cargados.");
    }
}