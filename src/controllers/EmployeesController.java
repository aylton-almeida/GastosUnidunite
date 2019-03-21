package controllers;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Employee;
import services.EmployeeService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class EmployeesController extends MainController implements Initializable{

    public JFXTextField searchInput;
    public TableView mainTableView;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn phoneColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));


        List<Employee> list = null;
        try {
            list = new EmployeeService().getAllClients();
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }

        list.stream()
                .sorted(Employee::compareTo)
                .forEach(employee -> mainTableView.getItems().add(employee));
    }

    public void filterSearch(ActionEvent event) {

        mainTableView.getItems().clear();

        String input = searchInput.getText();

        List<Employee> list = null;
        try {
            list = new EmployeeService().getAllClients();
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }

        list.stream()
                .filter(employee -> employee.getId() == Integer.parseInt(input))
                .forEach(employee -> mainTableView.getItems().add(employee));
    }

    public void clearSearch(ActionEvent event) {

        searchInput.setText(null);
        mainTableView.getItems().clear();
        List<Employee> list = null;
        try {
            list = new EmployeeService().getAllClients();
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }

        list.stream()
                .sorted(Employee::compareTo)
                .forEach(employee -> mainTableView.getItems().add(employee));
    }
}
