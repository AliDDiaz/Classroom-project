package ChatBotProject.entities;

public abstract class Routine {

    protected String name;

    public Routine(String name){
        this.name = name;
    }

    public abstract String generateRoutine();
}
