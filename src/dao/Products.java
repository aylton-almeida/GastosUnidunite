package dao;

import interfaces.Dao;
import logic.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Products implements Dao<Product> {

    private String host = "bancounidunite.mysql.database.azure.com";
    private String database = "unidunite";
    private String user = "AyltonJunior@bancounidunite";
    private String password = "Aylton123";
    private Connection connection = null;

    public Products() throws Exception {
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
    public List<Product> getAllObjects() throws Exception {
        List<Product> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * from tbl_product;");
        while (results.next()) {
            Product product = new Product(results.getInt(1), results.getString(2), results.getString(5), results.getString(4), results.getDouble(3));
            list.add(product);
        }
        return list;
    }

    @Override
    public Product getObject(Object key) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from tbl_product WHERE id = ?;");
        preparedStatement.setInt(1, (int) key);
        ResultSet results = preparedStatement.executeQuery();
        if (results.next())
            return new Product(results.getInt(1), results.getString(2), results.getString(5), results.getString(4), results.getDouble(3));
        return null;
    }

    @Override
    public void addObject(Product o) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tbl_product (id, name, value, size, factory) VALUES (?, ?, ?, ?, ?);");
        preparedStatement.setInt(1, o.getId());
        preparedStatement.setString(2, o.getName());
        preparedStatement.setDouble(3, o.getValue());
        preparedStatement.setString(4, o.getSize());
        preparedStatement.setString(5, o.getFactory());
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateObject(Product o) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tbl_product (id, name, value, size, factory) VALUES (?, ?, ?, ?, ?);");
        preparedStatement.setInt(1, o.getId());
        preparedStatement.setString(2, o.getName());
        preparedStatement.setDouble(3, o.getValue());
        preparedStatement.setString(4, o.getSize());
        preparedStatement.setString(5, o.getFactory());
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteObject(Product o) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE tbl_product WHERE id = ?;");
        preparedStatement.setInt(1, o.getId());
        preparedStatement.executeUpdate();

    }
}
