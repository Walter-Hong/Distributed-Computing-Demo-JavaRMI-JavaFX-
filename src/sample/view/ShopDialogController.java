package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ShopDialogController {

    @FXML
    private TextField ShopIdTextField;
    private Stage dialogStage;
    private int shopId;

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
        if (ShopIdTextField.getText().isEmpty()) {
            ShopIdTextField.setText("0");
        }
        shopId = Integer.parseInt(ShopIdTextField.getText());
        dialogStage.close();
    }

    @FXML
    private void cancelButtonPressed() {
        dialogStage.close();
    }
}
