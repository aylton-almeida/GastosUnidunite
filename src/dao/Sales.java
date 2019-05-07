package dao;

import interfaces.Dao;
import logic.Product;
import logic.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Sales implements Dao<Sale> {
    private String host = "bancounidunite.mysql.database.azure.com";
    private String database = "unidunite";
    private String user = "AyltonJunior@bancounidunite";
    private String password = "Aylton123";
    private Connection connection = null;

    public Sales() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = String.format("jdbc:mysql://%s/%s", host, database);

        //Set connection properties
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        properties.setProperty("useSSL", "true");
        properties.setProperty("verifyServerCertificate", "true");
        properties.setProperty("requireSSL", "false");

        connection = DriverManager.getConnection(url, properties);

        if (connection == null)
            throw new Exception("Failed to create connection to database.");
    }

    @Override
    public List<Sale> getAllObjects() throws Exception {
        List<Sale> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * from tbl_sale_item;");
        while (results.next()) {
            Sale sale = this.getObject(results.getInt(2));
            list.add(sale);
        }
        return list;
    }

    @Override
    public Sale getObject(Object key) throws Exception {
        //Recuperar venda sem items
        PreparedStatement ps = connection.prepareStatement("SELECT * from tbl_sale WHERE id = ?");
        ps.setInt(1, (int) key);
        ResultSet saleResult = ps.executeQuery();
        Sale sale = null;
        if (saleResult.next()) {
            sale = new Sale((int) key, saleResult.getDouble(5), saleResult.getDate(6).toLocalDate(), saleResult.getInt(3), saleResult.getInt(2), saleResult.getInt(4));
        }
        if (sale != null) {
            //Pegar items da venda
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from tbl_sale_item WHERE id_sale = ?;");
            preparedStatement.setInt(1, (int) key);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                sale.addProductToList(results.getInt(3));
            }
            return sale;
        }
        return null;
    }

    @Override
    public void addObject(Sale o) throws Exception {
//        PreparedStatement ps = connection.prepareStatement("SELECT id FROM tbl_sale ORDER BY id DESC LIMIT 1;");
//        ResultSet result = ps.executeQuery();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tbl_sale(employee_id, client_id, pay_type, value, date) VALUES(?, ? ,? ,?, ?);", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, o.getEmployeeId());
        preparedStatement.setInt(2, o.getClientId());
        preparedStatement.setInt(3, o.getPayType());
        preparedStatement.setDouble(4, o.getValue());
        preparedStatement.setString(5, o.getDate());
        preparedStatement.executeUpdate();
        ResultSet result = preparedStatement.getGeneratedKeys();
        int newSaleId = 0;
        if (result.next()){
            newSaleId = result.getInt(1);
        }
        for (int i = 0; i < o.getProductsQtt(); i++){
            PreparedStatement ps2 = connection.prepareStatement("INSERT INTO tbl_sale_item (id_sale, id_product) VALUES (?, ?)");
            ps2.setInt(1, newSaleId);
            ps2.setInt(2, o.getProductList().get(i));
            ps2.executeUpdate();
        }

    }

    @Override
    public void updateObject(Sale o) throws Exception {

    }

    @Override
    public void deleteObject(Sale o) throws Exception {

    }
}
