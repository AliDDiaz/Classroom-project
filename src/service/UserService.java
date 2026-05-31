package service;

import entities.*;
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

    //Función para calcular calorías diarias
    public double calculateCalories(int id){

        User user = repository.findByCode(id);

        if(user == null){
            return 0;
        }

        double weight = user.getWeight();
        double height = user.getHeight() * 100;
        int age = user.getAge();

        double tmb;

        if(user.getGender().equalsIgnoreCase("Masculino")){

            tmb = (10 * weight) + (6.25 * height) - (5 * age) + 5;
        } else {

            tmb = (10 * weight) + (6.25 * height) - (5 * age) - 161;
        }

        return tmb * 1.55;
    }

    public String caloriesRecommendation(int id){

        User user = repository.findByCode(id);

        if(user == null){
            return "Usuario no encontrado";
        }

        double calories = calculateCalories(id);

        switch(user.getMainGoal()){

            case "Perder peso":
                return "Para perder peso se recomienda consumir aproximadamente "
                        + (int)(calories - 400) + "kcal diarias.";

            case "Ganar músculo":
                return "Para ganar músculo se recomienda consumir aproximadamente "
                        + (int)(calories + 300) + "kcal diarias.";

            case "Mantenerse en forma":
                return "Para mantenerse en forma se recomienda consumir aproximadamente"
                        + (int)(calories) + "kcal diarias.";

            default:
                return "Consumo recomendado aproximado: "
                        + (int)(calories) +"kcal diarias.";
        }
    }

    //Función que genera las rutinas
    public String generateRoutine(int id){

        User user = repository.findByCode(id);

        if(user == null){
            return "Usuario no encontrado.";
        }

        Routine routine;

        // OBJETIVO PRINCIPAL
        switch (user.getMainGoal()){

            case "Perder peso":
                routine = new WeightLossRoutine();
                break;

            case "Ganar músculo":
                routine = new MuscleGainRoutine();
                break;

            case "Mantenerse en forma":
                routine = new StayingFitRoutine();
                    break;

            case "Mejorar la resistencia":
                routine = new ImproveResistenceRoutine();
                break;

            case "Aumentar la flexibilidad":
                routine = new IncreaseFlexibilityRoutine();
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
