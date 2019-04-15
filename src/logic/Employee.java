package logic;

import interfaces.Registrabel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Employee implements Comparable {

    private SimpleStringProperty name;
    private SimpleIntegerProperty id;
    private SimpleStringProperty phone;

    public Employee(String name, String phone) {
        setName(name);
        setPhone(phone);
        setId(-1);

    }

    public Employee(String name, String phone, int id){
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
    public int compareTo(Object o) {
        return this.getId() - ((Employee) o).getId();
    }
}
