package logic;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private double value;
    private String date;

    public Transaction(double value, LocalDate date){
        setValue(value);
        setDate(date);
    }

    public Transaction(){
        setValue(0.0);
        setDate(LocalDate.now());
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

    public void setDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        this.date = formatter.format(date);
    }
}
