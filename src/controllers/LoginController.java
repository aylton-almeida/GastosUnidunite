package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import logic.User;
import services.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends MainController implements Initializable {
    public JFXPasswordField inputPass;
    public JFXTextField inputEmail;
    private static UserService userService;

    public void makeLogin(ActionEvent event) {
//        showLoader();
        String userInput = inputEmail.getText();
        String passInput = inputPass.getText();
        if (!userInput.isEmpty() && passInput.length() >= 8 && isEmailValid(inputEmail)) {
            try {
                final User[] user = new User[1];
                user[0] = userService.login(userInput, passInput);
//                Thread t = new Thread(() -> {
//                    try {
//                        user[0] = userService.login(userInput, passInput);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//                t.start();
//                while (t.getState() != Thread.State.TERMINATED);
                if (user[0].getId() != -1) {
                    setLoggedUser(user[0]);
                    clearScreen();
                    // hideLoader();
                    loadLeftUI("/fxml/NavBar.fxml");
                } else {
                    //hideLoader();
                    showMsg("Email ou senha incorretos");
                }
            } catch (Exception e) {
                //hideLoader();
                showMsg(e.getMessage());
                e.printStackTrace();
            }
        } else {
            //hideLoader();
            showMsg("Preencha os campos corretamente");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addRequiredValidator(inputEmail);
        addRequiredValidator(inputPass);
        loadLeftUI("/fxml/LogoLeftBar.fxml");
        try {
            userService = new UserService();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Definir click do enter como click do botao
        inputPass.onActionProperty().set(e -> makeLogin(new ActionEvent()));
    }

}
