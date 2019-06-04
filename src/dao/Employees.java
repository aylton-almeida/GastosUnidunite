package dao;

import interfaces.Dao;
import logic.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Employees implements Dao<Employee> {
    private String host = "35.198.17.15";
    private String database = "SqlUnidunite";
    private String user = "appgerencial";
    private String password = "unidunitegerencial";
    private Connection connection;


    public Employees() throws Exception {
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
    public List<Employee> getAllObjects() throws Exception {
        List<Employee> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * from tbl_employee;");
        while (results.next()) {
            Employee employee = new Employee(results.getString(2), results.getString(3), results.getInt(1));
            employee.setId(results.getInt(1));
            list.add(employee);
        }
        return list;
    }

    @Override
    public Employee getObject(Object key) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from tbl_product WHERE id = ?;");
        preparedStatement.setInt(1, (int) key);
        ResultSet results = preparedStatement.executeQuery();
        if (results.next())
            return new Employee(results.getString(2), results.getString(3), results.getInt(1));
        return null;
    }

    public void addObject(Employee o) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tbl_employee (name, phone) VALUES (?, ?);");
        preparedStatement.setString(1, o.getName());
        preparedStatement.setString(2, o.getPhone());
        preparedStatement.executeUpdate();

    }

    @Override
    public void updateObject(Employee o) throws Exception {
        PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE tbl_employee SET name = ?, phone = ? WHERE id = ?;");
        preparedStatement.setString(1, o.getName());
        preparedStatement.setString(2, o.getPhone());
        preparedStatement.setInt(3, o.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteObject(Employee o) throws Exception {
        PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM tbl_employee WHERE id = ?;");
        preparedStatement.setInt(1, o.getId());
        preparedStatement.executeUpdate();
    }
}
