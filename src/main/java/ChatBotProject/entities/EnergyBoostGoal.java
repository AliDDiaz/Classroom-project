package ChatBotProject.entities;

public class EnergyBoostGoal extends SecondaryGoals{

    public EnergyBoostGoal(){
        super("Aumentar energía");
    }

    @Override
    public String getRecommendation() {
        return "- Mantener buena hidratación";
    }
}
