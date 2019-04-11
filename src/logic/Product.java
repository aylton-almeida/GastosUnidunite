package logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product implements Comparable {
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

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getFactory() {
        return factory.get();
    }

    public void setFactory(String factory) {
        this.factory = new SimpleStringProperty(factory);
    }

    public String getSize() {
        return size.get();
    }

    public void setSize(String size) {
        this.size = new SimpleStringProperty(size);
    }

    public double getValue() {
        return value.get();
    }

    public void setValue(double value) {
        this.value = new SimpleDoubleProperty(value);
    }

    @Override
    public int compareTo(Object o) {
        return this.getId() - ((Product) o).getId();
    }
}
