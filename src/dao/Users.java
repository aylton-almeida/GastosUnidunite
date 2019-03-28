package dao;

import interfaces.Dao;
import logic.User;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Properties;

public class Users implements Dao<User> {
    private String host = "bancounidunite.mysql.database.azure.com";
    private String database = "unidunite";
    private String user = "AyltonJunior@bancounidunite";
    private String password = "Aylton123";
    private Connection connection = null;

    public Users() throws Exception {
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
    public List<User> getAllObjects() throws Exception {
        List<User> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * from tbl_user;");
        while (results.next()) {
            User user = new User(results.getString(2), results.getString(3), results.getBoolean(4));
            user.setId(results.getInt(1));
            list.add(user);
        }
        return list;
    }

    @Override
    public User getObject(Object key) throws Exception {

        return null;
    }

    @Override
    public void addObject(User o) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tbl_user (email, password, isAdmin) VALUES (?, ?, ?);");
        preparedStatement.setString(1, o.getEmail());
        preparedStatement.setString(2, o.getPassword());
        preparedStatement.setBoolean(3, o.isAdmin());
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateObject(User o) throws Exception {

    }

    @Override
    public void deleteObject(User o) throws Exception {

    }
}
