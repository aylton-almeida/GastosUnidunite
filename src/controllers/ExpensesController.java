package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ExpensesController extends MainController implements Initializable {
    public TableView mainTableView;
    public TableColumn dateColumn;
    public TableColumn descriptionColumn;
    public TableColumn valueColumn;

    public void gotToRegisterExpense(ActionEvent event) {
        clearMainArea();
        loadCenterUI("RegisterExpense.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void clearSearch(ActionEvent actionEvent) {
    }

    public void filterSearch(ActionEvent actionEvent) {
    }
}
