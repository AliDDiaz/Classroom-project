package entities;

public class ImproveResistenceRoutine extends Routine{

    public ImproveResistenceRoutine(){
        super("Mejorar la resistencia");
    }


    @Override
    public String generateRoutine() {
        return """
               Rutina principal:
               - Running
               - Circuitos HIIT
               """;
    }
}
