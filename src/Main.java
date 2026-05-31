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

        int id = readValidId(sc, service);

        String name = readValidName(sc);

        int age = readValidAge(sc);

        double weight = readValidWeight(sc);

        double height = readValidHeight(sc);

        String gender = readValidGender(sc);

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
                case 0:
                    System.out.println("Programa finalizado. Gracias por usar nuestros servicios.");
                    break;
                default:
                    System.out.println("Opción inválida");

            }

        }while(op!=0);

        // INVOCACION
        showDataUser(service,id);
    }

    static void showDataUser(UserService service,int id){
        User savedUser = service.findUser(id);

        System.out.println("\n📋 Datos del usuario:");
        System.out.println(savedUser);

        double imc = service.calculateIMC(id);

        System.out.printf("IMC: %.2f\n", imc);

        System.out.println(service.bmiRecommendation(id));
        if(savedUser.getMainGoal().isEmpty()){
            System.out.println("\nNo seleccionaste objetivos.");
        } else {
            System.out.println(service.generateRoutine(id));
        }
        
        double calories = service.calculateCalories(id);

        System.out.printf("Calorías diarias estimadas: %.2f kcal\n", calories);
        System.out.println(service.caloriesRecommendation(id));

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
                    break;
                case 0:
                    System.out.println("Finalizando selección...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        } while (choice != 0);
    }
    static void menuUser(Scanner in,UserService service,int code){
        System.out.println("INFORMACION REGISTRADA DE USUARIO:");
        showDataUser(service,code);
        double weight;
        int op;
        do {
            System.out.println("Bienvenido a las configuraciones Usuario#"+code);
            System.out.println("Ha seleccionado el menu de actuailizacion de Usuario");
            System.out.println("1. cambiar peso");
            System.out.println("2. cambiar objetivos");
            System.out.println("0. Terminar");
            System.out.print("Opcion: ");
            op=in.nextInt();
            switch (op){
                case 1:
                    User savedUser = service.findUser(code);
                    System.out.println("Peso registrado: " + savedUser.getWeight());
                    System.out.print("Peso actual: ");
                    weight= in.nextDouble();
                    in.nextLine();
                    service.updateWeight(weight,code);
                    break;
                case 2:
                    menuGoal(in,service,code);
                    break;
                case 0:
                    System.out.println("Configuraciones terminadas.");
                    break;
                default:
                    System.out.println("Opción inválida");

            }
        }while(op!= 0);

    }

    //Métodos

    static int readValidId(Scanner sc, UserService service){

        int id;
        do {
            System.out.print("ID: ");
            id = sc.nextInt();
            sc.nextLine();

            if(id <= 0){
                System.out.println("ID inválido.");
            }
            if(service.findUser(id) != null){
                System.out.println("Ese ID ya existe.");
            }

        }while(id <= 0 || service.findUser(id) != null);

        return id;
    }

    static String readValidName(Scanner sc){

        String name;
        do {
            System.out.print("Nombre: ");
            name = sc.nextLine();

            if(name.trim().isEmpty()){
                System.out.println("Nombre inválido. Intente nuevamente.");
            }
        }while(name.trim().isEmpty());

        return name;
    }

    static int readValidAge(Scanner sc){

        int age;
        do {
            System.out.print("Edad: ");
            age = sc.nextInt();

            if(age <= 0 || age > 120){
                System.out.println("Edad inválida. Intente nuevamente.");
            }
        }while(age <= 0 || age > 120);

        return age;
    }

    static double readValidWeight(Scanner sc){

        double weight;
        do {
            System.out.print("Peso (kg): ");
            weight = sc.nextDouble();
            if(weight <= 0){
                System.out.println("Peso inválido. Intente nuevamente.");
            }
        }while(weight <= 0);

        return weight;
    }

    static double readValidHeight(Scanner sc){

        double height;
        do {
            System.out.print("Altura (m): ");
            height = sc.nextDouble();
            sc.nextLine();
            if(height <= 0){
                System.out.println("Altura inválida. Intente nuevamente.");
            }
        }while(height <= 0);

        return height;
    }

    static String readValidGender(Scanner sc){

        int option;
        String gender = "";

        do {
            System.out.println("Seleccione su género:");
            System.out.println("1. Masculino");
            System.out.println("2. Femenino");
            System.out.print("Opción: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option){

                case 1:

                    gender = "Masculino";
                    break;

                case 2:
                    gender = "Femenino";
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

        }while(option != 1 && option != 2);

        return gender;
    }

}