package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;

import java.util.ArrayList;
import java.util.List;

public class Sale extends Transaction implements Comparable<Sale> {
    private SimpleIntegerProperty id;
    private List<Integer> productList;
    private SimpleIntegerProperty clientId;
    private SimpleIntegerProperty employeeId;
    private SimpleIntegerProperty payType; //[1] - Crédito [2] - Débito [3] - Cheque [4] - Crediário [5] - A vista

    public Sale(int id, double value, String date, int clientId, int employeeId, int payType) {
        super(value, date);
        setId(id);
        setClientId(clientId);
        setEmployeeId(employeeId);
        setPayType(payType);
        setProductList(new ArrayList<>());
    }

    public Sale(double value, int clientId, int employeeId, int payType, List<Integer> list) {
        super(value);
        setClientId(clientId);
        setEmployeeId(employeeId);
        setId(-1);
        setPayType(payType);
        setProductList(list);
    }

    public Sale() {
        super();
        setClientId(-1);
        setEmployeeId(-1);
        setId(-1);
        setPayType(-1);
        this.productList = null;
    }

    public List<Integer> getProductList() {
        return productList;
    }

    public void setProductList(List<Integer> list) {
        this.productList = list.isEmpty() ? new ArrayList<>() : list;
    }

    public void addProductToList(Integer product) {
        this.productList.add(product);
    }

    public int getProductsQtt() {
        return this.productList.size();
    }

    public int getClientId() {
        return clientId.get();
    }

    public void setClientId(int clientId) {
        this.clientId = new SimpleIntegerProperty(clientId);
    }

    public int getEmployeeId() {
        return employeeId.get();
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = new SimpleIntegerProperty(employeeId);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public int getPayType() {
        return payType.get();
    }

    public void setPayType(int payType) {
        this.payType = new SimpleIntegerProperty(payType);
    }

    @Override
    public String toString() {
        return this.getId() + "\n" + this.getClientId() +
                "\n" + this.getEmployeeId() +
                "\n" + this.getPayType() +
                "\n" + this.getValue() +
                "\n" + this.getDate();
    }

    @Override
    public int compareTo(Sale o) {
        return this.getId() - o.getId();
    }
}
