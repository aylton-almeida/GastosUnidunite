package dao;

import core.Employee;
import interfaces.Dao;

import java.util.List;

public class Employees implements Dao<Employee> {
    @Override
    public List<Employee> getAllObjects() throws Exception {
        return null;
    }

    @Override
    public Employee getObject(Object key) throws Exception {
        return null;
    }

    @Override
    public void addObject(Employee o) throws Exception {

    }

    @Override
    public void updateObject(Employee o) throws Exception {

    }

    @Override
    public void deleteObject(Employee o) throws Exception {

    }
}
