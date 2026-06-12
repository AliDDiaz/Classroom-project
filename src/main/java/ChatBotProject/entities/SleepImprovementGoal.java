package ChatBotProject.entities;

public class SleepImprovementGoal extends SecondaryGoals {

    public SleepImprovementGoal(){
        super("Mejorar sueño");
    }

    @Override
    public String getRecommendation() {
        return "- Dormir mínimo 8 horas";
    }
}
