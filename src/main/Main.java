package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

/**
 * @author Andrew Duliba : 08/09/2022 C482 SOFTWARE-1 Inventory Management System
 * **/

public class Main extends Application {

    /** Main start method
     *
     * FUTURE ENHANCEMENT: I could add html and css to the GUI to make the application more appealing to the user.
     *
     * LOGICAL CHALLENGES: The first challenge I had was getting the correct data to load based on its ordering in each constructor.
     * The second challenge I had was setting up the application from scratch, rather than having a preloaded FXML maven file.
     * The third challenge I had was setting up all the on action buttons to actually do the function they are supposed to while also giving the appropriate error dialog box.
     * I could go on listing dozens of challenges, but then these notes would become overcumbered.
     *
     * @param stage Sets the main stage for the application. **/

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MainForm.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Main method.
     *
     * Java Docs is located in the JavaDocs folder which is inside the MarchallChristianInventorySystem file.
     *
     * @param args Preloaded data for instructor to use for demonstration of programs functionality. **/

    public static void main(String[] args) {

        Part part1 = new InHouse(1, 49, 1, 99, 1,"Body", 15.00);
        Part part2 = new Outsourced(2, 49, 1, 99, "Wheel","Best Bikes Co.", 4.00);
        Part part3 = new Outsourced(3, 49, 1, 99, "Chain","Better than Best Bikes Co.", 10.00);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

        Product product1 = new Product(1, 49, 1, 99, "Sport Bike", 300.00);
        Product product2 = new Product(2, 49, 1, 99, "Children's Bike", 100.00);
        Product product3 = new Product(3, 49, 1, 99, "Adult Bike", 200.00);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        launch();
    }
}