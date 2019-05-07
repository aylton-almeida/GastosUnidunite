package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sale extends Transaction{
    private int id;
    private List<Integer> productList;
    private int clientId;
    private int employeeId;
    private int payType; //[1] - Crédito [2] - Débito [3] - Cheque [4] - Crediário [5] - A vista

    public Sale(int id, double value, LocalDate date, int clientId, int employeeId, int payType){
        super(value, date);
        setId(id);
        setClientId(clientId);
        setEmployeeId(employeeId);
        setPayType(payType);
        this.productList = new ArrayList<Integer>();
    }

    public Sale(double value, LocalDate date, int clientId, int employeeId, int payType){
        super(value, date);
        setClientId(clientId);
        setEmployeeId(employeeId);
        setId(-1);
        setPayType(payType);
        this.productList = new ArrayList<Integer>();
    }

    public Sale(double value, LocalDate date, int clientId, int employeeId, int id, int payType){
        super(value, date);
        setClientId(clientId);
        setEmployeeId(employeeId);
        setId(id);
        setPayType(payType);
        this.productList = new ArrayList<Integer>();
    }

    public Sale(double value, LocalDate date, int clientId, int employeeId, int id, int payType, List<Integer> list){
        super(value, date);
        setClientId(clientId);
        setEmployeeId(employeeId);
        setId(id);
        setPayType(payType);
        this.productList = list;
    }

    public Sale(double value, LocalDate date, int clientId, int employeeId, int payType, List<Integer> list){
        super(value, date);
        setClientId(clientId);
        setEmployeeId(employeeId);
        setId(-1);
        setPayType(payType);
        this.productList = list;
    }

    public Sale(){
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

    public void addProductToList(Integer product) {
        this.productList.add(product);
    }

    public int getProductsQtt(){
        return this.productList.size();
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    @Override
    public String toString(){
        return this.getId() + "\n" + this.getClientId() +
                "\n" + this.getEmployeeId() +
                "\n" + this.getPayType() +
                "\n" + this.getValue() +
                "\n" + this.getDate();
    }
}
