package entities;

public class MuscleGainRoutine extends Routine {

    public MuscleGainRoutine(){
        super("Ganar músculo");
    }
    @Override
    public String generateRoutine() {
        return """
               Rutina para ganar músculo: 
               -Pesas
               -Flexiones
               -Sentadillas
               """;
    }
}
