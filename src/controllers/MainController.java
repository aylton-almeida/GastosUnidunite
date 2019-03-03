package controllers;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    public StackPane mainStackPane;
    public BorderPane mainBorderPane;

    public MainController(StackPane stackPane, BorderPane borderPane){
        this.mainStackPane = stackPane;
        this.mainBorderPane = borderPane;
    }

    public MainController(){
        this.mainBorderPane = null;
        this.mainStackPane = null;
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
    }

    public void showLoader(){
        JFXSpinner spinner = new JFXSpinner();
        spinner.setRadius(30);
        mainStackPane.getChildren().add(spinner);
    }

    public void hideLoader(){
        mainStackPane.getChildren().remove(1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCenterUI("Login.fxml");
    }
}
