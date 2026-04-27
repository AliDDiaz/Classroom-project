package repositories;

import entities.User;

import java.util.ArrayList;

public class UserRepository {

    private ArrayList<User> list = new ArrayList<>();

    public void create(User user){

        list.add(user);

    }

    public ArrayList<User> getAll() {

        return list;

    }

    public void getAllUsers(){

        list.forEach(System.out::println);

    }

    public User findByCode(int code){

        for(User u: list){

            if(u.getId() == code){
                return u;
            }

        }

        return null;

    }

    public boolean deleteByCode(int code){

    return list.removeIf(s -> s.getId() == code);

    }

}
