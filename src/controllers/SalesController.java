package controllers;

import com.jfoenix.controls.JFXDatePicker;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Client;
import logic.Employee;
import logic.Product;
import logic.Sale;
import services.SaleService;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class SalesController extends MainController implements Initializable {
    public JFXDatePicker toDateInput;
    public JFXDatePicker fromDateInput;
    public TableView<Sale> mainTableView;
    public TableColumn<Object, Object> dateColumn;
    public TableColumn<Object, Object> productsColumn;
    public TableColumn<Object, Object> sellerColumn;
    public TableColumn<Object, Object> clientColumn;
    public TableColumn<Object, Object> valueColumn;
    private List<Client> clientList;
    private List<Product> productList;
    private List<Employee> employeeList;
    private List<Sale> saleList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        sellerColumn.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("ClientId"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("Value"));
        mainTableView.getItems().add(null);

        showLoader();
        Task retriveClientsTask = new Task() {
            @Override
            protected List<Sale> call() throws Exception {
                return SaleService.getAllSales();
            }

            @Override
            protected void succeeded() {
                saleList = (List<Sale>) getValue();
                hideLoader();
                showOnTable();
            }

            @Override
            protected void failed() {
                getException().printStackTrace();
                showMsg("Ocorreu um problema ao recuperar os dados");
                hideLoader();
            }
        };
        Thread t = new Thread(retriveClientsTask);
        t.setDaemon(true);
        t.start();
    }

    private void showOnTable() {
        mainTableView.getItems().clear();
        this.saleList.stream()
                .sorted(Sale::compareTo)
                .forEach(sale -> mainTableView.getItems().add(sale));
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

    public void editSale(ActionEvent actionEvent) {
    }

    public void deleteSale(ActionEvent actionEvent) {
        if (mainTableView.getSelectionModel().getSelectedItem() != null) {
            showLoader();
            Task retriveClientsTask = new Task() {
                @Override
                protected Integer call() throws Exception {
                    SaleService.deleteSale(mainTableView.getSelectionModel().getSelectedItem());
                    return 1;
                }

                @Override
                protected void succeeded() {
                    Sale s = mainTableView.getSelectionModel().getSelectedItem();
                    mainTableView.getItems().removeAll(s);
                    saleList.remove(s);
                    hideLoader();
                    showMsg("Venda deletada com sucesso");
                }

                @Override
                protected void failed() {
                    getException().printStackTrace();
                    showMsg("Ocorreu um problema ao recuperar os dados");
                    hideLoader();
                }
            };
            Thread t = new Thread(retriveClientsTask);
            t.setDaemon(true);
            t.start();
        } else {
            showMsg("Selecione alguma linha para continuar");
        }
    }
}
