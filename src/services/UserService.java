package services;

import dao.Users;
import exceptions.UserException;
import logic.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private Users users;

    public UserService(){
        this.users = new Users();
    }

    public User login(String email, String password) throws Exception {
        List<User> userList;
        userList = users.getAllObjects();
        return userList.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElseGet(()->{
                    User invalidUser = new User();
                    invalidUser.setId(-1);
                    return invalidUser;
                });
    }

    public void loggout(){

    }

    public void addUser (String email, String pass) throws Exception {
        users.addObject(new User(email, pass, false, 0));
    }

    public void removeUser(int id) throws Exception {
        users.deleteObject(users.getObject(id));
    }

    public void updateUser(User user) throws Exception {
        users.updateObject(user);
    }

    public boolean changePassword(String oldPass, String newPass){
        return true;
    }
}
