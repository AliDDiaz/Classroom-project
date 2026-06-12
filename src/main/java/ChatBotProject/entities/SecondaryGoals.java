package ChatBotProject.entities;

public abstract class SecondaryGoals {

    protected String name;

    public SecondaryGoals(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return name;
    }



    public abstract String getRecommendation();
}
