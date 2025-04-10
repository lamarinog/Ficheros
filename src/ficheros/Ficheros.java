package ficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Ficheros {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner in = new Scanner(System.in);
        Scanner inStr = new Scanner(System.in);
        int opcion = 0;
        while (opcion != 10) {
            System.out.println("Menu.");
            System.out.println("1. Crear archivo.");
            System.out.println("2. Editar archivo.");
            System.out.println("3. Leer archivo.");
            System.out.println("4. Gestionar fichero 'inventario.txt'.");
            System.out.println("5. Diccionario.");
            System.out.println("6. Salir.");
            System.out.println("Elija la opcion: ");
            opcion = in.nextInt();
            switch (opcion) {
                case 1:
                    crear(inStr);
                    break;
                case 2:
                    editar(inStr, in);
                    break;
                case 3:
                    leer(inStr);
                    break;
                case 4:
                    System.out.println("Ingrese la cantidad: ");
                    int cant = in.nextInt();
                    gestionar(cant);
                    break;
                case 5:
                    diccionario(inStr);
                    break;
                case 6:
                    System.out.println("Se finaliza programa.");
                    break;
                default:
                    System.out.println("Elegir una opcion correcta.");
                    break;
            }
        }
    }

    public static String comprobarArchivo(Scanner inStr) {
        boolean check = true;
        String nombre_dir = "";
        File dir;
        while (check) {
            System.out.println("Escriba el nombre del archivo o '-1' para salir.");
            nombre_dir = inStr.nextLine();
            if (nombre_dir.equals("-1")) {
                check = false;
            } else {
                dir = new File(nombre_dir);
                if (dir.exists()) {
                    if (!dir.isDirectory()) {
                        check = false;
                    }
                }
            }
        }
        return nombre_dir;
    }

    //1
    public static void crear(Scanner inStr) {
        System.out.println("Ingrese el nombre del archivo a crear con su extension: ");
        String nombre_dir = inStr.nextLine();
        File dir = new File(nombre_dir);
        try {
            if (dir.createNewFile()) {
                System.out.println("Fichero creado.");
            } else {
                System.out.println("El fichero ya existe.");
            }
        } catch (IOException e) {
            System.out.println("Existe un error al crear el archivo.");
            e.printStackTrace();
        }
        System.out.println("El nombre del archivo es: " + dir.getName());
        System.out.println("La ruta absoluta del archivo es: " + dir.getPath());
        System.out.println("El peso en kb es: " + dir.length());
    }

    //2
    public static void editar(Scanner inStr, Scanner in) {
        String nombre_dir = comprobarArchivo(inStr);
        if (!nombre_dir.equals("-1")) {
            boolean check = true;
            String texto = "";
            try {
                FileWriter editar = new FileWriter(nombre_dir, true);
                PrintWriter escribir = new PrintWriter(new FileWriter(nombre_dir));
                while (check) {
                    System.out.println("Menu de edicion.");
                    System.out.println("1. Ingresar texto al archivo.");
                    System.out.println("2. Salir de edicion.");
                    int opcion = in.nextInt();
                    if (opcion == 1) {
                        System.out.println("Ingrese el texto nueva al archivo.");
                        texto = inStr.nextLine();
                        editar.write(texto + " - Esto se escribe con FileWriter" + "\n");
                        escribir.println(texto + " - Esto se escribe con PrintWritter");
                    } else if (opcion == 2) {
                        System.out.println("Finaliza la edición.");
                        check = false;
                    } else {
                        System.out.println("Elija una opcion correcta.");
                    }
                }
                escribir.close();
                editar.close();
            } catch (IOException e) {
                System.out.println("Se ha generado un error al editar el archivo o leerlo.");
                e.printStackTrace();
            }
        } else {
            System.out.println("No se realizo la edicion.");
        }
    }

    //3
    public static void leer(Scanner inStr) {
        String nombre_dir = comprobarArchivo(inStr);
        if (!nombre_dir.equals("-1")) {
            int caracter;
            try {
                FileReader leer = new FileReader(nombre_dir);
                System.out.println("Contenido del archivo con FileReader " + nombre_dir + ":");
                while ((caracter = leer.read()) != -1) {
                    System.out.print((char) caracter);
                }
                leer.close();
                BufferedReader leer_buf = new BufferedReader(new FileReader(nombre_dir));
                System.out.println("Contenido del archivo con BufferedReader " + nombre_dir + ":");
                while (leer_buf.readLine() != null) {
                    System.out.print(leer_buf.readLine());
                }
                leer_buf.close();
                Scanner leer_scn = new Scanner(new FileReader(nombre_dir));
                System.out.println("Contenido del archivo con Scanner " + nombre_dir + ":");
                while (leer_scn.hasNextLine()) {
                    System.out.println(leer_scn.nextLine());
                }
                leer_scn.close();
            } catch (IOException e) {
                System.out.println("Se ha generado un error al editar el archivo o leerlo.");
                e.printStackTrace();
            }
        } else {
            System.out.println("No se lee el archivo.");
        }
    }

    //4
    public static void gestionar(int cantidad) {
        Scanner inStr = new Scanner(System.in);
        String nombre_dir = comprobarArchivo(inStr);
        if (!nombre_dir.equals("-1")) {
            String producto = "", precio = "", cant = "";
            try {
                BufferedWriter escr = new BufferedWriter(new FileWriter(nombre_dir));
                for (int i = 0; i < cantidad; i++) {
                    escr.write("linea 1: ");
                    System.out.println("Ingrese el nombre del producto: ");
                    producto = inStr.nextLine();
                    escr.write(producto + " ");
                    System.out.println("Ingrese el precio del producto: ");
                    precio = inStr.nextLine();
                    escr.write(precio + " ");
                    System.out.println("Ingrese la cantidad del producto: ");
                    cant = inStr.nextLine();
                    escr.write(cantidad);
                    escr.newLine();
                }
                escr.close();
                System.out.println("Se han ingresado los datos correctamente.");
                leer_gest(nombre_dir);
            } catch (IOException e) {
                System.out.println("Se ha generado un error al gestionar el archivo.");
                e.printStackTrace();
            }
        } else {
            System.out.println("No se gestiona el archivo.");
        }
    }

    public static void leer_gest(String nombre_dir) {
        try {
            float total = 0;
            BufferedReader leer = new BufferedReader(new FileReader(nombre_dir));
            while (leer.readLine() != null) {
                String[] aux = leer.readLine().split(" ");
                for (int i = 0; i < aux.length; i++) {
                    total += Float.parseFloat(aux[1]) + Float.parseFloat(aux[2]);
                }
            }
            System.out.printf("El total de todo el inventario es: %.2f%n", total);
            leer.close();
        } catch (IOException e) {
            System.out.println("Se ha generado un error al gestionar el archivo.");
            e.printStackTrace();
        }
    }

    //5 INCOMPLETO
    public static void diccionario(Scanner inStr) {
        String nombre_dir = comprobarArchivo(inStr);
        if (!nombre_dir.equals("-1")) {
            try {
                int contador = 0, contador_nu = 0;
                Scanner leer = new Scanner(new BufferedReader(new FileReader(nombre_dir)));
                while (leer.hasNextLine()) {
                    String linea = leer.nextLine();
                    String[] palabras = linea.trim().split("\\s+");
                    if (!linea.trim().isEmpty()) {
                        contador += palabras.length;
                    }
                }
                String[] palabras_nu = new String[contador];
                int opti = 0;
                while (leer.hasNextLine()) {
                    String linea = leer.nextLine();
                    String[] palabras = linea.trim().split("\\s+");
                    for (int i = 0; i < palabras.length; i++) {
                        for (int j = opti; j < palabras_nu.length; j++) {
                            if (palabras_nu[j] == null) {
                                palabras[i] = palabras[i].toLowerCase().replaceAll("[.,-_]", "");
                                palabras_nu[j] = palabras[i];
                                j = palabras_nu.length;
                                opti = j + 1;
                            }
                        }
                    }
                }
                String[] resultado = Arrays.stream(palabras_nu).distinct().toArray(String[]::new);
            } catch (IOException e) {
                System.out.println("Se ha generado un error al gestionar el archivo.");
                e.printStackTrace();
            }
        } else {
            System.out.println("No se gestiona el archivo.");
        }
    }
}
