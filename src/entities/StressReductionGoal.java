package entities;

public class StressReductionGoal extends SecondaryGoals {

    public StressReductionGoal(){
        super("Reducir estrés");
    }


    @Override
    public String getRecommendation() {
        return "- Meditación 10 minutos";
    }
}
