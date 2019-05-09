package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Client;
import services.ClientService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientsController extends MainController implements Initializable {
    public JFXTextField searchInput;
    public TableView<Client> mainTableView;
    public TableColumn<Object, Object> idColumn;
    public TableColumn<Object, Object> nameColumn;
    public TableColumn<Object, Object> adressColumn;
    public TableColumn<Object, Object> emailColumn;
    public TableColumn<Object, Object> phoneColumn;
    private List<Client> clientsList;
    private ClientService clientService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        adressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        mainTableView.getItems().add(null);

        showLoader();
        Task retriveClientsTask = new Task() {

            @Override
            protected List<Client> call() throws Exception {
                clientService = new ClientService();
                return clientService.getAllClients();
            }

            @Override
            protected void succeeded() {
                clientsList = (List<Client>) getValue();
                hideLoader();
                showOnTable();
            }
        };
        Thread t = new Thread(retriveClientsTask);
        t.setDaemon(true);
        t.start();

        //Definir que quando o enter for pressionado o filtro ocorra
        searchInput.setOnAction(this::filterSearch);
        //Definir mudanca do filtro a medida que os dados sao digitados
        searchInput.onKeyReleasedProperty().set(e -> this.filterSearch(new ActionEvent()));
    }


    private void showOnTable() {
        mainTableView.getItems().clear();
        this.clientsList.stream()
                .sorted(Client::compareTo)
                .forEach(client -> mainTableView.getItems().add(client));
    }

    public void goToRegisterClients(ActionEvent event) {
        clearMainArea();
        loadCenterUI("/fxml/RegisterClients.fxml");
    }

    public void filterSearch(ActionEvent event) {

        String input = searchInput.getText();

        mainTableView.getItems().clear();

        clientsList.forEach(client -> {
            if (("" + client.getId()).startsWith(input) || client.getName().toLowerCase().startsWith(input.toLowerCase()))
                mainTableView.getItems().add(client);
        });
    }

    public void deleteClients(ActionEvent actionEvent) {
        showLoader();
        if (mainTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                Client c = mainTableView.getSelectionModel().getSelectedItem();
                mainTableView.getItems().removeAll(c);
                clientService.deleteClient(c);
                clientsList.remove(c);
                hideLoader();
            } catch (Exception e) {
                showMsg("Ocorreu um erro" + e.getMessage());
                e.printStackTrace();
                hideLoader();
            }
        } else {
            showMsg("Selecione alguma linha para continuar");
            hideLoader();
        }
    }

    public void editClients(ActionEvent actionEvent) {
        if (mainTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                actualClient = mainTableView.getSelectionModel().getSelectedItem();
                clearMainArea();
                loadCenterUI("/fxml/RegisterClients.fxml");
            } catch (Exception e) {
                showMsg("Ocorreu um erro" + e.getMessage());
                e.printStackTrace();
            }
        } else showMsg("Selecione alguma linha para continuar");
    }
}
