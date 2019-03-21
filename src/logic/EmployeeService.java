package logic;

import dao.Employees;
import logic.Employee;

import java.util.List;

public class EmployeeService {
    private Employees employees;

    public EmployeeService(){
        this.employees = new Employees();
    }

    public List<Employee> getAllClients() throws Exception {
        return employees.getAllObjects();
    }

    public void addClient(Employee e) throws Exception{
        employees.addObject(e);
    }
}
