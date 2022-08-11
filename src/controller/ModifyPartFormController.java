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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

/** @author Andrew Duliba : 08/09/2022 C482 SOFTWARE-1 Inventory Management System. **/

public class ModifyPartFormController implements Initializable {

    //VARIABLES, initialized for use in methods.

    /** Stage variable, type Stage.
     *
     * Sets the stage for the scene. **/
    Stage stage;

    /** Scene variables, type Parent.
     *
     * Sets the scene for the user to view. **/
    Parent scene;

    /** Part selectedPart variable **/
    private Part selectedPart;

    //FXML VARIABLES

    //initialized and mapped to their appropriate classes for loading data.
    /** Radio buttons toggle group. **/
    @FXML
    public ToggleGroup radioBtnTgl;

    /** In-House radio button and Outsourced radio buttons. **/
    @FXML
    public RadioButton inHouseRadioBtn, outsourcedRadioBtn;

    /** Part ID, Name, Inventory, Price, Max, Min, and Argument Text Fields. **/
    @FXML
    public TextField idTxt, nameTxt, invTxt, priceTxt, maxTxt, minTxt, argTxt;

    /** In-House and Company Name argument Label. **/
    @FXML
    public Label argLabel;

    /** Part Save and cancel buttons. **/
    @FXML
    public Button saveBtn, cancelBtn;

    //METHODS

    //use of DisplayMainForm(event) method allows for the ability to open the MainFormController view.
    /** Returns the user to the Main Form View.
     *
     * @param event Main Form displayed.
     * @throws IOException **/
    public void DisplayMainForm(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));//sets up the scene
        stage.show();
    }

    //alerts user if inventory is outside the scope of the min/max.
    /** Tells the user that the inventory level must be between the min and max values.
     *
     * @param min Minimum value for part.
     * @param max Maximum value for part.
     * @param stock Inventory level for the part.
     * @return Boolean telling user if the inventory value is valid or not. **/
    private boolean inventoryIsValid(int min, int max, int stock) {
        boolean valid = true;

        if (stock > max || stock < min) {
            valid = false;
            alertDisplays(2);
        } return valid;
    }

    //alerts user if their min value is greater than their max value.
    /** Tells the user if the min value is invalid.
     *
     * @param min Minimum value for part.
     * @param max Maximum value for part.
     * @return Boolean telling user if the min is valid or not. **/
    private boolean minIsValid(int min, int max) {

        boolean valid = true;

        if (min <= 0 || min >= max) {
            valid = false;
            alertDisplays(3);
        }
        return valid;
    }

    //Allows me to use alertDisplays(case) on previous onaction events. Gives 4 different cases for user errors and instructs them on the error.
    /** Initializes all error messages.
     *
     * Based on the users incorrect input, an error message will be displayed instructing the user instead of crashing program.
     *
     * @param alertType Alert message. **/
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

    /** Initializes and populates part and product tables with values from selected part.
     *
     * The selected part and its values that were clicked on in the MainFormController view carry over to this view and populates the text fields to be modified and saved.
     *
     * @param url
     * @param resourceBundle .**/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedPart = MainFormController.getSelectedPart();

        if (selectedPart instanceof InHouse) {
            inHouseRadioBtn.setSelected(true);
            argLabel.setText("MachineID");
            argTxt.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        if (selectedPart instanceof Outsourced) {
            outsourcedRadioBtn.setSelected(true);
            argLabel.setText("Company Name");
            argTxt.setText(((Outsourced) selectedPart).getCompanyName());
        }

        idTxt.setText(String.valueOf(selectedPart.getId()));
        nameTxt.setText(String.valueOf(selectedPart.getName()));
        invTxt.setText(String.valueOf(selectedPart.getStock()));
        priceTxt.setText(String.valueOf(selectedPart.getPrice()));
        maxTxt.setText(String.valueOf(selectedPart.getMax()));
        minTxt.setText(String.valueOf(selectedPart.getMin()));

    }

    //ONACTION EVENTS

    /** loads all the text fields with the selected parts values.
     *
     * Part can be modified based on the radio button that is clicked and adds the part to its appropriate class.
     *
     * After modifying the parts preloaded values and the save button is clicked, the users inputted data is saved, but only if there is no error.
     *
     * After part has been saved, the MainFormController view is displayed with newly saved part.
     *
     * RUNTIME ERROR: If the text fields are empty or incorrect values are added, the application will display the appropriate error message with instructions on how to fix the error.
     *
     * @param event Save button clicked.
     * @throws IOException**/
    @FXML
    void onActionSaveBtn(ActionEvent event) throws IOException {

        try {
            int id = selectedPart.getId();
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
                        Inventory.addPart(inHousePart);
                        partAdded = true;
                    } catch (Exception e) {
                        alertDisplays(4);
                    }
                }

                if (outsourcedRadioBtn.isSelected()) {
                    String companyName = argTxt.getText();
                    Outsourced outsourcedPart = new Outsourced(id, inv, min, max, name, companyName, price);
                    Inventory.addPart(outsourcedPart);
                    partAdded = true;
                }
            }

            if (partAdded) {
                Inventory.deletePart(selectedPart);
                DisplayMainForm(event);
            }
        }

        catch(Exception e)
        {
            alertDisplays(1);
        }
    }

    /** Returns the user to the MainFormController view .
     *
     * When the cancel button is clicked it opens the MainFormController view.
     *
     * @param event Cancel button clicked.
     * @throws IOException **/
    @FXML
    void onActionCancelBtn(ActionEvent event) throws IOException {
        DisplayMainForm(event);
    }

    /** When the In-house radio button is clicked, the machine ID label is displayed so the user can input and save data pertaining to machineId variable.
     *
     * @param event In-House radio button clicked. **/
    @FXML
    void onActionInHouseRadioBtnSelected(ActionEvent event) {
        if (inHouseRadioBtn.isSelected()) {
            argLabel.setText("Machine ID");
        }
    }

    /** When the Outsourced radio button is clicked, the company name label is displayed so the user can input and save data pertaining to companyName variable.
     *
     * @param event Outsourced radio button clicked. **/
    @FXML
    void onActionOutsourcedRadioBtnSelected(ActionEvent event) {
        if (outsourcedRadioBtn.isSelected()) {
            argLabel.setText("Company Name");
        }
    }

}
