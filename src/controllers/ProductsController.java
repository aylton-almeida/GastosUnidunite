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
import javafx.scene.control.TreeItem;
import services.ProductService;

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
        idColumn.setPrefWidth(100);
        idColumn.setCellValueFactory(param -> param.getValue().getValue().getIdProperty().asObject());

        JFXTreeTableColumn<Product, String> nameColumn = new JFXTreeTableColumn("Name");
        nameColumn.setPrefWidth(100);
        nameColumn.setCellValueFactory(param -> param.getValue().getValue().getNameProperty());

        JFXTreeTableColumn<Product, String> sizeColumn = new JFXTreeTableColumn("Size");
        sizeColumn.setPrefWidth(100);
        sizeColumn.setCellValueFactory(param -> param.getValue().getValue().getSizeProperty());

        JFXTreeTableColumn<Product, String> factoryColumn = new JFXTreeTableColumn("Factory");
        factoryColumn.setPrefWidth(100);
        factoryColumn.setCellValueFactory(param -> param.getValue().getValue().getFactoryProperty());

        JFXTreeTableColumn<Product, Double> valueColumn = new JFXTreeTableColumn("Value");
        valueColumn.setPrefWidth(100);
        valueColumn.setCellValueFactory(param -> param.getValue().getValue().getValueProperty().asObject());

        ObservableList<Product> products = null;
        try {
            products = FXCollections.observableArrayList(new ProductService().getAllProducts());
        } catch (Exception e) {
            showMsg(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        final TreeItem<Product> root = new RecursiveTreeItem<>(products, RecursiveTreeObject::getChildren);
        mainTableTree.getColumns().setAll(idColumn, nameColumn, sizeColumn, factoryColumn, valueColumn);
        mainTableTree.setRoot(root);
        mainTableTree.setShowRoot(false);
    }
}
