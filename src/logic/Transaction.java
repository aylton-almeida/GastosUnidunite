package logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Transaction implements Comparable<Transaction>{
    private double value;
    private String date;

    public Transaction(double value, LocalDate date){
        setValue(value);
        setDate(date);
    }

    public Transaction(double value, String date) {
        setValue(value);
        this.date = date;
    }

    public Transaction(double value){
        setValue(value);
        setDate(LocalDate.now());
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

    public Date getDateComparable() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.parse(this.getDate());
    }

    @Override
    public int compareTo(Transaction o){
        try {
            return this.getDateComparable().compareTo(o.getDateComparable());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
