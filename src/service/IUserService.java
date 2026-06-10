package service;

import entities.*;

import java.util.ArrayList;

public interface IUserService {
    public boolean registerUser(User user);
    public double calculateIMC(int id);
    public String bmiRecommendation(int id);
    public double calculateCalories(int id);
    public String caloriesRecommendation(int id);
    public String generateRoutine(int id);
    public ArrayList<User> getAllUser();
    public User findUser(int id);
    public boolean deleteUser(int id);
    public void updateWeight(double weight,int id);
    public void assignMainGoal(int userId, String goal);
    public void addSecondaryGoal(int userId, SecondaryGoals goal);
}
