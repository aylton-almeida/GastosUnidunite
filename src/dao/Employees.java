package dao;

import interfaces.Dao;
import logic.Employee;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Employees implements Dao<Employee> {

    private String fileName;

    public Employees() {
        setFileName("employees");
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Employee> getAllObjects() throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "r");
        List<Employee> employeeList = new ArrayList();
        file.readInt();
        int actualPoint = 4;
        while (actualPoint < file.length()) {
            int size = file.readInt();
            byte[] b = new byte[size];
            file.read(b);
            employeeList.add((Employee) new Employee().setByteArray(b));
            actualPoint += 4 + size;
        }
        file.close();
        return employeeList;
    }

    @Override
    public Employee getObject(Object key) throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "r");
        file.readInt();
        int actualPoint = 4;
        while (actualPoint < file.length()) {
            int size = file.readInt();
            byte[] b = new byte[size];
            file.read(b);
            Employee employee = (Employee) new Employee().setByteArray(b);
            if (employee.getId() == (int) key)
                return employee;
            actualPoint += 4 + size;
        }
        file.close();
        return null;
    }

    public void addObject(Employee o) throws Exception {
        RandomAccessFile file = new RandomAccessFile(getFileName(), "rw");
        file.seek(0);
        file.writeInt(o.getId());
        file.seek(file.length());
        file.writeInt(o.getByteArray().length);
        file.write(o.getByteArray());
        file.close();
    }

    @Override
    public void updateObject(Employee o) throws Exception {

    }

    @Override
    public void deleteObject(Employee o) throws Exception {

    }
}
