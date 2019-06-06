package dao;

import interfaces.Dao;
import logic.Product;
import logic.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Sales implements Dao<Sale> {
    private String host = "35.198.17.15";
    private String database = "SqlUnidunite";
    private String user = "appgerencial";
    private String password = "unidunitegerencial";
    private Connection connection;

    public Sales() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = String.format("jdbc:mysql://%s/%s", host, database);

        //Set connection properties
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        properties.setProperty("socketFacotry", "com.google.cloud.sql.mysql.SocketFactory");
        properties.setProperty("useSSL", "false");

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
            if (!list.stream().anyMatch(s -> s.getId() == sale.getId()))
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
            PreparedStatement ps2 = connection.prepareStatement("SELECT NAME FROM tbl_client WHERE id = ?;");
            ps2.setInt(1, saleResult.getInt(3));
            ResultSet clientResult = ps2.executeQuery();
            if (clientResult.next()) {
                String clientName = clientResult.getString(1);
                PreparedStatement ps3 = connection.prepareStatement("SELECT NAME FROM tbl_employee WHERE id = ?;");
                ps3.setInt(1, saleResult.getInt(2));
                ResultSet employeeResult = ps3.executeQuery();
                if (employeeResult.next()) {
                    String employeeName = employeeResult.getString(1);
                    sale = new Sale((int) key, saleResult.getDouble(5), saleResult.getString(6), clientName, employeeName, saleResult.getInt(4));
                }
            }
        }
        if (sale != null) {
            //Pegar items da venda
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from tbl_sale_item WHERE id_sale = ?;");
            preparedStatement.setInt(1, (int) key);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                PreparedStatement ps4 = connection.prepareStatement("SELECT * FROM tbl_product WHERE id = ?");
                ps4.setInt(1, results.getInt(3));
                ResultSet productResult = ps4.executeQuery();
                if (productResult.next())
                    sale.addProductToList(new Product(productResult.getInt(1), productResult.getString(2), productResult.getString(5), productResult.getString(4), productResult.getDouble(3)));
            }
            return sale;
        }
        return null;
    }

    @Override
    public void addObject(Sale o) throws Exception {
        PreparedStatement ps = connection.prepareStatement("SELECT id FROM tbl_employee WHERE name = ?;");
        ps.setString(1, o.getEmployeeName());
        ResultSet employeeResult = ps.executeQuery();
        if (employeeResult.next()) {
            int employeeId = employeeResult.getInt(1);
            PreparedStatement ps2 = connection.prepareStatement("SELECT id FROM tbl_client where name = ?");
            ps2.setString(1, o.getClientName());
            ResultSet clientResult = ps2.executeQuery();
            if (clientResult.next()) {
                int clientId = clientResult.getInt(1);
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tbl_sale(employee_id, client_id, pay_type, value, date) VALUES(?, ? ,? ,?, ?);", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, employeeId);
                preparedStatement.setInt(2, clientId);
                preparedStatement.setInt(3, o.getPayType());
                preparedStatement.setDouble(4, o.getValue());
                preparedStatement.setString(5, o.getDate());
                preparedStatement.executeUpdate();
                ResultSet result = preparedStatement.getGeneratedKeys();
                int newSaleId = 0;
                if (result.next()) {
                    newSaleId = result.getInt(1);
                }
                for (int i = 0; i < o.getProductsQtt(); i++) {
                    PreparedStatement ps3 = connection.prepareStatement("INSERT INTO tbl_sale_item (id_sale, id_product) VALUES (?, ?)");
                    ps3.setInt(1, newSaleId);
                    ps3.setInt(2, o.getProductList().get(i).getId());
                    ps3.executeUpdate();
                }
            }
        }
    }

    @Override
    public void updateObject(Sale o) throws Exception {

    }

    @Override
    public void deleteObject(Sale o) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tbl_sale_item WHERE id_sale = ?;");
        preparedStatement.setInt(1, o.getId());
        preparedStatement.executeUpdate();
        PreparedStatement ps2 = connection.prepareStatement("DELETE FROM tbl_sale WHERE id = ?;");
        ps2.setInt(1, o.getId());
        ps2.executeUpdate();
    }
}
