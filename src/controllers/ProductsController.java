package controllers;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import core.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.control.TreeItem;
import services.ProductService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsController extends MainController implements Initializable {

    public JFXTreeTableView mainTableTree;
    public JFXTreeTableColumn idColumn;
    public JFXTreeTableColumn nameColumn;
    public JFXTreeTableColumn sizeColumn;
    public JFXTreeTableColumn factoryColumn;
    public JFXTreeTableColumn valueColumn;
    private ObservableList<Product> data;

    public void goToRegisterProductButton(ActionEvent event) {
        clearMainArea();
        loadCenterUI("RegisterProducts.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn = new JFXTreeTableColumn("id");
        nameColumn = new JFXTreeTableColumn("name");
        sizeColumn = new JFXTreeTableColumn("size");
        factoryColumn = new JFXTreeTableColumn("factory");
        valueColumn = new JFXTreeTableColumn("value");

        mainTableTree.getColumns().addAll(idColumn, nameColumn, sizeColumn, factoryColumn, valueColumn);

        try {
            data = FXCollections.observableArrayList();
            data.addAll(new ProductService().getAllProducts());
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }

        idColumn.setCellFactory(new TreeItemPropertyValueFactory<Product, Integer>("id"));
        nameColumn.setCellFactory(new TreeItemPropertyValueFactory<Product, String>("name"));
        sizeColumn.setCellFactory(new TreeItemPropertyValueFactory<Product, String>("size"));
        factoryColumn.setCellFactory(new TreeItemPropertyValueFactory<Product, String>("factory"));
        valueColumn.setCellFactory(new TreeItemPropertyValueFactory<Product, Double>("value"));

        TreeItem<Product> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);

        mainTableTree.setRoot(root);
    }
}
