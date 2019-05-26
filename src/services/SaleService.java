package services;

import dao.Sales;
import logic.Product;
import logic.Sale;

import java.util.List;

public abstract class SaleService {

    public static void addSale(double value, String clientId, String employeeId, int payType, List<Product> list) throws Exception {
        new Sales().addObject(new Sale(value, clientId, employeeId, payType, list));
    }

    public static List<Sale> getAllSales() throws Exception {
        return new Sales().getAllObjects();
    }

    public static void deleteSale(Sale s) throws Exception {
        new Sales().deleteObject(s);
    }
}
