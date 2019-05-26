package logic;

import java.text.ParseException;

public class Expense extends Transaction implements Comparable<Expense>{
    private int id;
    private String description;
    private boolean isPublic;


    public Expense(String description, double value, boolean isPublic, String date) {
        super(value, date);
        setDescription(description);
        setPublic(isPublic);
        setId(-1);
    }

    public Expense(String description, double value, boolean isPublic, String date, int id) {
        super(value, date);
        setDescription(description);
        setPublic(isPublic);
        setId(id);
    }

    public Expense(String description, double value, boolean isPublic) {
        super(value);
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

    @Override
    public int compareTo(Expense o){
        try {
            return this.getDateComparable().compareTo(o.getDateComparable());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

