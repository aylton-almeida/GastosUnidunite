package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import logic.Employee;
import services.EmployeeService;

import java.net.URL;
import java.util.ResourceBundle;


public class RegisterEmployeesController extends MainController implements Initializable {
    public JFXTextField nameInput;
    public JFXTextField phoneInput;
    public JFXButton doneButton;
    public Label titleLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addRequiredValidator(nameInput);
        addRequiredValidator(phoneInput);

        if (actualEmployee != null) {
            nameInput.setText(actualEmployee.getName());
            phoneInput.setText(actualEmployee.getPhone());

            doneButton.onActionProperty().set(ignored -> {
                if (nameInput.validate() && phoneInput.validate()) {
                    try {
                        new EmployeeService().updateEmployee(new Employee(nameInput.getText(), phoneInput.getText(), actualEmployee.getId()));
                        showMsg("Funcionario atualizado com sucesso");
                        clearMainArea();
                        loadCenterUI("/fxml/Employees.fxml");
                        actualEmployee = null;
                    } catch (Exception e) {
                        showMsg(e.getMessage());
                        e.printStackTrace();
                    }
                } else showMsg("Preencha todos os campos corretamente");
            });
            titleLabel.setText("Editar Funcionário");
        } else {
            doneButton.onActionProperty().set(ignored -> {
                if (nameInput.validate() && phoneInput.validate()) {
                    try {
                        new EmployeeService().addEmployee(new Employee(nameInput.getText(), phoneInput.getText()));
                        showMsg("Funcionario cadastrado com sucesso");
                        clearMainArea();
                        loadCenterUI("/fxml/Employees.fxml");
                    } catch (Exception e) {
                        showMsg(e.getMessage());
                        e.printStackTrace();
                    }
                } else showMsg("Preencha todos os campos corretamente");
            });
        }
    }
}

