package entities;

import java.util.ArrayList;

public class User {

    private int id;
    private String name;
    private int age;
    private double weight;
    private double height;
    private String gender;

    private String mainGoal;
    private ArrayList<String> secondaryGoals;

    public User(int id, String name, int age, double weight, double height, String gender,
                String mainGoal, ArrayList<String> secondaryGoals) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.mainGoal = mainGoal;
        this.secondaryGoals = secondaryGoals;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMainGoal() {
        return mainGoal;
    }

    public void setMainGoal(String mainGoal) {
        this.mainGoal = mainGoal;
    }

    public ArrayList<String> getSecondaryGoals() {
        return secondaryGoals;
    }

    public void setSecondaryGoals(ArrayList<String> secondaryGoals) {
        this.secondaryGoals = secondaryGoals;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", height=" + height +
                ", gender='" + gender + '\'' +
                ", mainGoal='" + mainGoal + '\'' +
                ", secondaryGoals=" + secondaryGoals +
                '}';
    }
}
