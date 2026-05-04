package service;

import entities.User;
import repositories.UserRepository;

import java.util.ArrayList;

public class UserService {

    private UserRepository repository = new UserRepository();

    public boolean registerUser(User user){

        if(user.getAge() <= 0){
            return false;
        }

        if(user.getName() == null || user.getName().isEmpty()){
            return false;
        }

        if(repository.findByCode(user.getId()) != null){
            return false;
        }

        repository.create(user);
        return true;
    }

    public ArrayList<User> getAllUser(){

        return repository.getAll();

    }

    public User findUser(int id){

        return repository.findByCode(id);

    }

    public boolean deleteUser(int id){

        return repository.deleteByCode(id);

    }
    public void updateWeight(double weight,int id){
        if(weight==0){
            System.out.println("Peso Imposible");
        }
        else {
            if(repository.weightUpdater(weight,id)){
                System.out.println("Actualizado correctamente");
            }
            else {
                System.out.println("No fue posible actualizar el peso");
            }
        }
    }
    public void assignMainGoal(int userId, String goal){
        User user = repository.findByCode(userId);

        if (user != null){
            user.setMainGoal(goal);
        }
    }

    public void addSecondaryGoal(int userId, String goal){
        User user = repository.findByCode(userId);

        if (user != null){
            user.getSecondaryGoals().add(goal);
        }
    }

}
