package ChatBotProject.entities;

public class StayingFitRoutine extends Routine{

    public StayingFitRoutine(){
        super("Mantenerse en forma");
    }

    @Override
    public String generateRoutine() {
        return """
               Rutina principal:
               - Trote suave
               - Estiramientos
               """;
    }
}
