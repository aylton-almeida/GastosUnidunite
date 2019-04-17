package dao;

import interfaces.Dao;
import logic.Sale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
//        List<Sale> list = new ArrayList<>();
//        Statement statement = connection.createStatement();
//        ResultSet results = statement.executeQuery("SELECT * from tbl_sale;");
//        while (results.next()) {
//            Sale sale = new Sale(results.getDouble());
//            list.add(sale);
//        }
//        return list;
        return null;
    }

    @Override
    public Sale getObject(Object key) throws Exception {
        return null;
    }

    @Override
    public void addObject(Sale o) throws Exception {

    }

    @Override
    public void updateObject(Sale o) throws Exception {

    }

    @Override
    public void deleteObject(Sale o) throws Exception {

    }
}
