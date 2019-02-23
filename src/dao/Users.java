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
        RandomAccessFile file = new RandomAccessFile(getFileName(), "r");
        List<User> userList = new ArrayList();
        int actualPoint = 0;
        while (actualPoint < file.length()) {
            int size = file.readInt();
            byte b[] = new byte[size];
            file.read(b);
            userList.add((User) new User().setByteArray(b));
            actualPoint += 4 + size;
        }
        file.close();
        return userList;
    }

    @Override
    public User getObject(Object key) throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "r");
        int actualPoint = 0;
        while (actualPoint < file.length()) {
            int size = file.readInt();
            byte b[] = new byte[size];
            file.read(b);
            User user = (User) new User().setByteArray(b);
            if (user.getId() == (int) key)
                return user;
            actualPoint += 4 + size;
        }
        file.close();
        return null;
    }

    @Override
    public void addObject(User o) throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "rw");
        file.seek(file.length());
        file.writeInt(o.getByteArray().length);
        file.write(o.getByteArray());
        file.close();
    }

    @Override
    public void updateObject(User o) throws Exception {

    }

    @Override
    public void deleteObject(User o) throws Exception {

    }


}
