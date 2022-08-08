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

public class AddProductFormController implements Initializable {

    //VARIABLES, initialized for use in methods.

    Stage stage;
    Parent scene;
    ObservableList<Part> ascParts = FXCollections.observableArrayList();

    //FXML VARIABLES

    //initialized and mapped to their appropriate classes for loading data.
    @FXML
    public TableView<Part> addPartsTable, ascPartsTable;
    @FXML
    public TextField idTxt, nameTxt, invTxt, priceTxt, maxTxt, minTxt, searchTxt;
    @FXML
    public TableColumn<Part, Integer> allPartIDCol, ascPartIDCol, allPartInvCol, ascPartInvCol;
    @FXML
    public TableColumn<Part, String> allPartNameCol, ascPartNameCol;
    @FXML
    public TableColumn<Part, Double> allPartPriceCol, ascPartPriceCol;
    @FXML
    public Button addAscPartBtn, removeAscPartBtn, saveProductBtn, cancelBtn;

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
        }
        return valid;
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

    //Allows me to use alertDisplays(case) on previous onaction events. Gives 5 different cases for user errors and instructs them on the error.
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

    //Initializes and populates part and product tables with data.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addPartsTable.setItems(Inventory.getAllParts());

        allPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        allPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        ascPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ascPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ascPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ascPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    //ONACTION EVENTS

    //Searches for Id that is entered with numbers, If numbers not used, searches for Name that is entered with letters.
    @FXML
    public void onActionSearchProducts(ActionEvent event) {

        try {
            int partId = Integer.parseInt(searchTxt.getText());
            addPartsTable.getSelectionModel().select(Inventory.lookupPart(partId));
        } catch (Exception e) {
            String searched = searchTxt.getText();
            ObservableList<Part> part = Inventory.lookupPart(searched);
            addPartsTable.setItems(part);
        }
    }

    //When add button clicked, the selected product from addProducts table will be added to the ascProducts table
    @FXML
    void onActionAddProductBtn(ActionEvent event) {

        Part selectedPart = addPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            alertDisplays(5);
        } else {
            ascParts.add(selectedPart);
            ascPartsTable.setItems(ascParts);
        }
    }

    //When clicked, saves users inputted data, but only if there is no error. After saved, MainFormController view displays with newly saved product.
    @FXML
    void onActionSaveProductBtn(ActionEvent event) throws IOException {

        try {
            int id = 0;
            int inv = Integer.parseInt(invTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            String name = nameTxt.getText();
            Double price = Double.valueOf(priceTxt.getText());
            if (minIsValid(min, max) && inventoryIsValid(min, max, inv)) {
                Product product = new Product(id, inv, min, max, name, price);
                for (Part part : ascParts) {
                    product.addAssociatedPart(part);
                }
                product.setId(Inventory.getTempProductId());
                Inventory.addProduct(product);
                DisplayMainForm(event);
            }
        } catch(Exception e) {
            alertDisplays(1);
        }
    }

    //If user selects a part and clicks the delete button, the selected part is deleted. If not, the user is given an alert and is instructed on their error.
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

    //When the cancel button is clicked it opens the MainFormController view.
    @FXML
    void onActionCancelBtn(ActionEvent event) throws IOException {
        DisplayMainForm(event);
    }

}

