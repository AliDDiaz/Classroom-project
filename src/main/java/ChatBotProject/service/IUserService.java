package ChatBotProject.service;

import ChatBotProject.entities.*;

import java.util.ArrayList;

public interface IUserService {
    boolean registerUser(User user);
    double calculateIMC(int id);
    String bmiRecommendation(int id);
    double calculateCalories(int id);
    String caloriesRecommendation(int id);
    String generateRoutine(int id);
    ArrayList<User> getAllUser();
    User findUser(int id);
    boolean deleteUser(int id);
    void updateWeight(double weight,int id);
    void assignMainGoal(int userId, String goal);
    void addSecondaryGoal(int userId, SecondaryGoals goal);
    void addHabit(int userId, Habit habit);
    String showHabits(int userId);
    String showWeightHistory(int id);
    String showProgress(int id);
}
