package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import logic.User;
import services.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class YourAccountController extends MainController implements Initializable {


    public Label labelBemVindo;
    public JFXTextField emailInput;
    public JFXPasswordField passInput;
    public JFXPasswordField confPassInput;
    public JFXButton updateDataBtn;
    public JFXButton createUserBtn;
    public JFXCheckBox isAdminInput;
    private static UserService userService;

    public void updateData(ActionEvent event) {
        showLoader();
        User user = loggedUser;
        try {
            if (isEmailValid(emailInput)) {
                user.setEmail(emailInput.getText());
                if (passInput.getText().equals(confPassInput.getText())) {
                    user.setPassword(passInput.getText());
                    user.setIsAdmin(isAdminInput.selectedProperty().get());
                    Task retrieveSalesTask = new Task() {
                        @Override
                        protected Integer call() throws Exception {
                            userService.updateUser(loggedUser);
                            return 1;
                        }

                        @Override
                        protected void succeeded() {
                            hideLoader();
                            showMsg("Usuario atualizado com sucesso");
                            clearMainArea();
                            loadCenterUI("/fxml/Sales.fxml");
                        }

                        @Override
                        protected void failed() {
                            getException().printStackTrace();
                            showMsg("Ocorreu um problema ao recuperar os dados");
                            hideLoader();
                        }
                    };
                    Thread t = new Thread(retrieveSalesTask);
                    t.setDaemon(true);
                    t.start();
                } else
                    showMsg("As senhas não coincidem");
            } else
                showMsg("Digite um e-mail valido");
        } catch (Exception e) {
            showMsg("Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createUser(ActionEvent event) {
        showLoader();
        if (emailInput.validate() && passInput.validate() && confPassInput.validate()) {
            try {
                if (isEmailValid(emailInput)) {
                    if (passInput.validate() && passInput.getText().equals(confPassInput.getText())) {
                        Task retrieveSalesTask = new Task() {
                            @Override
                            protected Integer call() throws Exception {
                                userService.addUser(emailInput.getText(), passInput.getText(), isAdminInput.selectedProperty().get());
                                return 1;
                            }

                            @Override
                            protected void succeeded() {
                                hideLoader();
                                showMsg("Usuário cadastrado com sucesso");
                                clearMainArea();
                                loadCenterUI("/fxml/Sales.fxml");
                            }

                            @Override
                            protected void failed() {
                                getException().printStackTrace();
                                showMsg("Ocorreu um problema ao recuperar os dados");
                                hideLoader();
                            }
                        };
                        Thread t = new Thread(retrieveSalesTask);
                        t.setDaemon(true);
                        t.start();
                    } else {
                        showMsg("As senhas não batem");
                    }
                } else {
                    showMsg("Digite um email valido");
                }
            } catch (Exception e) {
                showMsg("Ocorreu um erro: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelBemVindo.setText("Bem vindo " + super.getLoggedUser().getEmail());
        emailInput.setText(super.getLoggedUser().getEmail());
        isAdminInput.selectedProperty().setValue(super.getLoggedUser().isAdmin());
        if (!super.getLoggedUser().isAdmin()) {
            createUserBtn.setOpacity(0);
            updateDataBtn.setLayoutX(407);
            isAdminInput.setOpacity(0);
        }

        addRequiredValidator(emailInput);
        addRequiredValidator(passInput);
        addRequiredValidator(confPassInput);

        showLoader();
        Task retrieveSalesTask = new Task() {
            @Override
            protected Integer call() throws Exception {
                userService = new UserService();
                return 1;
            }

            @Override
            protected void succeeded() {
                hideLoader();
            }

            @Override
            protected void failed() {
                getException().printStackTrace();
                showMsg("Ocorreu um problema ao recuperar os dados");
                hideLoader();
            }
        };
        Thread t = new Thread(retrieveSalesTask);
        t.setDaemon(true);
        t.start();
    }
}
