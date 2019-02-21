package controllers;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import core.User;
import dao.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class LoginController {
    public Button btnLogin;
    public PasswordField inputPass;
    public TextField inputEmail;
    public Pane msgPane;
    public Button closeMsgBtn;
    public Text msgText;
    public AnchorPane mainPane;
    public StackPane myStackPane;

    public void makeLogin(ActionEvent event) throws Exception {
        String userInput = inputEmail.getText();
        String passInput = inputPass.getText();
        if (!userInput.isEmpty() && passInput.length() >= 8) {
            List<User> users = new Users().getAllObjects();
            System.out.println(users.isEmpty());
            boolean passCorrect = users.stream()
                    .anyMatch(user -> user.getEmail().equals(userInput) && user.getPassword().equals(passInput));
            if (passCorrect){
                //Create new scene
                Parent homeParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Scene homeScene = new Scene(homeParent);
                //Get atual scene
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(homeScene);
                window.show();
            }else{
                showMsg("Email ou senha incorretos");
            }
        } else {
            showMsg("Preencha os campos corretamente");
        }
    }

    public void showMsg(String text) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setBody(new Text(text));
        JFXDialog dialog = new JFXDialog(myStackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        dialog.show();
    }
}
