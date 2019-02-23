package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public JFXButton vendasButton;
    public JFXButton gastosButton;
    public JFXButton totaisButton;
    public JFXButton produtosButton;
    public JFXButton clientesButton;
    public JFXButton geralButton;
    public JFXButton funcionariosButton;
    public StackPane myStackPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void showMsg(String text) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setBody(new Text(text));
        JFXDialog dialog = new JFXDialog(myStackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        dialog.show();
    }
}


