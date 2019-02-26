package controllers;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import core.Product;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;
import services.ProductService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsController extends MainController implements Initializable {

    public JFXTreeTableView<Product> mainTableTree;

    public void goToRegisterProductButton(ActionEvent event) {
        clearMainArea();
        loadCenterUI("RegisterProducts.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JFXTreeTableColumn<Product, Integer> idColumn = new JFXTreeTableColumn<>("Id");
        idColumn.setPrefWidth(100);
        idColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Product, Integer> param) -> param.getValue().getValue().getID());

    }
}
