package entities;

public class HealthyHabitsGoal extends SecondaryGoals {

    public HealthyHabitsGoal(){
        super("Desarrollar hábitos saludables");
    }

    @Override
    public String getRecommendation() {
        return "- Mantener horarios constantes";
    }
}
