package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        mainTableView.getItems().add(null);

        showLoader();
        Task retriveClientsTask = new Task() {

            @Override
            protected List<Employee> call() throws Exception {
                employeeService = new EmployeeService();
                return employeeService.getAllEmployee();
            }

            @Override
            protected void succeeded() {
                listEmployee = (List<Employee>) getValue();
                hideLoader();
                setOnTable();
            }
        };
        Thread t = new Thread(retriveClientsTask);
        t.setDaemon(true);
        t.start();

        //Definir que quando o enter for pressionado o filtro ocorra
        searchInput.setOnAction(this::filterSearch);
        //Definir mudanca do filtro a medida que os dados sao digitados
        searchInput.onKeyReleasedProperty().set(e -> this.filterSearch(new ActionEvent()));
    }

    private void setOnTable() {
        mainTableView.getItems().clear();
        this.listEmployee.stream()
                .sorted(Employee::compareTo)
                .forEach(employee -> mainTableView.getItems().add(employee));
    }

    public void filterSearch(ActionEvent event) {

        mainTableView.getItems().clear();

        String input = searchInput.getText();

        listEmployee.forEach((e) -> {
            if (("" + e.getId()).startsWith(input) || e.getName().toLowerCase().startsWith(input.toLowerCase()))
                mainTableView.getItems().add(e);
        });
    }

    public void goToRegisterEmployees(ActionEvent event) {
        clearMainArea();
        loadCenterUI("/fxml/RegisterEmployees.fxml");
    }

    public void editEmployee(ActionEvent actionEvent) {
        if (mainTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                actualEmployee = mainTableView.getSelectionModel().getSelectedItem();
                clearMainArea();
                loadCenterUI("/fxml/RegisterEmployees.fxml");
            } catch (Exception e) {
                showMsg("Ocorreu um erro" + e.getMessage());
                e.printStackTrace();
            }
        } else showMsg("Selecione alguma linha para continuar");
    }

    public void deleteEmployee(ActionEvent actionEvent) {
        if (mainTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                Employee e = mainTableView.getSelectionModel().getSelectedItem();
                mainTableView.getItems().removeAll(e);
                employeeService.deleteEmployee(e);
                listEmployee.remove(e);
            } catch (Exception e) {
                showMsg("Ocorreu um erro" + e.getMessage());
                e.printStackTrace();
            }
        } else showMsg("Selecione alguma linha para continuar");
    }
}
