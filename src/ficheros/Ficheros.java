package ficheros;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Blanca
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
            System.out.println("10. Salir.");
            System.out.println("Elija la opcion: ");
            opcion = in.nextInt();
            switch (opcion) {
                case 1:
                    crear(inStr);
                    break;
                case 2:
                    editar(inStr, in);
                    break;
                case 10:
                    System.out.println("Se finaliza programa.");
                    break;
                default:
                    System.out.println("Elegir una opcion correcta.");
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
        boolean check = true;
        int caracter;
        String texto = "";
        String nombre_dir = comprobarArchivo(inStr);
        if (!nombre_dir.equals("-1")) {
            try {
                FileWriter editar = new FileWriter(nombre_dir, true);
                FileReader leer = new FileReader(nombre_dir);
                while (check) {
                    System.out.println("Menu de edicion.");
                    System.out.println("1. Ingresar texto al archivo.");
                    System.out.println("2. Salir de edicion.");
                    int opcion = in.nextInt();
                    if (opcion == 1) {
                        System.out.println("Ingrese el texto nueva al archivo.");
                        texto = inStr.nextLine();
                        editar.write(texto + "\n");
                    } else if (opcion == 2) {
                        System.out.println("Finaliza la edición.");
                        check = false;
                    } else {
                        System.out.println("Elija una opcion correcta.");
                    }
                }
                System.out.println("Contenido del archivo " + nombre_dir + ":");
                while ((caracter = leer.read()) != -1) {
                    System.out.print((char) caracter);
                }
                leer.close();
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
    //public
}
