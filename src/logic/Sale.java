package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sale extends Transaction{
    private int id;
    private List<Item> itemList;
    private int clientId;
    private int sellerId;
    private int payType; //[1] - Crédito [2] - Débito [3] - Cheque [4] - Crediário [5] - A vista

    public Sale(double value, LocalDate date, int clientId, int sellerId, int payType){
        super(value, date);
        setClientId(clientId);
        setSellerId(sellerId);
        setId(-1);
        setPayType(payType);
        this.itemList = new ArrayList<>();
    }

    public Sale(double value, LocalDate date, int clientId, int sellerId, int id, int payType){
        super(value, date);
        setClientId(clientId);
        setSellerId(sellerId);
        setId(id);
        setPayType(payType);
        this.itemList = new ArrayList<>();
    }

    public Sale(double value, LocalDate date, int clientId, int sellerId, int id, int payType, List<Item> list){
        super(value, date);
        setClientId(clientId);
        setSellerId(sellerId);
        setId(id);
        setPayType(payType);
        this.itemList = list;
    }

    public Sale(){
        super();
        setClientId(-1);
        setSellerId(-1);
        setId(-1);
        setPayType(-1);
        this.itemList = null;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void addItemList(Item item) {
        this.itemList.add(item);
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
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
}
