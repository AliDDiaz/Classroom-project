package ChatBotProject.service;

import ChatBotProject.entities.*;
import ChatBotProject.repositories.UserRepository;

import java.util.ArrayList;

public class UserService implements IUserService{

    private UserRepository repository = new UserRepository();
    @Override
    public boolean registerUser(User user){

        if(user.getAge() <= 0 || user.getAge() > 120){
            throw new IllegalArgumentException("Edad inválida. Intente nuevamente.");

        }

        if(user.getWeight() <= 0){
            throw new IllegalArgumentException("Peso inválido. Intente nuevamente.");

        }

        if(user.getHeight() <= 0){
            throw new IllegalArgumentException("Altura inválida. Intente nuevamente.");

        }

        if(user.getName() == null || user.getName().isEmpty()){
            throw new IllegalArgumentException("Nombre inválido. Intente nuevamente.");

        }

        if(repository.findByCode(user.getId()) != null){
            throw new IllegalArgumentException("El usuario ya existe.");

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
            throw new IllegalArgumentException("Peso inválido");
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

            history += "Registro " + count + ": " + weight + " kg\n";

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

    public void setTargetWeight(int userId, double targetWeight){

        User user = repository.findByCode(userId);

        if(user == null){

            System.out.println("Usuario no encontrado.");
            return;
        }

        user.setTargetWeight(targetWeight);

        System.out.println(
                "Meta de peso configurada correctamente.");
    }

    public String showGoalProgress(int userId){

        User user = repository.findByCode(userId);

        if(user == null){

            return "Usuario no encontrado.";
        }

        if(user.getTargetWeight() <= 0){

            return "No has configurado una meta de peso.";
        }

        double currentWeight = user.getWeight();

        double targetWeight = user.getTargetWeight();

        String progress = "===== META DE PESO =====\n";

        progress += "Peso actual: " + currentWeight + " kg\n";
        progress += "Meta: " + targetWeight + " kg\n";

        if(currentWeight == targetWeight){

            progress += "¡Felicitaciones! Alcanzaste tu meta de peso.";

        }
        else if(currentWeight > targetWeight){

            progress += String.format(
                    "Te faltan %.2f kg para alcanzar tu meta.",
                    currentWeight - targetWeight);

        }
        else{

            progress += String.format(
                    "Has superado tu meta por %.2f kg. ¡Excelente trabajo!",
                    targetWeight - currentWeight);

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

    public int generateHabitId(int userId){

        User user = repository.findByCode(userId);

        if(user == null){
            return 1;
        }

        return user.getHabits().size() + 1;
    }

    public void completeHabit(int userId, int habitId){

        User user = repository.findByCode(userId);

        if(user == null){

            System.out.println("Usuario no encontrado.");
            return;
        }

        for(Habit h : user.getHabits()){

            if(h.getId() == habitId){

                h.setCompleted(true);

                System.out.println("Hábito completado correctamente.");
                return;
            }
        }

        System.out.println("No existe un hábito con ese ID.");
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

    public void deleteHabit(int userId, int habitId){

        User user = repository.findByCode(userId);

        if(user == null){

            System.out.println("Usuario no encontrado.");
            return;
        }

        boolean removed = user.getHabits().removeIf(h -> h.getId() == habitId);

        if(removed){

            System.out.println("Hábito eliminado correctamente.");

        }else{

            System.out.println("No existe un hábito con ese ID.");
        }
    }

    public String habitsStatistics(int userId){

        User user = repository.findByCode(userId);

        if(user == null){
            return "Usuario no encontrado.";
        }

        ArrayList<Habit> habits = user.getHabits();

        if(habits.isEmpty()){
            return "No hay hábitos registrados.";
        }

        int completed = 0;

        for(Habit h : habits){

            if(h.isCompleted()){
                completed++;
            }
        }

        int total = habits.size();

        int pending = total - completed;

        double percentage = (completed * 100.0) / total;

        String stats = "===== ESTADÍSTICAS DE HÁBITOS =====\n";
        stats += "Hábitos registrados: " + total + "\n";
        stats += "Completados: " + completed + "\n";
        stats += "Pendientes: " + pending + "\n";
        stats += String.format("Porcentaje de cumplimiento: %.2f%%", percentage);

        return stats;
    }
}
