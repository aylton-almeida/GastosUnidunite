package services;

import dao.Products;
import logic.Product;

import java.util.List;

public class ProductService {
    private Products products;

    public ProductService() {
        this.products = new Products();
    }

    public void addProduct(Product product) throws Exception {
        products.addObject(product);
    }

    public List<Product> getAllProducts() throws Exception {
        return products.getAllObjects();
    }
}
