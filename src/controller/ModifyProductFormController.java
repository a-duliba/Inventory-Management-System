package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** @author Andrew Duliba : 08/09/2022 C482 SOFTWARE-1 Inventory Management System. **/

public class ModifyProductFormController implements Initializable {

    //VARIABLES, initialized for use in methods.

    /** Stage variable, type Stage.
     *
     * Sets the stage for the scene. **/
    Stage stage;

    /** Scene variables, type Parent.
     *
     * Sets the scene for the user to view. **/
    Parent scene;

    /** Observable List called ascParts of type Part. **/
    ObservableList<Part> ascParts = FXCollections.observableArrayList();

    /** Product selectedProduct variable **/
    Product selectedProduct;

    //FXML VARIABLES

    //initialized and mapped to their appropriate classes for loading data.
    /** Product Part and Associated Parts table views. **/
    @FXML
    public TableView<Part> allPartsTable, ascPartsTable;

    /** Part ID, Name, Inventory, Price, Max, Min, and Search Text Fields. **/
    @FXML
    public TextField idTxt, nameTxt, invTxt, priceTxt, maxTxt, minTxt, searchTxt;

    /** Product Part TableColumn Part ID and Inventory.
     *
     * Associated Part TableColumn Part ID and Inventory.
     *
     * **/
    @FXML
    public TableColumn<Part, Integer> allPartIDCol, ascPartIDCol, allPartInvCol, ascPartInvCol;

    /** Product Part TableColumn Part Name.
     *
     * Associated Part TableColumn Part Name.
     *
     * **/
    @FXML
    public TableColumn<Part, String> allPartNameCol, ascPartNameCol;

    /** Product Part TableColumn Part Price.
     *
     * Associated Part TableColumn Part Price.
     *
     * **/
    @FXML
    public TableColumn<Part, Double> allPartPriceCol, ascPartPriceCol;

    /** Part add associated, remove associated, save, and cancel buttons. **/
    @FXML
    public Button addAscPartBtn, removeAscPartBtn, saveProductBtn, cancelBtn;

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

    //Allows me to use alertDisplays(case) on previous onaction events. Gives 5 different cases for user errors and instructs them on the error.
    /** Initializes all error messages.
     *
     * Based on the users incorrect input, an error message will be displayed instructing the user instead of crashing the program.
     *
     * @param alertType Alert message. **/

    private void alertDisplays(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText("Form contains empty fields or invalid values");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Value for Inventory");
                alert.setContentText("Inventory must be a number that is equal to or in between min and max values");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Value for Min");
                alert.setContentText("Min must be a number that is greater than 0 and less than Max");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Value for MachineID");
                alert.setContentText("MachineID must be numbers only.");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Part Was Not selected");
                alert.setContentText("Please select a part.");
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

        selectedProduct = MainFormController.getSelectedProduct();
        ascParts = selectedProduct.getAllAssociatedParts();

        allPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        allPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsTable.setItems(Inventory.getAllParts());

        ascPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ascPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ascPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ascPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ascPartsTable.setItems(ascParts);

        idTxt.setText(String.valueOf(selectedProduct.getId()));
        nameTxt.setText(selectedProduct.getName());
        invTxt.setText(String.valueOf(selectedProduct.getStock()));
        priceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        maxTxt.setText(String.valueOf(selectedProduct.getMax()));
        minTxt.setText(String.valueOf(selectedProduct.getMin()));
    }

    //ONACTION EVENTS

    /** Search bar for the parts table.
     *
     * Searches for an ID that is entered with numbers, If numbers are not used, searches for letter that are entered that match Product name values.
     *
     * @param event Search Bar clicked and data inputted. **/
    @FXML
    public void onActionSearchProducts(ActionEvent event) {

        try {
            int partId = Integer.parseInt(searchTxt.getText());
            allPartsTable.getSelectionModel().select(Inventory.lookupPart(partId));
        } catch (Exception e) {
            String searched = searchTxt.getText();
            ObservableList<Part> part = Inventory.lookupPart(searched);
            allPartsTable.setItems(part);
        }
    }

    /** Adds a part to the associated parts table.
     *
     * When the add button is clicked, the selected product from addProducts table will be added to the associated parts table
     *
     * RUNTIME ERROR: If product was not selected, the application will display the appropriate error message with instructions on how to fix the error.
     *
     * @param event Add button clicked. **/
    @FXML
    void onActionAddProductBtn(ActionEvent event) {

        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();//

        if (selectedPart == null) {
            alertDisplays(5);
        } else {
            ascParts.add(selectedPart);
            ascPartsTable.setItems(ascParts);
        }
    }

    /** When clicked, the users inputted data will be saved, but only if there is no error.
     *
     * After Product has been saved, the MainFormController view is displayed with newly saved Product.
     *
     * RUNTIME ERROR: If the text fields are empty or incorrect values are added, the application will display the appropriate error message with instructions on how to fix the error.
     *
     * @param event Save button clicked. **/
    @FXML
    void onActionSaveProductBtn(ActionEvent event) throws IOException {

        try {
            int id = selectedProduct.getId();
            int inv = Integer.parseInt(invTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            String name = nameTxt.getText();
            Double price = Double.valueOf(priceTxt.getText());
            Boolean productAdded = false;

            if (minIsValid(min, max) && inventoryIsValid(min, max, inv)) {

                Product product = new Product(id, inv, min, max, name, price);

                selectedProduct.getId();

                for (Part part : ascParts) {
                    product.addAssociatedPart(part);
                }
                Inventory.addProduct(product);
                productAdded = true;
            }

            if (productAdded) {
                Inventory.deleteProduct(selectedProduct);
                DisplayMainForm(event);
            }

        } catch(Exception e) {
            alertDisplays(1);
        }
    }

    /** When clicked, the users selected part will be deleted, but only if a part is selected
     *
     * When the part is selected and the remove button is clicked, a dialog box will display confirming if the user wants to remove the associated part.
     *
     * RUNTIME ERROR: If part was not selected, the application will display the appropriate error message with instructions on how to fix the error.
     *
     * @param event Remove Associated Part button clicked. **/
    @FXML
    void onActionRemoveAscPart(ActionEvent event) {

        Part selectedPart = ascPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            alertDisplays(5);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Part");
            alert.setContentText("Are you sure you want to remove this associated part?");
            Optional<ButtonType> answer = alert.showAndWait();

            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                ascParts.remove(selectedPart);
                ascPartsTable.setItems(ascParts);
            }
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

}
