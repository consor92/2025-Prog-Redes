import java.util.Scanner;

public class MainMenu {


    public static void main(String[] args) {
        while (true) {
            mostrarMenuPrincipal();
            int opcion = pedirNumero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
					//pueden ser static
                    ClassEjercicios1.mostrarSubmenu();
                    break;
                case 2:
					//o se puede instacion el objeto
					ClassEjercicios1 ce1 = new ClassEjercicios1();
                    ce1.mostrarSubmenu();
                    break;
                case 0:
                    System.out.println(AnsiColor.GREEN + "¡Hasta luego!" + AnsiColor.RESET);
                    return;
                default:
                    System.out.println(AnsiColor.RED + "Opción no válida. Intente nuevamente." + AnsiColor.RESET);
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println(AnsiColor.BLUE + "===============================");
        System.out.println("         MENÚ PRINCIPAL");
        System.out.println("===============================" + AnsiColor.RESET);
        System.out.println(AnsiColor.YELLOW + " 1. Gestión de Proyectos");
        System.out.println(" 2. Gestión de Miembros");
        System.out.println(" 0. Salir" + AnsiColor.RESET);
    }

}




public class ClassEjercicios1 {


    public static void mostrarSubmenu() {
        while (true) {
            System.out.println(AnsiColor.PURPLE + "\n--- Gestión Ejercicios Punto 1 ---" + AnsiColor.RESET);
            System.out.println(" 1. ");
            System.out.println(" 2. ");
            System.out.println(" 0. Volver al menú principal");

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    ejercicioA1();
                    break;
                case 2:
                    ejercicioB1();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(AnsiColor.RED + "Opción no válida." + AnsiColor.RESET);
            }
        }
    }

    private static void ejercicioA1() {
        System.out.println(AnsiColor.GREEN + "Resolviendo ejercicio 1 (a)..." + AnsiColor.RESET);
        // lógica 
    }

    private static void ejercicioB1() {
        System.out.println(AnsiColor.GREEN + "Resolviendo ejercicio 1 (b)..." + AnsiColor.RESET);
        // lógica 
    }
}
