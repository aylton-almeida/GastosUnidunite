package controllers;

import com.jfoenix.controls.JFXDatePicker;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Expense;
import services.ExpenseService;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ExpensesController extends MainController implements Initializable {
    public TableView<Expense> mainTableView;
    public TableColumn<Expense, String> dateColumn;
    public TableColumn<Expense, String> descriptionColumn;
    public TableColumn<Expense, Double> valueColumn;
    public JFXDatePicker toDateInput;
    public JFXDatePicker fromDateInput;
    public Label totalLabel;
    private List<Expense> expenseList;
    private ExpenseService expenseService;
    private double totalValue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("Value"));
        mainTableView.getItems().add(null);

        showLoader();
        Task retrieveSalesTask = new Task() {
            @Override
            protected List<Expense> call() throws Exception {
                expenseService = new ExpenseService();
                return expenseService.getAllExpenses();
            }

            @Override
            protected void succeeded() {
                expenseList = (List<Expense>) getValue();
                hideLoader();
                showOnTable(expenseList);
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
    }

    public void showOnTable(List<Expense> list) {
        mainTableView.getItems().clear();
        this.totalValue = 0;
        if (super.getLoggedUser().isAdmin())
            list.stream()
                    .sorted(Expense::compareTo)
                    .forEach(expense -> {
                        this.totalValue += expense.getValue();
                        mainTableView.getItems().add(expense);
                    });
        else
            list.stream()
                    .filter(Expense::isPublic)
                    .sorted(Expense::compareTo)
                    .forEach(expense -> {
                        this.totalValue += expense.getValue();
                        mainTableView.getItems().add(expense);
                    });
        DecimalFormat df = new DecimalFormat("##.##");
        this.totalLabel.setText("R$" + df.format(this.totalValue));
    }


    public void applyFilter() {
        Date toDate = toDateInput.getValue() != null ? Date.from(toDateInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
        Date fromDate = fromDateInput.getValue() != null ? Date.from(fromDateInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;

        mainTableView.getItems().clear();
        this.showOnTable(this.expenseList.stream()
                .filter(expense -> {
                    if (toDate != null && fromDate != null) {
                        try {
                            return fromDate.compareTo(expense.getDateComparable()) <= 0 && toDate.compareTo(expense.getDateComparable()) >= 0;
                        } catch (ParseException e) {
                            showMsg("Ocorreu um problema ao aplicar os filtros");
                            e.printStackTrace();
                        }
                    } else {
                        if (toDate != null) {
                            try {
                                return toDate.compareTo(expense.getDateComparable()) >= 0;
                            } catch (ParseException e) {
                                showMsg("Ocorreu um problema ao aplicar os filtros");
                                e.printStackTrace();
                            }
                        } else {
                            if (fromDate != null) {
                                try {
                                    return fromDate.compareTo(expense.getDateComparable()) <= 0;
                                } catch (ParseException e) {
                                    showMsg("Ocorreu um problema ao aplicar os filtros");
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList()));
    }

    public void deleteExpense(ActionEvent actionEvent) {
        if (mainTableView.getSelectionModel().getSelectedItem() != null) {
            showLoader();
            Task retriveClientsTask = new Task() {
                @Override
                protected Integer call() throws Exception {
                    expenseService.deleteExpense(mainTableView.getSelectionModel().getSelectedItem());
                    return 1;
                }

                @Override
                protected void succeeded() {
                    Expense expense = mainTableView.getSelectionModel().getSelectedItem();
                    mainTableView.getItems().removeAll(expense);
                    expenseList.remove(expense);
                    hideLoader();
                    showMsg("Gatos deletado com sucesso");
                }

                @Override
                protected void failed() {
                    getException().printStackTrace();
                    showMsg("Ocorreu um problema ao deletar os dados");
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

    public void editExpense(ActionEvent actionEvent) {
        if (mainTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                actualExpense = mainTableView.getSelectionModel().getSelectedItem();
                clearMainArea();
                loadCenterUI("/fxml/RegisterExpense.fxml");
            } catch (Exception e) {
                showMsg("Ocorreu um erro" + e.getMessage());
                e.printStackTrace();
            }
        } else showMsg("Selecione alguma linha para continuar");
    }

    public void goToRegisterExpense(ActionEvent actionEvent) {
        clearMainArea();
        loadCenterUI("/fxml/RegisterExpense.fxml");
    }
}
