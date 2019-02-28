package controllers;

import core.Product;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ProductService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsController extends MainController implements Initializable {

    public TableView<Product> mainTableView;
    public TableColumn<Product, Integer> idColumn;
    public TableColumn<Product, String> nameColumn;
    public TableColumn<Product, String> sizeColumn;
    public TableColumn<Product, String> factoryColumn;
    public TableColumn<Product, Double> valueColumn;

    public void goToRegisterProductButton(ActionEvent event) {
        clearMainArea();
        loadCenterUI("RegisterProducts.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("Size"));
        factoryColumn.setCellValueFactory(new PropertyValueFactory<>("Factory"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("Value"));


        List<Product> list = null;
        try{
            list = new ProductService().getAllProducts();
        }catch(Exception e){
            showMsg(e.getMessage());
            e.printStackTrace();
        }

        list.forEach(product -> mainTableView.getItems().add(product));
    }
}
