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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Recuperar dados do banco e mostra-los na tabela
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

        //Definir que quando o enter for pressionado o filtro ocorra
        searchInput.setOnAction(this::filterSearch);
        //Definir mudanca do filtro a medida que os dados sao digitados
        searchInput.onKeyReleasedProperty().set(e -> this.filterSearch(new ActionEvent()));
    }

    private void showOnTable() {
        this.productList.stream()
                .sorted(Product::compareTo)
                .forEach(product -> mainTableView.getItems().add(product));
    }

    public void goToRegisterProduct(ActionEvent event) {
        clearMainArea();
        loadCenterUI("/fxml/RegisterProducts.fxml");
    }

    //Filtra a pesquisa
    public void filterSearch(ActionEvent event) {

        mainTableView.getItems().clear();

        String input = searchInput.getText();

        productList.forEach(product -> {
            if (("" + product.getId()).startsWith(input) || product.getName().toLowerCase().startsWith(input.toLowerCase()))
                mainTableView.getItems().add(product);
        });
    }

    //Deleta o produto selecionado na tabela
    public void deleteProduct(ActionEvent actionEvent) {
        try {
            Product p = mainTableView.getSelectionModel().getSelectedItem();
            mainTableView.getItems().removeAll(p);
            productService.deleteProduct(p);
            productList.remove(p);
        } catch (Exception ignored) {
        }
    }

    //Abre uma pagina para edicao do produto selecionado da tabela
    public void editProduct(ActionEvent actionEvent) {
        try {
            actualProduct = mainTableView.getSelectionModel().getSelectedItem();
            clearMainArea();
            loadCenterUI("/fxml/RegisterProducts.fxml");
        } catch (Exception ignored) {
        }
    }
}
