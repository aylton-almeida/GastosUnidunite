package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Expense;
import services.ExpenseService;

import java.net.URL;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ExpensesController extends MainController implements Initializable {
    public TableView<Expense> mainTableView;
    public TableColumn<Expense, Integer> idColumn;
    public TableColumn<Expense, String> dateColumn;
    public TableColumn<Expense, String> descriptionColumn;
    public TableColumn<Expense, Double> valueColumn;
    private List<Expense> expenseList;
    private ExpenseService expenseService;
    private Expense expense;
    public JFXTextField searchInput;

    public void showOnTable(){
        this.expenseList.forEach(expense -> mainTableView.getItems().add(expense));
    }

    public void gotToRegisterExpense(ActionEvent event) {
        clearMainArea();
        loadCenterUI("RegisterExpense.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("Value"));

        try{
            expenseService = new ExpenseService();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            this.expenseList = expenseService.getAllExpenses();

        }catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }

        //Definir que quando o enter for pressionado o filtro ocorra
        searchInput.setOnAction(this::filterSearch);
        //Definir mudanca do filtro a medida que os dados sao digitados
        searchInput.onKeyReleasedProperty().set(e -> this.filterSearch(new ActionEvent()));

       // showOnTable();

    }

    public void clearSearch(ActionEvent event) {
        searchInput.setText(null);
        mainTableView.getItems().clear();
        //showOnTable();
    }



    public void filterSearch(ActionEvent event) {

        String input = searchInput.getText();

        mainTableView.getItems().clear();

        expenseList.forEach(expense -> {
            if (("" + )
                mainTableView.getItems().add(expense);
        });
    }
}
