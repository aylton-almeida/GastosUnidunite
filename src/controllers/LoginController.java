package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import logic.User;
import services.UserService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController extends MainController implements Initializable {
    public JFXPasswordField inputPass;
    public JFXTextField inputEmail;
    private static UserService userService;

    public void makeLogin(ActionEvent event) {
        showLoader();
        String userInput = inputEmail.getText();
        String passInput = inputPass.getText();
        if (!userInput.isEmpty() && passInput.length() >= 8 && isEmailValid(inputEmail)) {
            try {
                Task retriveClientsTask = new Task() {
                    @Override
                    protected User call() throws Exception {
                        userService = new UserService();
                        return userService.login(userInput, passInput);
                    }

                    @Override
                    protected void succeeded() {
                        User user = (User) getValue();
                        if (user.getId() != -1) {
                            setLoggedUser(user);
                            clearScreen();
                            hideLoader();
                            loadLeftUI("/fxml/NavBar.fxml");
                        } else {
                            hideLoader();
                            showMsg("Email ou senha incorretos");
                        }
                    }
                };
                Thread t = new Thread(retriveClientsTask);
                t.setDaemon(true);
                t.start();
            } catch (Exception e) {
                hideLoader();
                showMsg("Ocorreu um erro" + e.getMessage());
                e.printStackTrace();
            }
        } else {
            hideLoader();
            showMsg("Preencha os campos corretamente");
        }
    }

//    private User requestUser() throws Exception {
//        User[] users = new User[1];
//        try {
//            users[0] = userService.login(inputEmail.getText(), inputPass.getText());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return users[0];
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addRequiredValidator(inputEmail);
        addRequiredValidator(inputPass);
        loadLeftUI("/fxml/LogoLeftBar.fxml");

        //Definir click do enter como click do botao
        inputPass.onActionProperty().set(e -> makeLogin(new ActionEvent()));
    }

}
