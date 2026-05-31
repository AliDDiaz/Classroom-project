package entities;

public class IncreaseFlexibilityRoutine extends Routine{

    public IncreaseFlexibilityRoutine(){
        super("Aumentar la flexibilidad");
    }

    @Override
    public String generateRoutine() {
        return """
               Rutina principal:
               - Yoga
               - Pilates
               - Estiramientos diarios
               """;
    }
}
