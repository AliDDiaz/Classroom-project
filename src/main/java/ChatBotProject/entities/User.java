package ChatBotProject.entities;

import java.util.ArrayList;

public class User {

    private int id;
    private String name;
    private int age;
    private double weight;
    private double height;
    private String gender;

    private String mainGoal;
    private ArrayList<SecondaryGoals> secondaryGoals;
    private ArrayList<Double> weightHistory;
    private ArrayList<Habit> habits;
    private double targetWeight;

    public User(int id, String name, int age, double weight, double height, String gender,
                String mainGoal, ArrayList<SecondaryGoals> secondaryGoals, ArrayList<Double> weightHistory,
                ArrayList<Habit> habits, double targetWeight) {

        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.mainGoal = mainGoal;
        this.secondaryGoals = secondaryGoals;
        this.weightHistory = weightHistory;
        this.habits = habits;
        this.targetWeight = targetWeight;
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

    public ArrayList<SecondaryGoals> getSecondaryGoals() {
        return secondaryGoals;
    }

    public void setSecondaryGoals(ArrayList<SecondaryGoals> secondaryGoals) {
        this.secondaryGoals = secondaryGoals;
    }

    public ArrayList<Double> getWeightHistory() {
        return weightHistory;
    }

    public void setWeightHistory(ArrayList<Double> weightHistory) {
        this.weightHistory = weightHistory;
    }

    public ArrayList<Habit> getHabits() {
        return habits;
    }

    public void setHabits(ArrayList<Habit> habits) {
        this.habits = habits;
    }

    public double getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(double targetWeight) {
        this.targetWeight = targetWeight;
    }

    @Override
    public String toString() {
        return "ID=" + id +
                "\nNombre: " + name +
                "\nEdad: " + age +
                "\nPeso: " + weight +
                "\nAltura: " + height +
                "\nGénero: " + gender +
                "\nObjetivo principal: " + mainGoal +
                "\nObjetivos secundarios: " + secondaryGoals;
    }
}
