package logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense extends Transaction {
    private int id;
    private String description;
    private boolean isPublic;


    public Expense(String description, double value, boolean isPublic, String date) {
        super(value, date);
        setDescription(description);
        setPublic(isPublic);
        setId(-1);
    }

    public Expense(){
        super(0, "");
        setDescription("");
        setPublic(false);
        setId(-1);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public Date dateComparable() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        Date date = format.parse(this.getDate());
        return date;
    }

}

