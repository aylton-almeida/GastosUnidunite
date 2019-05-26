package dao;

import interfaces.Dao;
import logic.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Expenses implements Dao<Expense> {

    private String host = "bancounidunite.mysql.database.azure.com";
    private String database = "unidunite";
    private String user = "AyltonJunior@bancounidunite";
    private String password = "Aylton123";
    private Connection connection = null;


    public Expenses() throws Exception {
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
    public List<Expense> getAllObjects() throws Exception {
        List<Expense> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT  * from tbl_expense;");
        while (results.next()) {
            Expense expense = new Expense(results.getString(2), results.getDouble(4), results.getBoolean(3),
                    results.getString(5), results.getInt(1));
            list.add(expense);
        }
        return list;
    }

    @Override
    public Expense getObject(Object key) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from tbl_expense WHERE id = ?;");
        preparedStatement.setInt(1, (int) key);
        ResultSet results = preparedStatement.executeQuery();
        if (results.next())
            return new Expense(results.getString(2), results.getDouble(4), results.getBoolean(3),
                    results.getString(5), results.getInt(1));
        return null;
    }


    @Override
    public void addObject(Expense o) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tbl_expense(description, priority, value, date) VALUES(?,?,?,?); ");
        preparedStatement.setString(1, o.getDescription());
        preparedStatement.setBoolean(2, o.isPublic());
        preparedStatement.setDouble(3, o.getValue());
        preparedStatement.setString(4, o.getDate());
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateObject(Expense o) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tbl_expense SET description = ?, value = ?, priority = ?, date = ? WHERE id = ?; ");
        preparedStatement.setString(1, o.getDescription());
        preparedStatement.setDouble(2, o.getValue());
        preparedStatement.setBoolean(3, o.isPublic());
        preparedStatement.setString(4, o.getDate());
        preparedStatement.setInt(5, o.getId());
        preparedStatement.executeUpdate();


    }

    @Override
    public void deleteObject(Expense o) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tbl_expense WHERE id = ?;");
        preparedStatement.setInt(1, o.getId());
        preparedStatement.executeUpdate();

    }
}
