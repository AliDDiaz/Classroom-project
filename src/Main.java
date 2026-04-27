import service.UserService;
import entities.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserService service = new UserService();

        System.out.println("🤖 Hola, soy tu entrenador personal");

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

        // OBJETIVO PRINCIPAL
        System.out.println("\nSelecciona tu objetivo principal:");
        System.out.println("1. Perder peso");
        System.out.println("2. Ganar músculo");
        System.out.println("3. Mantenerse en forma");

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
            System.out.println("0. Terminar");

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
                case 0:
                    System.out.println("Finalizando selección...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        } while (choice != 0);

        // MOSTRAR RESULTADO
        User savedUser = service.findUser(id);

        System.out.println("\n📋 Datos del usuario:");
        System.out.println(savedUser);
    }
}