package logic;

import interfaces.Registrabel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Employee implements Registrabel, Comparable {

    private SimpleStringProperty name;
    private SimpleIntegerProperty id;
    private SimpleStringProperty phone;

    public Employee(String name, String phone, int id) {
        setName(name);
        setPhone(phone);
        setId(id);
    }

    public Employee() {
        setName("");
        setId(-1);
        setPhone("");

    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone = new SimpleStringProperty(phone);
    }

    @Override
    public byte[] getByteArray() throws Exception {
        ByteArrayOutputStream record = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(record);
        output.writeUTF(getName());
        output.writeInt(getId());
        return record.toByteArray();
    }

    @Override
    public Registrabel setByteArray(byte[] b) throws Exception {
        ByteArrayInputStream record = new ByteArrayInputStream(b);
        DataInputStream input = new DataInputStream(record);
        setName(input.readUTF());
        setPhone(input.readUTF());
        setId(input.readInt());
        return this;
    }

    @Override
    public int compareTo(Object o) {
        return this.getId() - ((Client) o).getId();
    }
}
