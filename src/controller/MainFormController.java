package controller;

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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** @author Andrew Duliba : 08/09/2022 C482 SOFTWARE-1 Inventory Management System. **/

public class MainFormController implements Initializable {

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
    private static Part selectedPart;

    /** Product selectedProduct variable **/
    private static Product selectedProduct;

    //FXML VARIABLES

    //initialized and mapped to their appropriate classes for loading data.

    /** Part table view **/
    @FXML
    public TableView<Part> partsTable;

    /** Products table view. **/
    @FXML
    public TableView<Product> productsTable;

    /** Parts ID and Inventory table columns. **/
    @FXML
    public TableColumn<Part, Integer> partIDCol, partInvCol;

    /** Products ID and Inventory table columns. **/
    @FXML
    public TableColumn<Product, Integer> productIDCol, productInvCol;

    /** Parts Name table columns. **/
    @FXML
    public TableColumn<Part, String> partNameCol;

    /** Products Name table columns. **/
    @FXML
    public TableColumn<Product, String> productNameCol;

    /** Parts Price table columns. **/
    @FXML
    public TableColumn<Part, Double> partPriceCol;

    /** Products Price table columns. **/
    @FXML
    public TableColumn<Product, Double> productPriceCol;

    /** Parts and Products search bar TextFields. **/
    @FXML
    public TextField partSearchTxt, productSearchTxt;

    /** Add, Modify, and Delete buttons for Parts Table. **/
    @FXML
    public Button addPartBtn, modifyPartBtn, deletePartBtn;

    /** Add, Modify, and Delete buttons for Products Table. **/
    @FXML
    public Button addProductBtn, modifyProductBtn, deleteProductBtn;

    /** Exit button. **/
    @FXML
    public Button exitBtn;

    //METHODS

    /** Gets selected part.
     *
     * Grabs the part that is selected and returns it.
     *
     * @return SelectedPart. **/
    public static Part getSelectedPart() {
        return selectedPart;
    }

    /** Gets selected product.
     *
     * Grabs the product that is selected and returns it.
     *
     * @return SelectedProduct. **/
    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    //Allows me to use alertDisplays(case) on previous onaction events. Gives 4 different cases for user errors and instructs them on the error.
    /** Initializes all error messages.
     *
     * Based on the users incorrect input, an error message will be displayed instructing the user instead of crashing the program.
     *
     * @param alertType Alert message. **/
    private void alertDisplays(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 0:
                alert.setTitle("Error");
                alert.setHeaderText("Part was not selected");
                alert.showAndWait();
                break;
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Product was not selected");
                alert.showAndWait();
                break;
            case 2:
                alertInfo.setTitle("Error");
                alertInfo.setHeaderText("Other parts are associated with this Product");
                alertInfo.setContentText("You must delete all parts associated with this product before deletion can be successful");
                alertInfo.showAndWait();
                break;
            case 3:
                alertInfo.setTitle("Error");
                alertInfo.setHeaderText("Part was not highlighted");
                alertInfo.setContentText("You must use either only numbers or only letters when searching for parts");
                alertInfo.showAndWait();
                break;
        }
    }

    /** Initializes and populates part and product tables with preloaded values.
     *
     * @param url
     * @param resourceBundle .**/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    //ONACTION EVENTS

    /** Search bar for the parts table.
     *
     * Searches for an ID that is entered with numbers, If numbers are not used, searches for letter that are entered that match Product name values.
     *
     * @param event Search Bar clicked and data inputted. **/
    @FXML
    public void onActionSearchParts(ActionEvent event) {

        //Searches for Id that is entered with numbers.
        try {
            int partId = Integer.parseInt(partSearchTxt.getText());
            partsTable.getSelectionModel().select(Inventory.lookupPart(partId));
        }
        //If numbers not used, searches for Name that is entered with letters.
        catch (Exception e) {
            String searched = partSearchTxt.getText();
            ObservableList<Part> part = Inventory.lookupPart(searched);
            partsTable.setItems(part);
        }
    }

    /** When clicked AddPartFormController view is opened.
     *
     * @param event Add Part Form displayed.
     * @throws IOException **/
    @FXML
    void onActionAddPartBtn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** When clicked ModifyPartFormController view is opened, but only if a part is selected.
     *
     * RUNTIME ERROR: If a part was not selected, the application will display the appropriate error message with instructions on how to fix the error.
     *
     * @param event Add Part Form displayed.
     * @throws IOException **/
    @FXML
    void onActionModifyPartBtn(ActionEvent event) throws IOException {

        selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            alertDisplays(0);
        } else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyPartForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    //If user selects a part and clicks the delete button, the selected part is deleted. If not, the user is given an alert and is instructed on their error.
    /** When clicked, the users selected part will be deleted, but only if a part is selected
     *
     * When the part is selected and the delete button is clicked, a dialog box will display confirming if the user wants to delete the part.
     *
     * RUNTIME ERROR: If a part was not selected, the application will display the appropriate error message with instructions on how to fix the error.
     *
     * @param event Delete button clicked. **/
    @FXML
    void onActionDeletePartBtn(ActionEvent event) {

        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            alertDisplays(0);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Parts");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> answer = alert.showAndWait();

            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }

    /** Search bar for the products table.
     *
     * Searches for an ID that is entered with numbers, If numbers are not used, searches for letter that are entered that match Product name values.
     *
     * @param event Search Bar clicked and data inputted. **/
    public void onActionSearchProducts(ActionEvent event) {

        //Searches for Id that is entered with numbers.
        try {
            int productId = Integer.parseInt(productSearchTxt.getText());
            productsTable.getSelectionModel().select(Inventory.lookupProduct(productId));
        }
        //If numbers not used, searches for Name that is entered with letters.
        catch (Exception e) {
            String searched = productSearchTxt.getText();
            ObservableList<Product> product = Inventory.lookupProduct(searched);
            productsTable.setItems(product);
        }
    }

    /** When clicked AddProductFormController view is opened.
     *
     * @param event Add Product Form displayed.
     * @throws IOException **/
    @FXML
    void onActionAddProductBtn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** When clicked ModifyProductFormController view is opened, but only if a part is selected.
     *
     * RUNTIME ERROR: If a product was not selected, the application will display the appropriate error message with instructions on how to fix the error.
     *
     * @param event Add Product Form displayed.
     * @throws IOException **/
    @FXML
    void onActionModifyProductBtn(ActionEvent event) throws IOException {

        selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            alertDisplays(1);
        } else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** When clicked, the users selected product will be deleted, but only if a product is selected
     *
     * When the product is selected and the delete button is clicked, a dialog box will display confirming if the user wants to delete the product.
     *
     * RUNTIME ERROR: If a product was not selected, the application will display the appropriate error message with instructions on how to fix the error.
     *
     * @param event Delete button clicked. **/
    @FXML
    void onActionDeleteProductBtn(ActionEvent event) {

        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            alertDisplays(1);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Products");
            alert.setContentText("Are you sure you want to delete this product?");
            Optional<ButtonType> answer = alert.showAndWait();

            if (answer.isPresent() && answer.get() == ButtonType.OK) {

                ObservableList<Part> associatedPart = selectedProduct.getAllAssociatedParts();

                if (associatedPart.size() >= 1) {
                    alertDisplays(2);
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }

    }

    /** Exists the application
     *
     * When the exit button is clicked, the application will close.
     *
     * @param event exist button clicked. **/
    @FXML
    public void onActionExit(ActionEvent event) {
        System.exit(0);
    }

}