package com.bufigol.s12.main;

import com.bufigol.s12.modelo.Producto;
import com.bufigol.utils.EntradaPorTeclado;
import org.apache.poi.ss.formula.atp.Switch;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EVM4S12 {
    public static void main(String[] args) {
        int opc;
        ArrayList<Producto> listadoDeProductos = cargarListadoDeProductos();
        HashMap<String,Integer> listadoDeCompras = new HashMap<>();
        do {
            opc = mostrarMenu();
            switch (opc) {
                case 1:
                    mostrarProductos(listadoDeProductos);
                    break;
                case 2:
                    listadoDeCompras = agregarAlListadoDeCompras(listadoDeCompras, listadoDeProductos);
                    break;
                case 3:
                    listadoDeCompras = parcelarListadoDeCompras(listadoDeCompras, listadoDeProductos);
                    break;
                case 4:
                    System.out.println("Hasta Luego!");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        } while (opc != 4);
    }

    private static HashMap<String, Integer> parcelarListadoDeCompras(HashMap<String, Integer> listadoDeCompras,
                                                                     ArrayList<Producto> listadoDeProductos) {
        HashMap<String, Integer> output;
        double total = 0;
        for (Producto listadoDeProducto : listadoDeProductos) {
            double aux = listadoDeProducto.getPrecio();
            if(listadoDeCompras.containsKey(listadoDeProducto.getCodigo())){
                aux *= listadoDeCompras.get(listadoDeProducto.getCodigo());
                total += aux;
            }
        }
        System.out.println("El total es: $" + total + " ¿Desea pagar ahora o seguir comprando?");
        int opc = EntradaPorTeclado.pedirEntero("""
                1. Pagar ahora
                2. Seguir comprando
                """);
        if(opc == 1){
            System.out.println("Gracias por su compra");
            output = new HashMap<>();
        }else {
            System.out.println("Continue con su compra");
            output = listadoDeCompras;
        }

        return output;
    }

    private static HashMap<String, Integer> agregarAlListadoDeCompras(HashMap<String, Integer> listadoDeCompras,
                                                                      ArrayList<Producto> listadoDeProductos) {
        HashMap<String, Integer> output = listadoDeCompras;
        int opc;
        boolean seguir = true;
        do {
            mostrarProductos(listadoDeProductos);
            opc = EntradaPorTeclado.pedirEntero("Elija un producto");
            if( opc > 0 && opc <= listadoDeProductos.size() ){
                seguir = false;
                if(output.containsKey(listadoDeProductos.get(opc-1).getCodigo())){
                    output.put(listadoDeProductos.get(opc-1).getCodigo(),output.get(listadoDeProductos.get(opc-1).getCodigo())+1);
                }else{
                    output.put(listadoDeProductos.get(opc-1).getCodigo(),1);
                }
            }else{
                System.out.println("Opcion no valida");
            }
        }while(seguir);
        System.out.println("Agregado");
        System.out.println("Tiene: " + output.get(listadoDeProductos.get(opc-1).getCodigo())
                + " " + listadoDeProductos.get(opc-1).getNombre() + " en su carrito\n");
        return output;
    }

    private static void mostrarProductos(ArrayList<Producto> listadoDeProductos) {
        for(int i=0;i<listadoDeProductos.size();i++){
            System.out.println( (i+1) + "- " +
                    listadoDeProductos.get(i).getNombre() +
                    " $" + listadoDeProductos.get(i).getPrecio());
        }
        System.out.println();
    }

    private static ArrayList<Producto> cargarListadoDeProductos() {
        ArrayList<Producto> listadoDeProductos = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/listadoDeProductos.xlsx");
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            String nombre="";
            int precio=0;
            String codigo="";
            for (Row row : sheet) {
                // Iterate over each cell in the row
                for (Cell cell : row) {
                    switch (cell.getColumnIndex()) {
                        case 0:
                            nombre = cell.getStringCellValue();
                            break;
                        case 1:
                            precio = (int) cell.getNumericCellValue();
                            break;
                        case 2:
                            codigo = cell.getStringCellValue();
                            listadoDeProductos.add(new Producto(nombre, precio,codigo));
                            break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listadoDeProductos;
    }

    private static int mostrarMenu() {
        String menu = """
                      1- Ver la lista de productos disponibles
                      2- Agregar un producto a mi lista de compras
                      3- Pagar mi lista de compras
                      4- Salir
                      Seleccione una opción:\s""";
        return EntradaPorTeclado.pedirEntero(menu);
    }
}
