package logic;

import interfaces.Registrabel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class  Client implements Comparable {

    private SimpleStringProperty name;
    private SimpleStringProperty address;
    private SimpleStringProperty email;
    private SimpleIntegerProperty id;
    private SimpleStringProperty phone;

    public Client(String name, String address, String email, String phone, int id) {
        setName(name);
        setAddress(address);
        setEmail(email);
        setPhone(phone);
        setId(id);
    }

    public Client() {
        setName("");
        setAddress("");
        setEmail("");
        setId(-1);
        setPhone("");

    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address = new SimpleStringProperty(address);
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

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone = new SimpleStringProperty(phone);
    }

    @Override
    public int compareTo(Object o) {
        return this.getId() - ((Client) o).getId();
    }
}
