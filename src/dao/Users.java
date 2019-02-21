package dao;

import core.User;
import interfaces.Dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Users implements Dao<User> {
    private String fileName;

    public Users() {
        setFileName("Users");
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<User> getAllObjects() throws Exception {
        List<User> users = new ArrayList<>();
        FileInputStream inputFile = new FileInputStream(getFileName());
        ObjectInputStream input = new ObjectInputStream(inputFile);
        do {
            users.add((User)input.readObject());
        }while(input.available() != 0);
        return users;
    }

    @Override
    public User getObject(Object key) throws Exception {
        FileInputStream inputFile = new FileInputStream(getFileName());
        ObjectInputStream input = new ObjectInputStream(inputFile);
        do {
            User user = (User) input.readObject();
            if (user.getCode() == (int) key) {
                input.close();
                inputFile.close();
                return user;
            }
        }while (input.available() != 0);
        return null;
    }

    @Override
    public void addObject(User o) throws Exception {
        FileOutputStream outputFile = new FileOutputStream(getFileName());
        ObjectOutputStream output = new ObjectOutputStream(outputFile);
        output.writeObject(o);
        output.flush();
        output.close();
        outputFile.close();
    }

    @Override
    public void updateObject(User o) throws Exception {

    }

    @Override
    public void deleteObject(User o) throws Exception {

    }


}
