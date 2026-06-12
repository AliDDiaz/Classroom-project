package ChatBotProject.entities;

public class WeightLossRoutine extends Routine {

    public WeightLossRoutine(){
        super("Perder peso");
    }

    @Override
    public String generateRoutine() {
        return """
               Rutina para perder peso:
               - Cardio 30 minutos
               - Caminata rápida
               - Bicicleta
               """;
    }
}
