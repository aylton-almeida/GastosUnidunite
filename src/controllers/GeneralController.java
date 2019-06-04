package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Expense;
import logic.Sale;
import logic.Transaction;
import services.ExpenseService;
import services.SaleService;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GeneralController extends MainController implements Initializable {
    public JFXDatePicker toDateInput;
    public JFXDatePicker fromDateInput;
    public TableView<Transaction> mainTableView;
    public TableColumn<Object, Object> dateColumn;
    public TableColumn<Object, Object> productsColumn;
    public TableColumn<Object, Object> sellerColumn;
    public TableColumn<Object, Object> clientColumn;
    public TableColumn<Object, Object> valueColumn;
    public TableColumn<Object, Object> paymentColumn;
    public Label totalLabel;
    public JFXComboBox<String> sellerComboBox;
    public JFXComboBox<String> payTypeComboBox;
    private List<Transaction> transactionList;
    private HashSet<String> employeeNames;
    private double totalValue = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        productsColumn.setCellValueFactory(new PropertyValueFactory<>("ProductString"));
        sellerColumn.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("ClientName"));
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("PayTypeString"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("Value"));
        mainTableView.getItems().add(null);
        employeeNames = new HashSet<>();

        showLoader();
        Task retrieveSalesTask = new Task() {
            @Override
            protected List<Sale> call() throws Exception {
                return SaleService.getAllSales();
            }

            @Override
            protected void succeeded() {
                transactionList = (List<Transaction>) getValue();
                transactionList.forEach(t -> employeeNames.add(((Sale) t).getEmployeeName()));
                payTypeComboBox.getItems().addAll(
                        "Todos",
                        "A vista",
                        "Cartão de Crédito",
                        "Cartão de Débito",
                        "Cheque",
                        "Crediário"
                );
                sellerComboBox.getItems().add("Todos");
                employeeNames.forEach(name -> sellerComboBox.getItems().add(name));

                dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
                productsColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
                valueColumn.setCellValueFactory(new PropertyValueFactory<>("Value"));

                Task retrieveSalesTask = new Task() {
                    @Override
                    protected List<Expense> call() throws Exception {
                        ExpenseService expenseService = new ExpenseService();
                        return expenseService.getAllExpenses();
                    }

                    @Override
                    protected void succeeded() {
                        transactionList.addAll((List<Expense>) getValue());
                        hideLoader();
                        showOnTable(transactionList);
                    }

                    @Override
                    protected void failed() {
                        getException().printStackTrace();
                        showMsg("Ocorreu um problema ao recuperar os dados");
                        hideLoader();
                    }
                };
                Thread t = new Thread(retrieveSalesTask);
                t.setDaemon(true);
                t.start();

            }

            @Override
            protected void failed() {
                getException().printStackTrace();
                showMsg("Ocorreu um problema ao recuperar os dados");
                hideLoader();
            }
        };
        Thread t = new Thread(retrieveSalesTask);
        t.setDaemon(true);
        t.start();

        //Definir qua ao mudar a data digitada o filtro seja aplicado
        fromDateInput.valueProperty().addListener(ignored -> applyFilter());
        toDateInput.valueProperty().addListener(ignored -> applyFilter());

        //Definir que ao selecionar um filtro de combobox a lista mude
        sellerComboBox.valueProperty().addListener(ignored -> this.applyFilter());
        payTypeComboBox.valueProperty().addListener(ignored -> this.applyFilter());
    }

    private void showOnTable(List<Transaction> list) {
        mainTableView.getItems().clear();
        this.totalValue = 0;
        list.stream()
                .sorted(Transaction::compareTo)
                .forEach(t -> {
                    this.totalValue += t.getValue();
                    mainTableView.getItems().add(t);
                });
        DecimalFormat df = new DecimalFormat("##.##");
        this.totalLabel.setText("R$" + df.format(this.totalValue));
    }

    private void applyFilter() {
        Date toDate = toDateInput.getValue() != null ? Date.from(toDateInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
        Date fromDate = fromDateInput.getValue() != null ? Date.from(fromDateInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;

        mainTableView.getItems().clear();
        List<Transaction> list = this.transactionList.stream()
                .filter(t -> {
                    if (toDate != null && fromDate != null) {
                        try {
                            return fromDate.compareTo(t.getDateComparable()) <= 0 && toDate.compareTo(t.getDateComparable()) >= 0;
                        } catch (ParseException e) {
                            showMsg("Ocorreu um problema ao aplicar os filtros");
                            e.printStackTrace();
                        }
                    } else {
                        if (toDate != null) {
                            try {
                                return toDate.compareTo(t.getDateComparable()) >= 0;
                            } catch (ParseException e) {
                                showMsg("Ocorreu um problema ao aplicar os filtros");
                                e.printStackTrace();
                            }
                        } else {
                            if (fromDate != null) {
                                try {
                                    return fromDate.compareTo(t.getDateComparable()) <= 0;
                                } catch (ParseException e) {
                                    showMsg("Ocorreu um problema ao aplicar os filtros");
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    return true;
                })
                .filter(t -> {
                    if (sellerComboBox.getValue() != null && !sellerComboBox.getValue().equals("Todos"))
                        if (t.getClass().equals(Sale.class))
                            return ((Sale) t).getEmployeeName().equals(sellerComboBox.getValue());
                        else
                            return false;
                    else
                        return true;
                })
                .filter(t -> {
                    int payType;
                    if (payTypeComboBox.getValue() != null) {
                        switch (payTypeComboBox.getValue()) {
                            case "A vista":
                                payType = 5;
                                break;
                            case "Cartão de Crédito":
                                payType = 1;
                                break;
                            case "Cartão de Débito":
                                payType = 2;
                                break;
                            case "Cheque":
                                payType = 3;
                                break;
                            case "Crediário":
                                payType = 4;
                                break;
                            default:
                                payType = -1;
                                break;
                        }
                        if (payType > 0)
                            if (t.getClass().equals(Sale.class))
                                return ((Sale) t).getPayType() == payType;
                            else
                                return false;
                        else
                            return true;
                    } else
                        return true;
                })
                .collect(Collectors.toList());
        this.showOnTable(list);
    }
}
