package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public Button btnLogin;
    public PasswordField inputPass;
    public TextField inputEmail;
    public Pane msgPane;
    public Button closeMsgBtn;
    public Text msgText;

    public void makeLogin(ActionEvent event) throws IOException {
        String user = inputEmail.getText();
        String pass = inputPass.getText();
        if (!user.isEmpty() && pass.length() >= 8) {
            showMsg("Login efetuado com sucesso", 1);
            //Create new scene
            Parent homeParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene homeScene = new Scene(homeParent);
            //Get atual scene
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();
        } else {
            showMsg("Email ou senha incorretos", 0);
        }
    }

    public void hideMsg() {
        msgPane.setOpacity(0);
        msgPane.setStyle("-fx-background-color: #ee831e");
        msgText.setText("");
    }

    public void showMsg(String text, int type) {
        msgText.setText(text);
        if (type == 1)
            msgPane.setStyle("-fx-background-color: #6cb235; -fx-background-radius: 30;");
        else
            msgPane.setStyle("-fx-background-color: #ee831e; -fx-background-radius: 30;");
        msgPane.setOpacity(100);
    }
}
