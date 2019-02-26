package core;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import interfaces.Registrabel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Product extends RecursiveTreeObject<Product> implements Registrabel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty factory;
    private SimpleStringProperty size;
    private SimpleDoubleProperty value;

    public Product(int id, String name, String factory, String size, double value) {
        setId(id);
        setName(name);
        setFactory(factory);
        setSize(size);
        setValue(value);
    }

    public Product(){
        setValue(0);
        setSize(null);
        setFactory(null);
        setName(null);
        setId(0);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty getID(){
        return this.id;
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty getNAME(){
        return this.name;
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getFactory() {
        return factory.get();
    }

    public SimpleStringProperty getFACTORY(){
        return this.factory;
    }

    public void setFactory(String factory) {
        this.factory = new SimpleStringProperty(factory);
    }

    public String getSize() {
        return size.get();
    }

    public SimpleStringProperty getSIZE(){
        return this.size;
    }

    public void setSize(String size) {
        this.size = new SimpleStringProperty(size);
    }

    public double getValue() {
        return value.get();
    }

    public SimpleDoubleProperty getVALUE(){
        return this.value;
    }

    public void setValue(double value) {
        this.value = new SimpleDoubleProperty(value);
    }

    @Override
    public byte[] getByteArray() throws Exception {
        ByteArrayOutputStream record = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(record);
        output.writeUTF(getName());
        output.writeUTF(getFactory());
        output.writeUTF(getSize());
        output.writeDouble(getValue());
        output.writeInt(getId());
        return record.toByteArray();
    }

    @Override
    public Registrabel setByteArray(byte[] b) throws Exception {
        ByteArrayInputStream record = new ByteArrayInputStream(b);
        DataInputStream input = new DataInputStream(record);
        setName(input.readUTF());
        setValue(input.readDouble());
        setFactory(input.readUTF());
        setId(input.readInt());
        setSize(input.readUTF());
        return this;
    }
}