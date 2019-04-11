package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Product;
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
    public JFXTextField searchInput;
    private List<Product> productList;
    private ProductService productService;

    private void showOnTable() {
        this.productList.stream()
                .sorted(Product::compareTo)
                .forEach(product -> mainTableView.getItems().add(product));
    }

    public void goToRegisterProduct(ActionEvent event) {
        clearMainArea();
        loadCenterUI("/fxml/RegisterProducts.fxml");
    }

    public void filterSearch(ActionEvent event) {

        mainTableView.getItems().clear();

        String input = searchInput.getText();

        productList.forEach(product -> {
            if (("" + product.getId()).equals(input) || product.getName().equalsIgnoreCase(input))
                mainTableView.getItems().add(product);
        });
    }

    public void clearSearch(ActionEvent event) {
        searchInput.setText(null);
        mainTableView.getItems().clear();
        showOnTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("Size"));
        factoryColumn.setCellValueFactory(new PropertyValueFactory<>("Factory"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("Value"));

        try {
            productService = new ProductService();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.productList = productService.getAllProducts();
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }

        showOnTable();
    }
}
