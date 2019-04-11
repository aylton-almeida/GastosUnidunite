package dao;

import interfaces.Dao;
import logic.Expense;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Expenses implements Dao<Expense> {

    private String fileName;

    public Expenses() {
        setFileName("Expenses");
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Expense> getAllObjects() throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "r");
        List<Expense> list = new ArrayList();
        file.readInt();
        int actualPoint = 4;
        while (actualPoint < file.length()) {
            int size = file.readInt();
            byte b[] = new byte[size];
            file.read(b);
            list.add((Expense) new Expense().setByteArray(b));
            actualPoint += 4 + size;
        }
        file.close();
        return list;
    }

    @Override
    public Expense getObject(Object key) throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "r");
        file.readInt();
        int actualPoint = 4;
        while (actualPoint < file.length()) {
            int size = file.readInt();
            byte b[] = new byte[size];
            file.read(b);
            Expense user = (Expense) new Expense().setByteArray(b);
            if (user.getId() == (int) key)
                return user;
            actualPoint += 4 + size;
        }
        file.close();
        return null;
    }

    @Override
    public void addObject(Expense o) throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "rw");
        int oldId = 0;
        if (file.length() > 0)
            oldId = file.readInt();
        o.setId(oldId + 1);
        file.seek(0);
        file.writeInt(o.getId());
        file.seek(file.length());
        file.writeInt(o.getByteArray().length);
        file.write(o.getByteArray());
        file.close();
    }

    @Override
    public void updateObject(Expense o) throws Exception {

    }

    @Override
    public void deleteObject(Expense o) throws Exception {

    }
}
