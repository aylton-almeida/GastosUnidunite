package controllers;

import javafx.scene.control.TreeTableColumn;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import core.Product;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javax.security.auth.callback.Callback;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductsController extends MainController implements Initializable {

    public JFXTreeTableView<Product> mainTableTree;

    public void goToRegisterProductButton(ActionEvent event) {
        clearMainArea();
        loadCenterUI("RegisterProducts.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JFXTreeTableColumn<Product, Integer> idColumn = new JFXTreeTableColumn("Id");
//        idColumn.setCellValueFactory(Callback<TreeTableColumn.>);
        //https://www.youtube.com/watch?v=pPTlDH8FVpM
        JFXTreeTableColumn nameColumn;
        JFXTreeTableColumn sizeColumn;
        JFXTreeTableColumn factoryColumn;
        JFXTreeTableColumn valueColumn;
    }
}
