package controllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import logic.Client;
import logic.Product;
import logic.User;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    public StackPane mainStackPane;
    public BorderPane mainBorderPane;
    public static User loggedUser = null;
    public static Product actualProduct = null;
    public static Client actualClient = null;

    public MainController(StackPane stackPane, BorderPane borderPane){
        this.mainStackPane = stackPane;
        this.mainBorderPane = borderPane;
    }

    public MainController(){
        this.mainBorderPane = null;
        this.mainStackPane = null;
    }

    public User getLoggedUser(){
        return loggedUser;
    }

    public void setLoggedUser(User user){
        this.loggedUser = user;
    }

    public void showMsg(String text){
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setBody(new Text(text));
        JFXDialog dialog = new JFXDialog(this.mainStackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        dialog.show();
    }

    public void loadCenterUI(String ui){
        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource(ui));
        }catch (Exception e){
            showMsg(e.getMessage());
            e.printStackTrace();
        }
        this.mainBorderPane.setCenter(root);
    }

    public void loadLeftUI(String ui){
        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource(ui));
        }catch (Exception e){
            showMsg(e.getMessage());
            e.printStackTrace();
        }
        this.mainBorderPane.setLeft(root);
    }

    public void clearScreen(){
        this.mainBorderPane.setLeft(null);
        this.mainBorderPane.setCenter(null);
        this.mainBorderPane.setTop(null);
        this.mainBorderPane.setRight(null);
        this.mainBorderPane.setBottom(null);
    }

    public void clearNavBar(){
        this.mainBorderPane.setCenter(null);
    }

    public void clearMainArea(){
        this.mainBorderPane.setCenter(null);
        this.mainBorderPane.setTop(null);
        this.mainBorderPane.setBottom(null);
    }

    public void showLoader(){
        JFXSpinner spinner = new JFXSpinner();
        spinner.setRadius(30);
        mainStackPane.getChildren().add(spinner);
    }

    public void hideLoader(){
        mainStackPane.getChildren().remove(1);
    }

    public boolean isEmailValid(JFXTextField field){
        if (!field.getText().contains("@") || !field.getText().contains(".")){
            return false;
        }
        return true;
    }

    public void addRequiredValidator(JFXTextField field){
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Esse campo é obrigatório");
        field.getValidators().add(requiredFieldValidator);
        field.focusedProperty().addListener(((o, oldValue, newValue) -> {
            if (!newValue)
                field.validate();
        }));
    }

    public void addRequiredValidator(JFXPasswordField field){
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Esse campo é obrigatório");
        field.getValidators().add(requiredFieldValidator);
        field.focusedProperty().addListener(((o, oldValue, newValue) -> {
            if (!newValue)
                field.validate();
        }));
    }

    public void addRequiredValidator(JFXComboBox field){
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Esse campo é obrigatório");
        field.getValidators().add(requiredFieldValidator);
        field.focusedProperty().addListener(((o, oldValue, newValue) -> {
            if (!newValue)
                field.validate();
        }));
    }

    public void addNumberValidator(JFXTextField field){
        NumberValidator numberValidator = new NumberValidator();
        numberValidator.setMessage("Digite apenas números");
        field.getValidators().add(numberValidator);
        field.setOnKeyReleased(e ->{
            String text = field.getText();
            if (!field.getText().isEmpty() && !field.validate()){
                field.setText(text.substring(0, text.length() - 1));
                field.positionCaret(field.getText().length());
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCenterUI("/fxml/Login.fxml");
    }
}
