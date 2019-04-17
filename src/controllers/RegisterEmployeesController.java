package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import logic.Employee;
import services.EmployeeService;

import java.net.URL;
import java.util.ResourceBundle;


public class RegisterEmployeesController extends MainController implements Initializable {
    public JFXTextField nameInput;
    public JFXTextField phoneInput;

    public void RegisterEmployee(ActionEvent event) {
        if (nameInput.validate() && phoneInput.validate()) {
            register();
        } else showMsg("Preencha todos os campos corretamente");
    }


    private void register() {
        try {
            new EmployeeService().addEmployee(new Employee(nameInput.getText(), phoneInput.getText()));
            showMsg("Funcionario cadastrado com sucesso");
            clearMainArea();
            loadCenterUI("/fxml/Employees.fxml");
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addRequiredValidator(nameInput);
        addRequiredValidator(phoneInput);
    }
}

