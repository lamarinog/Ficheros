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

    //MENU DEL EJERCICIO
    public static void menu() {
        Scanner in = new Scanner(System.in);
        Scanner inStr = new Scanner(System.in);
        int opcion = 0;
        while (opcion != 6) {
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
                    System.out.println("Ingrese la cantidad de productos a ingresar: ");
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

    //COMPRUEBA SI ESXISTE EL ARCHIVO Y NO ES UN DIRECTORIO
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

    //1 CREAR ARCHIVO EN CASO NO EXISTA
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

    //2 EDITAR EL ARCHIVO
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

    //3 LEER EL ARCHIVO
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
                System.out.println();
                System.out.println("Contenido del archivo con BufferedReader " + nombre_dir + ":");
                while ((caracter = leer_buf.read()) != -1) {
                    System.out.print((char) caracter);
                }
                leer_buf.close();
                Scanner leer_scn = new Scanner(new FileReader(nombre_dir));
                System.out.println();
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

    //4 GESTIONAR EL INVENTARIO
    public static void gestionar(int cantidad) {
        Scanner inStr = new Scanner(System.in);
        String nombre_dir = comprobarArchivo(inStr);
        if (!nombre_dir.equals("-1")) {
            String producto = "", precio = "", cant = "";
            try {
                BufferedWriter escr = new BufferedWriter(new FileWriter(nombre_dir));
                for (int i = 0; i < cantidad; i++) {
                    System.out.println("Ingrese el nombre del producto: ");
                    producto = inStr.nextLine();
                    escr.write(producto + " ");
                    System.out.println("Ingrese el precio del producto: ");
                    precio = inStr.nextLine();
                    escr.write(precio + " ");
                    System.out.println("Ingrese la cantidad del producto: ");
                    cant = inStr.nextLine();
                    escr.write(cant);
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
            int i = 1;
            Scanner leer = new Scanner(new BufferedReader(new FileReader(nombre_dir)));
            while (leer.hasNextLine()) {
                String linea = leer.nextLine();
                String[] aux = linea.trim().split("\\s+");
                System.out.println("Producto " + i);
                System.out.println("Nombre: " + aux[0] + ", Precio: " + aux[1] + ", Cantidad: " + aux[2]);
                total += Float.parseFloat(aux[1]) * Float.parseFloat(aux[2]);
                i++;
            }
            System.out.printf("El total de todo el inventario es: %.2f%n", total);
            leer.close();
        } catch (IOException e) {
            System.out.println("Se ha generado un error al gestionar el archivo.");
            e.printStackTrace();
        }
    }

    //METODOS DE DICCIONARIO LITERAL 5
    public static void ordenar(String[] resultado) {
        String temp = "";
        for (int i = 0; i < resultado.length; i++) {
            for (int j = 1; j < (resultado.length - i); j++) {
                if (resultado[j - 1].compareTo(resultado[j]) > 0) {
                    temp = resultado[j - 1];
                    resultado[j - 1] = resultado[j];
                    resultado[j] = temp;
                }
            }
        }
        System.out.println("Luego de la ordenacion: ");
        System.out.println(Arrays.toString(resultado));
    }

    //CREAR EL ARRAY CON LA CANTIDAD DE PALABRAS Y LAS LIMPIA
    public static void limpiarArray(String[] palabras_nu, String nombre_dir) {
        try {
            Scanner leer = new Scanner(new BufferedReader(new FileReader(nombre_dir)));
            int opti = 0;
            while (leer.hasNextLine()) {
                String linea = leer.nextLine();
                String[] palabras = linea.trim().split("\\s+");
                for (int i = 0; i < palabras.length; i++) {
                    for (int j = opti; j < palabras_nu.length; j++) {
                        if (palabras_nu[j] == null) {
                            palabras[i] = palabras[i].toLowerCase().replaceAll("[.,-_]", "");
                            palabras_nu[j] = palabras[i];
                            opti = j + 1;
                            j = palabras_nu.length;
                        }
                    }
                }
            }
            leer.close();
        } catch (IOException e) {
            System.out.println("Se ha generado un error al gestionar el archivo.");
            e.printStackTrace();
        }
    }

    //GENERA EL DICCIONARIO.TXT Y SI YA EXISTE NO LO HACE.
    public static void generar(String[] palabras) {
        File dir = new File("diccionario.txt");
        try {
            if (dir.createNewFile()) {
                System.out.println("Fichero creado.");
                BufferedWriter escr = new BufferedWriter(new FileWriter("diccionario.txt"));
                for (int i = 0; i < palabras.length; i++) {
                    escr.write(palabras[i]);
                    escr.newLine();
                }
                escr.write("Total de palabras: " + Integer.toString(palabras.length) + ".");
                escr.close();
            } else {
                System.out.println("El fichero ya existe.");
            }
        } catch (IOException e) {
            System.out.println("Existe un error al crear el archivo.");
            e.printStackTrace();
        }
    }

    //5 DICCIONARIO, ORDENACION
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
                leer.close();
                String[] palabras_nu = new String[contador];
                limpiarArray(palabras_nu, nombre_dir);
                System.out.println("Palabras sin limpiar: ");
                System.out.println(Arrays.toString(palabras_nu));
                String[] resultado = Arrays.stream(palabras_nu).distinct().toArray(String[]::new);
                System.out.println("Antes de la ordenacion: ");
                System.out.println(Arrays.toString(resultado));
                ordenar(resultado);
                generar(resultado);
                System.out.println("Se encontraron " + contador + " palabras al inicio del proceso.");
                System.out.println("Se encontraron " + resultado.length + " palabras al final del proceso.");
            } catch (IOException e) {
                System.out.println("Se ha generado un error al gestionar el archivo.");
                e.printStackTrace();
            }
        } else {
            System.out.println("No se gestiona el archivo.");
        }
    }

}
