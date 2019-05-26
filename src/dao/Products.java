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
        String url = String.format("jdbc:mysql://%s/%s", this.host, this.database);
        Properties properties = new Properties();
        properties.setProperty("user", this.user);
        properties.setProperty("password", this.password);
        properties.setProperty("useSSL", "true");
        properties.setProperty("verifyServerCertificate", "true");
        properties.setProperty("requireSSL", "false");
        this.connection = DriverManager.getConnection(url, properties);
        if (this.connection == null) {
            throw new Exception("Failed to create connection to database.");
        }
    }

    public List<Product> getAllObjects() throws Exception {
        List<Product> list = new ArrayList();
        Statement statement = this.connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * from tbl_product;");

        while (results.next()) {
            Product product = new Product(results.getInt(1), results.getString(2), results.getString(5), results.getString(4), results.getDouble(3));
            list.add(product);
        }

        return list;
    }

    public Product getObject(Object key) throws Exception {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from tbl_product WHERE id = ?;");
        preparedStatement.setInt(1, (Integer) key);
        ResultSet results = preparedStatement.executeQuery();
        return results.next() ? new Product(results.getInt(1), results.getString(2), results.getString(5), results.getString(4), results.getDouble(3)) : null;
    }

    public void addObject(Product o) throws Exception {
        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO tbl_product (id, name, value, size, factory) VALUES (?, ?, ?, ?, ?);");
        preparedStatement.setInt(1, o.getId());
        preparedStatement.setString(2, o.getName());
        preparedStatement.setDouble(3, o.getValue());
        preparedStatement.setString(4, o.getSize());
        preparedStatement.setString(5, o.getFactory());
        preparedStatement.executeUpdate();
    }

    public void updateObject(Product o) throws Exception {
        PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE tbl_product SET name = ?, value = ?, size = ?, factory = ? WHERE id = ?;");
        preparedStatement.setString(1, o.getName());
        preparedStatement.setDouble(2, o.getValue());
        preparedStatement.setString(3, o.getSize());
        preparedStatement.setString(4, o.getFactory());
        preparedStatement.setInt(5, o.getId());
        preparedStatement.executeUpdate();
    }

    public void deleteObject(Product o) throws Exception {
        PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM tbl_product WHERE id = ?;");
        preparedStatement.setInt(1, o.getId());
        preparedStatement.executeUpdate();
    }
}

