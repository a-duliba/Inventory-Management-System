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

public class MainFormController implements Initializable {

    //VARIABLES, initialized for use in methods.

    Stage stage;
    Parent scene;
    private static Part selectedPart;
    private static Product selectedProduct;

    //FXML VARIABLES

    //initialized and mapped to their appropriate classes for loading data.
    @FXML
    public TableView<Part> partsTable;
    @FXML
    public TableView<Product> productsTable;
    @FXML
    public TableColumn<Part, Integer> partIDCol, partInvCol;
    @FXML
    public TableColumn<Product, Integer> productIDCol, productInvCol;
    @FXML
    public TableColumn<Part, String> partNameCol;
    @FXML
    public TableColumn<Product, String> productNameCol;
    @FXML
    public TableColumn<Part, Double> partPriceCol;
    @FXML
    public TableColumn<Product, Double> productPriceCol;
    @FXML
    public TextField partSearchTxt, productSearchTxt;
    @FXML
    public Button addPartBtn, modifyPartBtn, deletePartBtn;
    @FXML
    public Button addProductBtn, modifyProductBtn, deleteProductBtn;
    @FXML
    public Button exitBtn;

    //METHODS
    public static Part getSelectedPart() {
        return selectedPart;
    }

    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    //Allows me to use alertDisplays(case) on previous onaction events. Gives 4 different cases for user errors and instructs them on the error.
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
            case 4:
                alertInfo.setTitle("Error");
                alertInfo.setHeaderText("Product was not highlighted");
                alertInfo.setContentText("You must use either only numbers or only letters when searching for products");
                alertInfo.showAndWait();
                break;
        }
    }

    //Initializes and populates part and product tables with data.
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
        //Informs and instructs the user of their error.
        if(selectedPart == null) {
            alertDisplays(3);
        }
    }

    //When the add button is clicked it opens the AddPartFormController view.
    @FXML
    void onActionAddPartBtn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //If user selects a part and clicks the modify button, the ModifyPartFormController view opens. If not, the user is given an alert and is instructed on their error.
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
        //Informs and instructs the user of their error.
        if(selectedPart == null) {
            alertDisplays(4);
        }
    }

    //When the add button is clicked it opens the AddProductFormController view.
    @FXML
    void onActionAddProductBtn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //If user selects a product and clicks the modify button, the ModifyProductFormController view opens. If not, the user is given an alert and is instructed on their error.
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

    //If user selects a product and clicks the delete button, the selected product is deleted. If not, the user is given an alert and is instructed on their error.
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

    //When the exit button is clicked, the application is closed.
    @FXML
    public void onActionExit(ActionEvent event) {
        System.exit(0);
    }

}