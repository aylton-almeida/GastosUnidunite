package logic;

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

    public int compareTo(Object o){
        return this.getId() - ((Expense) o).getId();
    }



}

