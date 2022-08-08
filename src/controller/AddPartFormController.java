package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddPartFormController implements Initializable {

    //VARIABLES, initialized for use in methods.

    Stage stage;
    Parent scene;

    //FXML VARIABLES

    //initialized and mapped to their appropriate classes for loading data.
    @FXML
    public ToggleGroup radioBtnTgl;
    @FXML
    public RadioButton inHouseRadioBtn, outsourcedRadioBtn;
    @FXML
    public TextField idTxt, nameTxt, invTxt, priceTxt, maxTxt, minTxt, argTxt;
    @FXML
    public Label argLabel;
    @FXML
    public Button saveBtn, cancelBtn;

    //METHODS

    //use of DisplayMainForm(event) method allows for the ability to open the MainFormController view.
    public void DisplayMainForm(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));//sets up the scene
        stage.show();
    }
    //alerts user if inventory is outside the scope of the min/max.
    private boolean inventoryIsValid(int min, int max, int stock) {
        boolean valid = true;

        if (stock > max || stock < min) {
            valid = false;
            alertDisplays(2);
        } return valid;
    }

    //alerts user if their min value is greater than their max value.
    private boolean minIsValid(int min, int max) {

        boolean valid = true;

        if (min <= 0 || min >= max) {
            valid = false;
            alertDisplays(3);
        }
        return valid;
    }
    //Allows me to use alertDisplays(case) on previous onaction events. Gives 4 different cases for user errors and instructs them on the error.
    private void alertDisplays(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part");
                alert.setContentText("Form contains empty fields or invalid values");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Value for Inventory");
                alert.setContentText("Inventory value must with the scope of the min and max values");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Value for Min");
                alert.setContentText("Min value must be greater than 0 and less than the Max value");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Value for MachineID");
                alert.setContentText("MachineID must only be numeric values.");
                alert.showAndWait();
                break;
        }
    }

    //Initializes data.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //ONACTION EVENTS

    //When clicked, saves users inputted data, but only if there is no error. Creates part based on radio button clicked and adds to appropriate class. After saved, MainFormController view displays with newly saved part.
    @FXML
    void onActionSaveBtn(ActionEvent event) throws IOException {

        try {
            int id = 0;
            int inv = Integer.parseInt(invTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            String name = nameTxt.getText();
            Double price = Double.valueOf(priceTxt.getText());
            Boolean partAdded = false;

            if (minIsValid(min, max) && inventoryIsValid(min, max, inv)) {

                if (inHouseRadioBtn.isSelected()) {
                    try {
                        int machineId = Integer.parseInt(argTxt.getText());
                        InHouse inHousePart = new InHouse(id, inv, min, max, machineId, name, price);
                        inHousePart.setId(Inventory.getTempPartId());
                        Inventory.addPart(inHousePart);
                        partAdded = true;
                    }   catch (Exception e) {
                            alertDisplays(4);
                    }
                }

                if (outsourcedRadioBtn.isSelected()) {
                String companyName = argTxt.getText();
                Outsourced outsourcedPart = new Outsourced(id, inv, min, max, name, companyName, price);
                outsourcedPart.setId(Inventory.getTempPartId());
                Inventory.addPart(outsourcedPart);
                partAdded = true;
                }
            }
            if (partAdded) {
                DisplayMainForm(event);
            }
        }
        catch(Exception e) {
            alertDisplays(1);
        }
}

    //When the cancel button is clicked it opens the MainFormController view.
    @FXML
    void onActionCancelBtn(ActionEvent event) throws IOException {
        DisplayMainForm(event);
    }

    //When the inhouse radio button is clicked machine ID label is displayed where machineId class data can be entered and saved.
    @FXML
    void onActionInHouseRadioBtnSelected(ActionEvent event) {
        if (inHouseRadioBtn.isSelected()) {
            argLabel.setText("Machine ID");
        }
    }

    //When the outsourced radio button is clicked company name label is displayed where companyName class data can be entered and saved.
    @FXML
    void onActionOutsourcedRadioBtnSelected(ActionEvent event) {
        if (outsourcedRadioBtn.isSelected()) {
            argLabel.setText("Company Name");
        }
    }

}