package services;

import dao.Products;
import logic.Product;

import java.util.List;

public class ProductService {
    private static Products products;

    public ProductService() throws Exception {
        products = new Products();
    }

    public void addProduct(Product product) throws Exception {
        products.addObject(product);
    }

    public List<Product> getAllProducts() throws Exception {
        return products.getAllObjects();
    }

    public void deleteProduct(Product product) throws Exception{
        products.deleteObject(product);
    }

    public void updateProduct(Product product) throws Exception{
        products.updateObject(product);
    }
}
