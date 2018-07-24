package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.util.DateUtil;
import sample.view.model.CustomerProperty;

public class CustomerEditDialogController {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField birthdayTextField;


    private Stage dialogStage;
    private CustomerProperty customerProperty;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCustomerProperty(CustomerProperty customerProperty) {
        this.customerProperty = customerProperty;

        firstNameTextField.setText(customerProperty.getFirstName());
        lastNameTextField.setText(customerProperty.getLastName());
        streetTextField.setText(customerProperty.getStreet());
        postalCodeTextField.setText(Integer.toString(customerProperty.getPostalCode()));
        cityTextField.setText(customerProperty.getCity());
        birthdayTextField.setText(DateUtil.format(customerProperty.getBirthday()));
        birthdayTextField.setPromptText("yyyy-MM-dd");
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void okButtonPressed() {
        if (isInputValid()) {
            customerProperty.setFirstName(firstNameTextField.getText());
            customerProperty.setLastName(lastNameTextField.getText());
            customerProperty.setStreet(streetTextField.getText());
            customerProperty.setPostalCode(Integer.parseInt(postalCodeTextField.getText()));
            customerProperty.setCity(cityTextField.getText());
            customerProperty.setBirthday(DateUtil.parse(birthdayTextField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void cancelButtonPressed() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameTextField.getText() == null || firstNameTextField.getText().length() == 0) {
            errorMessage += "No valid first name.\n";
        }

        if (lastNameTextField.getText() == null || lastNameTextField.getText().length() == 0) {
            errorMessage += "No valid last name.\n";
        }

        if (streetTextField.getText() == null || streetTextField.getText().length() == 0) {
            errorMessage += "No valid street.\n";
        }

        if (postalCodeTextField.getText() == null || postalCodeTextField.getText().length() == 0) {
            errorMessage += "No valid postal code\n";
        } else {
            try {
                Integer.parseInt(postalCodeTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)\n";
            }
        }

        if (cityTextField.getText() == null || cityTextField.getText().length() == 0) {
            errorMessage += "No valid city.\n";
        }

        if (birthdayTextField.getText() == null || birthdayTextField.getText().length() == 0) {
            errorMessage += "No valid birthday.\n";
        } else {
            if (!DateUtil.validDate(birthdayTextField.getText())) {
                errorMessage += "No valid birthday. Use the format yyyy-MM-dd.\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Plase correct invalid fields");
            alert.setContentText(errorMessage);
            alert.show();

            return false;
        }
    }
}
