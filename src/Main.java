import entities.*;
import service.UserService;

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

        ArrayList<Double> history = new ArrayList<>();
        history.add(weight);

        User user = new User(id, name, age, weight, height, gender, "", new ArrayList<>(), history);

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
            try {

                op = sc.nextInt();
            }catch (Exception e){

                System.out.println("Opción inválida.");
                sc.nextLine();
                op = -1;
            }
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
        int option;
        try {

            option = sc.nextInt();
            sc.nextLine();
        } catch (Exception e){

            System.out.println("Debe ingresar un número.");
            sc.nextLine();
            option = -1;
        }

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
            try {

                choice = sc.nextInt();
                sc.nextLine();

            }catch (Exception e){

                System.out.println("Debe ingresar un número.");
                sc.nextLine();
                choice = -1;
            }

            switch (choice) {
                case 1:
                    service.addSecondaryGoal(id, new StressReductionGoal());
                    break;
                case 2:
                    service.addSecondaryGoal(id, new SleepImprovementGoal());
                    break;
                case 3:
                    service.addSecondaryGoal(id, new EnergyBoostGoal());
                    break;
                case 4:
                    service.addSecondaryGoal(id, new HealthyHabitsGoal());
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
            try {

                op = in.nextInt();
                in.nextLine();
            }catch (Exception e){

                System.out.println("Debe ingresar un número.");
                in.nextLine();
                op = -1;

            }
            switch (op){
                case 1:
                    User savedUser = service.findUser(code);
                    System.out.println("Peso registrado: " + savedUser.getWeight());
                    System.out.print("Peso actual: ");

                    try {

                        weight = in.nextDouble();
                        in.nextLine();
                    }catch (Exception e){

                        System.out.println("Debe ingresar un número válido.");
                        in.nextLine();
                        weight = -1;
                    }

                    if(weight > 0) {
                        service.updateWeight(weight, code);
                    }
                    
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
            try {

            id = sc.nextInt();
            sc.nextLine();

            if (id <= 0) {
                System.out.println("ID inválido.");
            }
            if (service.findUser(id) != null) {
                System.out.println("Ese ID ya existe.");
            }

        } catch (Exception e){

                System.out.println("Debe inggresar un número válido.");
                sc.nextLine();
                id = -1;
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
            try {

                age = sc.nextInt();
                sc.nextLine();

                if (age <= 0 || age > 120) {
                    System.out.println("Edad inválida. Intente nuevamente.");
                }

            }catch (Exception e){

                System.out.println("Debe ingresar una edad válida.");
                sc.nextLine();
                age = -1;
            }
        }while(age <= 0 || age > 120);

        return age;
    }

    static double readValidWeight(Scanner sc){

        double weight;
        do {
            System.out.print("Peso (kg): ");
            try {

                weight = sc.nextDouble();
                sc.nextLine();
                if (weight <= 0) {
                    System.out.println("Peso inválido. Intente nuevamente.");
                }

            }catch (Exception e){

                System.out.println("Debe ingresar un peso válido.");
                sc.nextLine();
                weight = -1;
            }
        }while(weight <= 0);

        return weight;
    }

    static double readValidHeight(Scanner sc){

        double height;
        do {
            System.out.print("Altura (m): ");
            try {

                height = sc.nextDouble();
                sc.nextLine();
                if (height <= 0) {
                    System.out.println("Altura inválida. Intente nuevamente.");
                }

            }catch (Exception e){

                System.out.println("Debe ingresar una altura válida.");
                sc.nextLine();
                height = -1;
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

            try {

                option = sc.nextInt();
                sc.nextLine();

            }catch (Exception e){

                System.out.println("Debe ingresar 1 0 2.");
                sc.nextLine();
                option = -1;
            }
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