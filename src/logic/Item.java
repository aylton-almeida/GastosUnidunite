package logic;

public class Item {
    private int qtt;
    private int productId;
    private double value;

    public Item(int qtt, int productId, double value) {
        setProductId(productId);
        setQtt(qtt);
        setValue(value);
    }

    public int getQtt() {
        return qtt;
    }

    public void setQtt(int qtd) {
        this.qtt = qtd;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
