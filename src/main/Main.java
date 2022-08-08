package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

//main start method that opens the MainFormController view
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MainForm.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }

    //temporary data for instructor to use for demonstration of programs functionality
    public static void main(String[] args) {

        Part part1 = new InHouse(1, 49, 0, 99, 1,"Body", 15.00);
        Part part2 = new InHouse(2, 49, 0, 99, 2,"Wheel", 5.00);
        Part part3 = new InHouse(3, 49, 0, 99, 3,"Chain", 5.00);
        Part part4 = new Outsourced(4, 49, 0, 99, "Shocks","Best Bikes Co.", 4.00);
        Part part5 = new Outsourced(5, 49, 0, 99, "Spokes","Better than Best Bikes Co.", 10.00);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);

        Product product1 = new Product(1, 49, 0, 99, "Sport Bike", 300.00);
        Product product2 = new Product(2, 49, 0, 99, "Children's Bike", 100.00);
        Product product3 = new Product(3, 49, 0, 99, "Adult Bike", 200.00);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        launch();
    }
}