package controllers;

import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import services.SaleService;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SalesController extends MainController implements Initializable {
    public JFXDatePicker toDateInput;
    public JFXDatePicker fromDateInput;
    public TableView mainTableView;
    public TableColumn idColumn;
    public TableColumn productsColumn;
    public TableColumn sellerColumn;
    public TableColumn clientColumn;
    public TableColumn valueColumn;
    public SaleService saleService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void applyFilter(ActionEvent actionEvent) {
        System.out.println(toDateInput.validate());
        System.out.println(toDateInput.getValue());
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        System.out.println(formatter1.format(toDateInput.getValue()));
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("YYYYMMdd");
        System.out.println(formatter2.format(toDateInput.getValue()));
    }

    public void goToRegisterSale(ActionEvent actionEvent) {
        clearMainArea();
        loadCenterUI("/fxml/RegisterSale.fxml");
    }

    public void clearFilter(ActionEvent actionEvent) {
    }
}
