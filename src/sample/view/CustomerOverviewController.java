package sample.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Client;
import sample.entity.Customer;
import sample.util.DateUtil;
import sample.view.model.CustomerProperty;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerOverviewController {
    @FXML
    private TableView<CustomerProperty> customerTableView;
    @FXML
    private TableColumn<CustomerProperty, String> firstNameColumn;
    @FXML
    private TableColumn<CustomerProperty, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    private Client client;

    private ObservableList<CustomerProperty> customerProperties = FXCollections.observableArrayList();

    public CustomerOverviewController() {
    }


    @FXML
    private void initialize() {


        customerTableView.setItems(customerProperties);
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        showCustomerDetails(null);

        customerTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCustomerDetails(newValue)
        );
    }


    private void showCustomerDetails(CustomerProperty customer) {
        if (customer != null) {
            firstNameLabel.setText(customer.getFirstName());
            lastNameLabel.setText(customer.getLastName());
            streetLabel.setText(customer.getStreet());
            postalCodeLabel.setText(Integer.toString(customer.getPostalCode()));
            cityLabel.setText(customer.getCity());
            birthdayLabel.setText(DateUtil.format(customer.getBirthday()));

            deleteButton.setDisable(false);
            editButton.setDisable(false);
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
            deleteButton.setDisable(true);
            editButton.setDisable(true);
        }
    }

    public boolean showCustomerEditDialog(CustomerProperty person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("CustomerEditDialog.fxml"));

            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Customer");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(client.getPrimaryStage());

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CustomerEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCustomerProperty(person);

            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @FXML
    private void newButtonPressed() {
        CustomerProperty tempPerson = new CustomerProperty();
        boolean okClicked = showCustomerEditDialog(tempPerson);

        if (okClicked) {
            customerProperties.add(tempPerson);
            try {
                client.getServer().addCustomer(tempPerson.toCustomer());
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void editButtonClicked() {
        CustomerProperty selectedPerson = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = showCustomerEditDialog(selectedPerson);

            if (okClicked) {
                showCustomerDetails(selectedPerson);
                try {
                    client.getServer().delCustomer(selectedPerson.getId());
                    client.getServer().addCustomer(selectedPerson.toCustomer());
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Edit");
            alert.setHeaderText("No Selection");
            alert.setContentText("Please select a  in the table.");

            alert.show();
        }
    }

    @FXML
    private void deleteButtonPressed() {
        int index = customerTableView.getSelectionModel().getSelectedIndex();
        CustomerProperty customer = customerTableView.getItems().get(index);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("conform");
        alert.setContentText("delete " + customer.getFirstName() + " " + customer.getLastName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            customerTableView.getItems().remove(index);
            try {
                client.getServer().delCustomer(customer.getId());
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void setClient(Client client) {
        this.client = client;
        try {
            List<Customer> customers = client.getServer().getCustomers();
            if (customers != null) {
                for (Customer customer : customers) {
                    customerProperties.add(new CustomerProperty(customer));
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void productButtonPressed(ActionEvent actionEvent) {
        client.showProductOverview();
    }
}
