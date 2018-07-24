package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Client;
import sample.entity.Customer;
import sample.entity.Order;
import sample.view.model.ProductProperty;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class buyDialogController {

    public TextField qtyTextField;
    @FXML
    private Label productInfoLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private ChoiceBox customerChoice;
    private ProductProperty productProperty;
    private List<Customer> customers;
    private Stage dialogStage;
    private int shopId;
    private Client client;

    public void setClient(Client client, ProductProperty productProperty) {
        this.client = client;
        this.productProperty = productProperty;
        try {
            customers = client.getServer().getCustomers();
            if (customers != null) {
                for (Customer customer : customers) {
                    customerChoice.getItems().add(customer.toString());
                }
                customerChoice.getSelectionModel().select(0);
            }
            productInfoLabel.setText(productProperty.toString());
            qtyTextField.textProperty().addListener((observable, oldValue, newValue) -> calcTotal());


            qtyTextField.setText("1");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void calcTotal() {
        try {
            int qty = Integer.parseInt(qtyTextField.getText());
            totalLabel.setText(productProperty.getPrice().multiply(BigDecimal.valueOf(qty)).toString() + "$");
        } catch (Exception e) {
            totalLabel.setText("#$");
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public int getShopId() {
        return shopId;
    }


    @FXML
    private void okButtonPressed() {
        Order order = new Order();
        order.setProduct(productProperty.toProduct());
        order.setCustomer(customers.get(customerChoice.getSelectionModel().getSelectedIndex()));
        order.setQuantity(Integer.parseInt(qtyTextField.getText()));
        try {
            client.getServer().createOrder(order);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dialogStage.close();
    }

    @FXML
    private void cancelButtonPressed() {
        dialogStage.close();
    }


}
