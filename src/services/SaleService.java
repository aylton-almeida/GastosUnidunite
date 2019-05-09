package services;

import dao.Sales;
import logic.Sale;

import java.util.List;

public abstract class SaleService {
    private static Sales sales;

    static {
        try {
            sales = new Sales();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addSale(double value, int clientId, int employeeId, int payType, List<Integer> list) throws Exception {
        sales.addObject(new Sale(value, clientId, employeeId, payType, list));
    }

    public static List<Sale> getAllSales() throws Exception {
        return sales.getAllObjects();
    }

    public static void deleteSale(Sale s) throws Exception {
        sales.deleteObject(s);
    }
}
