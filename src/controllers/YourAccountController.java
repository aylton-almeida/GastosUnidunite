package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class YourAccountController extends MainController implements Initializable {


    public Label labelBemVindo;
    public JFXTextField emailInput;
    public JFXPasswordField passInput;
    public JFXTextField confPassInput;
    public JFXButton updateDataBtn;
    public JFXButton createUser;
    public JFXCheckBox isAdminInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelBemVindo.setText(super.getLoggedUser().getEmail());

    }

    public void updateData(ActionEvent event) {
    }

    public void createUser(ActionEvent event) {
    }
}
