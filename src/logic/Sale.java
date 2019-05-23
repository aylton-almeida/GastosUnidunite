package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Sale extends Transaction implements Comparable<Sale> {
    private SimpleIntegerProperty id;
    private List<Product> productList;
    private SimpleStringProperty clientName;
    private SimpleStringProperty employeeName;
    private SimpleIntegerProperty payType; //[1] - Crédito [2] - Débito [3] - Cheque [4] - Crediário [5] - A vista

    public Sale(int id, double value, String date, String clientName, String employeeName, int payType) {
        super(value, date);
        setId(id);
        setClientName(clientName);
        setEmployeeName(employeeName);
        setPayType(payType);
        setProductList(new ArrayList<>());
    }

    public Sale(double value, String clientName, String employeeName, int payType, List<Product> list) {
        super(value);
        setClientName(clientName);
        setEmployeeName(employeeName);
        setId(-1);
        setPayType(payType);
        setProductList(list);
    }

    public Sale() {
        super();
        setClientName("");
        setEmployeeName("");
        setId(-1);
        setPayType(-1);
        this.productList = null;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> list) {
        this.productList = list.isEmpty() ? new ArrayList<Product>() : list;
    }

    public void addProductToList(Product product) {
        this.productList.add(product);
    }

    public int getProductsQtt() {
        return this.productList.size();
    }

    public String getProductString() {
        StringBuilder str = new StringBuilder();
        for (Product p : this.productList){
            str.append(p.getName()).append("\n");
        }
        return str.toString();
    }

    public String getClientName() {
        return clientName.get();
    }

    public void setClientName(String clientName) {
        this.clientName = new SimpleStringProperty(clientName);
    }

    public String getEmployeeName() {
        return employeeName.get();
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = new SimpleStringProperty(employeeName);
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

    public String getPayTypeString(){
        switch (this.getPayType()) {
            case 1:
                return "Cartão de Crédito";
            case 2:
                return "Cartão de Débito";
            case 3:
                return "Cheque";
            case 4:
                return "Crediário";
            default:
                return "Á vista";
        }
    }

    @Override
    public String toString() {
        return this.getId() + "\n" + this.getClientName() +
                "\n" + this.getEmployeeName() +
                "\n" + this.getPayType() +
                "\n" + this.getValue() +
                "\n" + this.getDate();
    }

    @Override
    public int compareTo(Sale o) {
        return this.getId() - o.getId();
    }
}
