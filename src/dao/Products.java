package dao;

import logic.Product;
import interfaces.Dao;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Products implements Dao<Product> {

    private String fileName;

    public Products(){
        setFileName("products");
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Product> getAllObjects() throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "r");
        List<Product> productList = new ArrayList();
        int actualPoint = 0;
        while (actualPoint < file.length()) {
            int size = file.readInt();
            byte b[] = new byte[size];
            file.read(b);
            productList.add((Product) new Product().setByteArray(b));
            actualPoint += 4 + size;
        }
        file.close();
        return productList;
    }

    @Override
    public Product getObject(Object key) throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "r");
        int actualPoint = 0;
        while (actualPoint < file.length()) {
            int size = file.readInt();
            byte b[] = new byte[size];
            file.read(b);
            Product product = (Product) new Product().setByteArray(b);
            if (product.getId() == (int) key)
                return product;
            actualPoint += 4 + size;
        }
        file.close();
        return null;
    }

    @Override
    public void addObject(Product o) throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "rw");
        file.seek(file.length());
        file.writeInt(o.getByteArray().length);
        file.write(o.getByteArray());
        file.close();
    }

    @Override
    public void updateObject(Product o) throws Exception {

    }

    @Override
    public void deleteObject(Product o) throws Exception {

    }
}
