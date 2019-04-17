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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class EmployeesController extends MainController implements Initializable {

    public JFXTextField searchInput;
    public TableView<Employee> mainTableView;
    public TableColumn<Employee, Integer> idColumn;
    public TableColumn<Employee, String> nameColumn;
    public TableColumn<Employee, String> phoneColumn;
    private List<Employee> listEmployee = new ArrayList<>();
    private static EmployeeService employeeService;

    private void setOnTable() {
        this.listEmployee.stream()
                .sorted(Employee::compareTo)
                .forEach(employee -> mainTableView.getItems().add(employee));
    }

    public void goToRegisterEmployees(ActionEvent event) {
        clearMainArea();
        loadCenterUI("/fxml/RegisterEmployees.fxml");
    }

    public void filterSearch(ActionEvent event) {

        mainTableView.getItems().clear();

        String input = searchInput.getText();

        listEmployee.forEach((e) -> {
            if (("" + e.getId()).equals(input) || e.getName().toLowerCase().contains(input.toLowerCase()))
                mainTableView.getItems().add(e);
        });
    }

    public void clearSearch(ActionEvent event) {
        searchInput.setText(null);
        mainTableView.getItems().clear();

        setOnTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        try {
            employeeService = new EmployeeService();
            listEmployee = employeeService.getAllEmployee();
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }

        setOnTable();
    }
}
