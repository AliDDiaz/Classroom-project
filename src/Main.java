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

        int op;

        do {

            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Registrar usuario.");
            System.out.println("2. Mostrar usuarios.");
            System.out.println("3. Buscar usuario.");
            System.out.println("4. Eliminar usuario.");
            System.out.println("5. Ingresar a usuario.");
            System.out.println("0. Salir.");
            System.out.print("Opción: ");

            try {
                op = sc.nextInt();
                sc.nextLine();
            } catch (Exception e){

                System.out.println("Debe ingresar un número.");
                sc.nextLine();
                op = -1;
            }

            switch(op){

                case 1:
                    registerUserMenu(sc, service);
                    break;

                case 2:
                    showAllUsers(service);
                    break;

                case 3:
                    searchUser(sc, service);
                    break;

                case 4:
                    deleteUser(sc, service);
                    break;

                case 5:
                    accessUser(sc, service);
                    break;

                case 0:
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        }while(op != 0);

    }

    static void registerUserMenu(Scanner sc, UserService service){

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

        if(created){

            System.out.println("Usuario registrado correctamente.");
        } else {

            System.out.println("Error al registrar usuario.");

        }
    }

    static void showAllUsers(UserService service){

        ArrayList<User> users = service.getAllUser();

        if(users.isEmpty()){

            System.out.println("No hay usuarios registrados.");
            return;
        }

        System.out.println("\n===== LISTA DE USUARIOS =====");

        for(User user : users){

            System.out.println("ID: " + user.getId() + " | Nombre: "
                    + user.getName() + " | Edad: " + user.getAge());

        }

    }

    static void searchUser(Scanner sc, UserService service){

        int id;

        System.out.println("Ingrese ID del usuario: ");

        try {
            id = sc.nextInt();
            sc.nextLine();

        }catch (Exception e){

            System.out.println("Debe ingresar un número válido.");
            sc.nextLine();
            id = -1;
        }

        if(id > 0) {

            User user = service.findUser(id);

            if (user != null) {

                System.out.println(user);

            } else {

                System.out.println("Usuario no encontrado.");

            }

        }

    }

    static void deleteUser(Scanner sc, UserService service){

        int id;

        System.out.println("Ingrese ID del usuario a eliminar: ");

        try {
            id = sc.nextInt();
            sc.nextLine();

        }catch (Exception e){

            System.out.println("Debe ingresar un número válido.");
            sc.nextLine();
            id = -1;
        }

        if(id > 0) {
            if (service.deleteUser(id)) {

                System.out.println("Usuario eliminado.");

            } else {

                System.out.println("No existe un usuario con ese ID.");

            }

        }

    }

    static void accessUser(Scanner sc, UserService service){

        int id;

        System.out.print("Ingrese ID del usuario: ");

        try {

            id = sc.nextInt();
            sc.nextLine();

        } catch (Exception e){

            System.out.println("Debe ingresar un número válido.");
            sc.nextLine();
            return;
        }

        User user = service.findUser(id);

        if(user == null){

            System.out.println("Usuario no encontrado.");
            return;
        }

        int op;

        do {

            System.out.println("\n===== MENÚ DE USUARIO =====");
            System.out.println("1. Ver información completa");
            System.out.println("2. Ver salud y recomendaciones");
            System.out.println("3. Configuraciones");
            System.out.println("4. Objetivos");
            System.out.println("0. Volver");
            System.out.print("Opción: ");

            try {

                op = sc.nextInt();
                sc.nextLine();

            } catch (Exception e){

                System.out.println("Debe ingresar un número.");
                sc.nextLine();
                op = -1;
            }

            switch (op){

                case 1:
                    showDataUser(service, id);
                    break;

                case 2:
                    showHealthData(service, id);
                    break;

                case 3:
                    menuUser(sc, service, id);
                    break;

                case 4:
                    goalsMenu(sc, service, id);
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        }while(op != 0);

    }

    static void showDataUser(UserService service, int id){

        User savedUser = service.findUser(id);

        if(savedUser == null){

            System.out.println("Usuario no encontrado.");
            return;
        }

        System.out.println("\n===== INFORMACIÓN DEL USUARIO =====");
        System.out.println(savedUser);

    }

    static void showHealthData(UserService service, int id){

        User savedUser = service.findUser(id);

        if(savedUser == null){

            System.out.println("Usuario no encontrado.");
            return;
        }

        System.out.println("\n===== SALUD Y RECOMENDACIONES =====");

        double imc = service.calculateIMC(id);

        System.out.printf("IMC: %.2f\n", imc);

        System.out.println(service.bmiRecommendation(id));

        double calories = service.calculateCalories(id);

        System.out.printf("Calorías diarias estimadas: %.2f kcal\n", calories);

        System.out.println(service.caloriesRecommendation(id));

        if(savedUser.getMainGoal().isEmpty()){

            System.out.println("\nNo seleccionaste objetivos.");

        } else {

            System.out.println("\n===== RUTINAS =====");

            System.out.println(service.generateRoutine(id));

        }

    }

    static void goalsMenu(Scanner sc, UserService service, int id){

        int op;

        do {

            System.out.println("\n===== MENÚ DE OBJETIVOS =====");
            System.out.println("1. Configurar objetivos");
            System.out.println("2. Ver rutinas");
            System.out.println("0. Volver");
            System.out.print("Opción: ");

            try {

                op = sc.nextInt();
                sc.nextLine();

            } catch (Exception e){

                System.out.println("Debe ingresar un número.");
                sc.nextLine();
                op = -1;
            }

            switch (op){

                case 1:
                    menuGoal(sc, service, id);
                    break;

                case 2:
                    System.out.println(service.generateRoutine(id));
                    break;

                case 0:
                    System.out.println("Saliendo del menú de objetivos...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        }while(op != 0);

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

        if(!mainGoal.isEmpty()) {
            service.assignMainGoal(id, mainGoal);
        }

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
            System.out.println("Ha seleccionado el menu de actuailización de Usuario");
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
                    goalsMenu(in, service, code);
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
            } else if(!name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")){
                System.out.println("El nombre solo debe contener letras.");
            }
        }while(name.trim().isEmpty() ||
                !name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));

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