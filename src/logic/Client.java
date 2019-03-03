package logic;

import interfaces.Registrabel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Client implements Registrabel, Comparable {

    private SimpleStringProperty name;
    private SimpleStringProperty adress;
    private SimpleStringProperty email;
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty phone;

    public Client(String name, String adress, String email, int id, int phone) {
        setName(name);
        setAdress(adress);
        setEmail(email);
        setId(id);
        setPhone(phone);
    }

    public Client() {
        setName("");
        setAdress("");
        setEmail("");
        setId(-1);
        setPhone(0);

    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getAdress() {
        return adress.get();
    }

    public void setAdress(String adress) {
        this.adress = new SimpleStringProperty(adress);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public int getPhone() {
        return phone.get();
    }

    public void setPhone(int phone) {
        this.phone = new SimpleIntegerProperty(phone);
    }

    @Override
    public byte[] getByteArray() throws Exception {
        ByteArrayOutputStream record = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(record);
        output.writeUTF(getName());
        output.writeUTF(getAdress());
        output.writeUTF(getEmail());
        output.writeInt(getPhone());
        output.writeInt(getId());
        return record.toByteArray();
    }

    @Override
    public Registrabel setByteArray(byte[] b) throws Exception {
        ByteArrayInputStream record = new ByteArrayInputStream(b);
        DataInputStream input = new DataInputStream(record);
        setName(input.readUTF());
        setAdress(input.readUTF());
        setEmail(input.readUTF());
        setPhone(input.readInt());
        setId(input.readInt());
        return this;
    }

    @Override
    public int compareTo(Object o) {
        return this.getId() - ((Client) o).getId();
    }
}
