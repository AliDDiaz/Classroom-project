package service;

import entities.User;
import repositories.UserRepository;

import java.util.ArrayList;

public class UserService {

    private UserRepository repository = new UserRepository();

    public boolean registerUser(User user){

        if(user.getAge() <= 0 || user.getAge() > 120){
            System.out.println("Edad inválida. Intente nuevamente.");
            return false;
        }

        if(user.getWeight() <= 0){
            System.out.println("Peso inválido. Intente nuevamente.");
            return false;
        }

        if(user.getHeight() <= 0){
            System.out.println("Altura inválida. Intente nuevamente.");
            return false;
        }

        if(user.getName() == null || user.getName().isEmpty()){
            System.out.println("Nombre inválido. Intente nuevamente.");
            return false;
        }

        if(repository.findByCode(user.getId()) != null){
            System.out.println("El usuario ya existe.");
            return false;
        }

        repository.create(user);
        return true;
    }

    public double calculateIMC(int id){

        User user = repository.findByCode(id);

        if(user != null){

            return user.getWeight() / (user.getHeight() * user.getHeight());
        }

        return 0;

    }

    public String bmiRecommendation(int id){

        double bmi = calculateIMC(id);

        if(bmi < 18.5){

            return "Tu IMC indica bajo peso." + "Se recomienda mejorar la " +
                    "alimentación y seguir una rutina moderada.";
        } else if(bmi < 25) {

            return "Tu IMC es normal. Continúa manteniendo hábitos saludables.";

        } else if(bmi < 30) {

            return "Tu IMC indica sobrepeso." +
                    "Se recomienda realizar actividad cardiovascular.";

        } else {

            return "Tu IMC indica obesidad. Se recomienda consultar " +
                    "un especialista y realizar actividad física controlada.";

        }

    }

    //Función que genera las rutinas
    public String generateRoutine(int id){

        User user = repository.findByCode(id);

        if(user == null){
            return "Usuario no encontrado.";
        }

        String routine = "";

        // OBJETIVO PRINCIPAL
        switch (user.getMainGoal()){

            case "Perder peso":
                routine += """
                    
                    Rutina principal:
                    - Cardio 30 minutos
                    - Caminata rápida
                    - Bicicleta
                    """;
                break;

            case "Ganar músculo":
                routine += """
                    
                    Rutina principal:
                    - Pesas
                    - Flexiones
                    - Sentadillas
                    """;
                break;

            case "Mantenerse en forma":
                routine += """
                    
                    Rutina principal:
                    - Trote suave
                    - Estiramientos
                    """;
                break;

            case "Mejorar la resistencia":
                routine += """
                    
                    Rutina principal:
                    - Running
                    - Circuitos HIIT
                    """;
                break;

            case "Aumentar la flexibilidad":
                routine += """
                    
                    Rutina principal:
                    - Yoga
                    - Pilates
                    - Estiramientos diarios
                    """;
                break;
        }

        // OBJETIVOS SECUNDARIOS
        routine += "\nRecomendaciones adicionales:\n";

        for(String goal : user.getSecondaryGoals()){

            switch (goal){

                case "Reducir estrés":
                    routine += "- Meditación 10 minutos\n";
                    break;

                case "Mejorar sueño":
                    routine += "- Dormir mínimo 8 horas\n";
                    break;

                case "Aumentar energía":
                    routine += "- Mantener buena hidratación\n";
                    break;

                case "Desarrollar hábitos saludables":
                    routine += "- Mantener horarios constantes\n";
                    break;
            }
        }

        return routine;
    }

    public ArrayList<User> getAllUser(){

        return repository.getAll();

    }

    public User findUser(int id){

        return repository.findByCode(id);

    }

    public boolean deleteUser(int id){

        return repository.deleteByCode(id);

    }
    public void updateWeight(double weight,int id){
        if(weight <= 0){
            System.out.println("Peso inválido");
        }
        else {
            if(repository.weightUpdater(weight,id)){
                System.out.println("Actualizado correctamente");
            }
            else {
                System.out.println("No fue posible actualizar el peso");
            }
        }
    }
    public void assignMainGoal(int userId, String goal){
        User user = repository.findByCode(userId);

        if (user != null){
            user.setMainGoal(goal);
        }
    }

    public void addSecondaryGoal(int userId, String goal){
        User user = repository.findByCode(userId);

        if (user != null){

            if(!user.getSecondaryGoals().contains(goal)){
                user.getSecondaryGoals().add(goal);
            } else {

                System.out.println("Ese objetivo ya fue agregado.");
            }

        }
    }

}
