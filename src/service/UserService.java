package service;

import entities.*;
import repositories.UserRepository;

import java.util.ArrayList;

public class UserService implements IUserService{

    private UserRepository repository = new UserRepository();
    @Override
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
    @Override
    public double calculateIMC(int id){

        User user = repository.findByCode(id);

        if(user != null){

            return user.getWeight() / (user.getHeight() * user.getHeight());
        }

        return 0;

    }
    @Override
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
    @Override
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
    @Override
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
    @Override
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

            default:
                return "No hay rutina disponible.";
        }

        String routineText = "Rutina principal:\n";
        routineText += routine.generateRoutine();
        // OBJETIVOS SECUNDARIOS
        routineText += "\nRecomendaciones adicionales:\n";
        for(SecondaryGoals goal : user.getSecondaryGoals()){

            routineText += goal.getRecommendation() + "\n";

        }

        return routineText;
    }
    @Override
    public ArrayList<User> getAllUser(){

        return repository.getAll();

    }
    @Override
    public User findUser(int id){

        return repository.findByCode(id);

    }
    @Override
    public boolean deleteUser(int id){

        return repository.deleteByCode(id);

    }

    @Override
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

    public String showWeightHistory(int id){

        User user = repository.findByCode(id);

        if(user == null){
            return "Usuario no encontrado.";
        }

        String history = "===== HISTORIAL DE PESO =====\n";

        int count = 1;

        for(Double weight : user.getWeightHistory()){

            history += "Registro " + count + ": "
                    + weight + " kg\n";

            count++;
        }

        return history;
    }

    public String showProgress(int id){

        User user = repository.findByCode(id);

        if(user == null){
            return "Usuario no encontrado.";
        }

        ArrayList<Double> history = user.getWeightHistory();

        if(history.isEmpty()){
            return "No hay datos de progreso.";
        }

        double initialWeight = history.get(0);
        double currentWeight = user.getWeight();

        double difference = currentWeight - initialWeight;

        String progress = "===== PROGRESO =====\n";
        progress += "Peso inicial: " + initialWeight + " kg\n";
        progress += "Peso actual: " + currentWeight + " kg\n";

        if(difference < 0){

            progress += "Has perdido "
                    + Math.abs(difference)
                    + " kg";

        }else if(difference > 0){

            progress += "Has ganado "
                    + difference
                    + " kg";

        }else{

            progress += "No hay cambios en el peso.";

        }

        return progress;
    }

    @Override
    public void assignMainGoal(int userId, String goal){
        User user = repository.findByCode(userId);

        if (user != null){
            user.setMainGoal(goal);
        }
    }
    @Override
    public void addSecondaryGoal(int userId, SecondaryGoals goal){

        User user = repository.findByCode(userId);

        if (user != null){

            for(SecondaryGoals g : user.getSecondaryGoals()){

                if(g.getName().equalsIgnoreCase(goal.getName())){

                    System.out.println("Ese objetivo ya fue agregado.");
                    return;
                }
            }

            user.getSecondaryGoals().add(goal);
            System.out.println("Objetivo agregado correctamente.");
        }
    }

    public void addHabit(int userId, Habit habit){

        User user = repository.findByCode(userId);

        if(user != null){

            user.getHabits().add(habit);
            System.out.println("Hábito agregado correctamente.");

        }else{

            System.out.println("Usuario no encontrado.");
        }
    }

    public String showHabits(int userId){

        User user = repository.findByCode(userId);

        if(user == null){

            return "Usuario no encontrado.";
        }

        if(user.getHabits().isEmpty()){

            return "No hay hábitos registrados.";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("\n===== HÁBITOS REGISTRADOS =====\n");

        for(Habit h : user.getHabits()){

            sb.append(h).append("\n");
        }

        return sb.toString();
    }
}
