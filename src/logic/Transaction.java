package logic;

public class Transaction {
    private double value;
    private String date;

    public Transaction(double value, String date){
        setValue(value);
        setDate(date);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
