import service.UserService;
import entities.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // Desarrollado por: Omar Agamez - primer commit
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserService service = new UserService();

        System.out.println("🤖 Hola, soy tu entrenador personal");
        System.out.println("Permiteme conocerte mejor querid@ Usuari@");
        // REGISTRO
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nombre: ");
        String name = sc.nextLine();

        System.out.print("Edad: ");
        int age = sc.nextInt();

        System.out.print("Peso (kg): ");
        double weight = sc.nextDouble();

        System.out.print("Altura (m): ");
        double height = sc.nextDouble();
        sc.nextLine();

        System.out.print("Género: ");
        String gender = sc.nextLine();

        User user = new User(id, name, age, weight, height, gender, "", new ArrayList<>());

        boolean created = service.registerUser(user);

        if (created) {
            System.out.println("✅ Usuario registrado correctamente");
        } else {
            System.out.println("❌ Error al registrar usuario");
            return;
        }

        int op=1;
        do {
            System.out.println("BIENVENIDO AL PROGRAMA");
            System.out.println("1. Seleccionar Objetivos");
            System.out.println("2. Configuraciones");
            System.out.println("0. Salir y resumir");
            System.out.print("Opcion: ");
            op= sc.nextInt();
            switch (op){
                case 1:menuGoal(sc,service,id);
                    break;
                case 2:menuUser(sc,service,id);
                    break;
                default:
                    System.out.println("Opción inválida");

            }

        }while(op!=0);

        // MOSTRAR RESULTADO
        User savedUser = service.findUser(id);

        System.out.println("\n📋 Datos del usuario:");
        System.out.println(savedUser);
    }
    static void menuGoal(Scanner sc,UserService service,int id){
        // OBJETIVO PRINCIPAL
        System.out.println("\nSelecciona tu objetivo principal:");
        System.out.println("1. Perder peso");
        System.out.println("2. Ganar músculo");
        System.out.println("3. Mantenerse en forma");
        System.out.println("4. Mejorar la resistencia");
        System.out.println("5. Aumentar la flexibilidad");
        System.out.print("Opcion: ");
        int option = sc.nextInt();
        sc.nextLine();

        String mainGoal = "";

        switch (option) {
            case 1:
                mainGoal = "Perder peso";
                break;
            case 2:
                mainGoal = "Ganar músculo";
                break;
            case 3:
                mainGoal = "Mantenerse en forma";
                break;
            case 4:
                mainGoal = "Mejorar la resistencia";
                break;
            case 5:
                mainGoal = "Aumentar la flexibilidad";
                break;
            default:
                System.out.println("Opción inválida");
        }

        service.assignMainGoal(id, mainGoal);

        // OBJETIVOS SECUNDARIOS
        int choice;

        do {
            System.out.println("\nObjetivos adicionales:");
            System.out.println("1. Reducir estrés");
            System.out.println("2. Mejorar sueño");
            System.out.println("3. Aumentar energía");
            System.out.println("4. Desarrollar hábitos saludables");
            System.out.println("0. Terminar");
            System.out.print("Opcion: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    service.addSecondaryGoal(id, "Reducir estrés");
                    break;
                case 2:
                    service.addSecondaryGoal(id, "Mejorar sueño");
                    break;
                case 3:
                    service.addSecondaryGoal(id, "Aumentar energía");
                    break;
                case 4:
                    service.addSecondaryGoal(id, "Desarrollar hábitos saludables");
                case 0:
                    System.out.println("Finalizando selección...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        } while (choice != 0);
    }
    static void menuUser(Scanner in,UserService service,int code){
        double weight;
        int op=1;
        do {
            System.out.println("Bienvenido a las configuraciones Usuario#"+code);
            System.out.println("Ha seleccionado el menu de actuailizacion de Usuario");
            System.out.println("1. cambiar peso");
            System.out.println("2. cambiar objetivos");
            System.out.print("Opcion: ");
            op=in.nextInt();
            switch (op){
                case 1:
                    User savedUser = service.findUser(code);
                    System.out.print("Peso registrado:"+savedUser.getWeight());
                    System.out.print("Peso actual:");
                    weight= in.nextDouble();
                    service.updateWeight(weight,code);
                    break;
                case 2:
                    menuGoal(in,service,code);
                    break;
                default:
                    System.out.println("Opción inválida");

            }
        }while(op!= 0);



    }

}