package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
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
                showLoader();
                if (nameInput.validate() && phoneInput.validate()) {
                    Task task = new Task() {

                        @Override
                        protected Integer call() throws Exception {
                            new EmployeeService().updateEmployee(new Employee(nameInput.getText(), phoneInput.getText(), actualEmployee.getId()));
                            return 1;
                        }

                        @Override
                        protected void succeeded() {
                            showMsg("Funcionario atualizado com sucesso");
                            clearMainArea();
                            loadCenterUI("/fxml/Employees.fxml");
                            actualEmployee = null;
                            hideLoader();
                        }

                        @Override
                        protected void failed() {
                            showMsg("Ocorreu um erro ao atualizar o funcionário");
                            hideLoader();
                            actualEmployee = null;
                        }
                    };
                    Thread t = new Thread(task);
                    t.setDaemon(true);
                    t.start();
                } else showMsg("Preencha todos os campos corretamente");
            });
            titleLabel.setText("Editar Funcionário");
        } else {
            doneButton.onActionProperty().set(ignored -> {
                showLoader();
                if (nameInput.validate() && phoneInput.validate()) {
                    Task task = new Task() {

                        @Override
                        protected Integer call() throws Exception {
                            new EmployeeService().addEmployee(new Employee(nameInput.getText(), phoneInput.getText()));
                            return 1;
                        }

                        @Override
                        protected void succeeded() {
                            showMsg("Funcionario cadastrado com sucesso");
                            clearMainArea();
                            loadCenterUI("/fxml/Employees.fxml");
                            actualEmployee = null;
                            hideLoader();
                        }

                        @Override
                        protected void failed() {
                            showMsg("Ocorreu um erro ao cadastrar o funcionário");
                            hideLoader();
                            actualEmployee = null;
                        }
                    };
                    Thread t = new Thread(task);
                    t.setDaemon(true);
                    t.start();
                } else showMsg("Preencha todos os campos corretamente");
            });
        }
    }
}

