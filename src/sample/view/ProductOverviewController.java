package sample.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import sample.entity.Product;
import sample.entity.Stock;
import sample.view.model.ProductProperty;
import sample.view.model.StockProperty;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class ProductOverviewController {

    @FXML
    private Label shopIdLabel;
    @FXML
    private TableView<ProductProperty> productTableView;
    @FXML
    private TableColumn<ProductProperty, Integer> idColumn;
    @FXML
    private TableColumn<ProductProperty, String> nameColumn;
    @FXML
    private TableColumn<ProductProperty, BigDecimal> priceColumn;
    @FXML
    private TableColumn<ProductProperty, String> descriptionColumn;
    @FXML
    private TableColumn<ProductProperty, String> categoryColumn;
    @FXML
    private ChoiceBox categoryChoice;
    @FXML
    private TableView stockTableView;
    @FXML
    private TableColumn<StockProperty, Integer> qtyColumn;
    @FXML
    private TableColumn<StockProperty, Integer> locationColumn;
    private Client client;
    private ObservableList<ProductProperty> productProperties = FXCollections.observableArrayList();
    private ObservableList<StockProperty> stockProperties = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        categoryChoice.getItems().addAll("all", "phone", "laptop");
        categoryChoice.getSelectionModel().select(0);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryNameProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        qtyColumn.setCellValueFactory(cellDate -> cellDate.getValue().quantityProperty());
        locationColumn.setCellValueFactory(cellDate -> cellDate.getValue().shopIdProperty());

        categoryChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                initData(newValue.intValue());
            }
        });
//        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
//
//        showStockDetails(null);
//
        productTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showStockDetails(newValue)
        );
    }


    private void showStockDetails(ProductProperty productProperty) {
        if (productProperty != null) {
            try {
                stockProperties.clear();
                List<Stock> stocks = client.getServer().getStock(productProperty.getId());
                if (stocks != null) {
                    for (Stock stock : stocks) {
                        stockProperties.add(new StockProperty(stock));
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            stockProperties.clear();
        }
    }

    @FXML
    private void newButtonPressed() {
//        CustomerProperty tempPerson = new CustomerProperty();
//        boolean okClicked = client.showCustomerEditDialog(tempPerson);
//
//        if (okClicked) {
//            client.getCustomerProperties().add(tempPerson);
//        }
    }

    @FXML
    private void editButtonClicked() {
//        CustomerProperty selectedPerson = customerTableView.getSelectionModel().getSelectedItem();
//        if (selectedPerson != null) {
//            boolean okClicked = client.showCustomerEditDialog(selectedPerson);
//
//            if (okClicked) {
//                showStockDetails(selectedPerson);
//            }
//        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Edit");
//            alert.setHeaderText("No Selection");
//            alert.setContentText("Please select a  in the table.");
//
//            alert.show();
//        }
    }

    @FXML
    private void deleteButtonPressed() {
//        int index = customerTableView.getSelectionModel().getSelectedIndex();
//        CustomerProperty customer = customerTableView.getItems().get(index);
//
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Delete");
//        alert.setHeaderText("conform");
//        alert.setContentText("delete " + customer.getFirstName() + " " + customer.getLastName());
//
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ButtonType.OK) {
//            customerTableView.getItems().remove(index);
//        } else {
//        }

    }

    private void initData(Integer categoryId) {
        try {
            productProperties.clear();
            if (categoryId != null && categoryId != 1 && categoryId != 2) {
                categoryId = null;
            }
            List<Product> products = this.client.getServer().getProductsByCategory(categoryId);
            if (products != null) {
                for (Product product : products) {
                    productProperties.add(new ProductProperty(product));
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setClient(Client client) {
        this.client = client;
        shopIdLabel.setText("shop id:" + client.getShopId());
        productTableView.setItems(productProperties);
        stockTableView.setItems(stockProperties);
        initData(null);
    }

    public void showShopDialog(ProductProperty productProperty) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("buyDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create Order");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(client.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            buyDialogController buyDialogController = loader.getController();
            buyDialogController.setDialogStage(dialogStage);
            buyDialogController.setClient(client, productProperty);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void purchasePressed(ActionEvent actionEvent) {
        ProductProperty selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            showShopDialog(selectedProduct);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Purchase");
            alert.setHeaderText("No Selection");
            alert.setContentText("Please select a in the table.");
            alert.show();
        }
    }

    public void stockClicked(ActionEvent actionEvent) {

    }

    public void customerClicked(ActionEvent actionEvent) {
        client.showCustomerOverview();
    }
}
