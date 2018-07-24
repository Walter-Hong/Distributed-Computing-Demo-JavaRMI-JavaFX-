package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.api.ShopService;
import sample.config.AppConfig;
import sample.view.CustomerOverviewController;
import sample.view.ProductOverviewController;
import sample.view.ShopDialogController;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Client extends Application {
    private ShopService server;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Integer shopId;


    public ShopService getServer() {
        if (server == null) {
            try {
                server = (ShopService) Naming.lookup(AppConfig.rmiName);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return server;
    }

    public Client() {
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Customer Manager");

        showShopDialog();
        initRootLayout();
        showProductOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Client.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showCustomerOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Client.class.getResource("view/CustomerOverview.fxml"));
            AnchorPane personOverview = loader.load();

            rootLayout.setCenter(personOverview);

            CustomerOverviewController customerController = loader.getController();
            customerController.setClient(this);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showProductOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Client.class.getResource("view/ProductOverview.fxml"));
            AnchorPane anchorPane = loader.load();

            rootLayout.setCenter(anchorPane);

            ProductOverviewController productController = loader.getController();
            productController.setClient(this);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showShopDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("view/ShopDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Input Shop Id");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ShopDialogController shopDialogController = loader.getController();
            shopDialogController.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            shopId = shopDialogController.getShopId();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public int getShopId() {
        return shopId;
    }
}