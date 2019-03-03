package dao;

import interfaces.Dao;
import logic.Client;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Clients implements Dao<Client> {
    private String fileName;

    public Clients() {
        setFileName("clients");
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List getAllObjects() throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "r");
        List<Client> clientList = new ArrayList();
        int actualPoint = 0;
        while (actualPoint < file.length()) {
            int size = file.readInt();
            byte b[] = new byte[size];
            file.read(b);
            clientList.add((Client) new Client().setByteArray(b));
            actualPoint += 4 + size;
        }
        file.close();
        return clientList;
    }

    @Override
    public Object getObject(Object key) throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "r");
        int actualPoint = 0;
        while (actualPoint < file.length()) {
            int size = file.readInt();
            byte b[] = new byte[size];
            file.read(b);
            Client client = (Client) new Client().setByteArray(b);
            if (client.getId() == (int) key)
                return client;
            actualPoint += 4 + size;
        }
        file.close();
        return null;
    }

    @Override
    public void addObject(Client o) throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "rw");
        file.seek(file.length());
        file.writeInt(o.getByteArray().length);
        file.write(o.getByteArray());
        file.close();
    }

    @Override
    public void updateObject(Client o) throws Exception {

    }

    @Override
    public void deleteObject(Client o) throws Exception {

    }
}
