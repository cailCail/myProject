
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Inhouse;
import model.Inventory;
import model.Product;

import java.io.IOException;

/**
 * This class creates an application that manages the inventory parts and products.
 *
 * FUTURE ENHANCEMENT: I would extend the functionality of this application by having it upload or update to a data base.
 * That way when a user logs out of the program information is retained and not lost when the user exits the application.
 * RUNTIME Error: Please see javadoc notes at the top of the mainFormController.
 *
 * JavaDoc file specific index files are found at JavaDoc/index.html. Specific JavaDoc files are located in the HTML file.
 * */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm2.fxml"));
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) {

        //Test data
        Product product1 = new Product (Product.nextid++, "Fender",999.99, 8, 2, 10);
        Product product2 = new Product (Product.nextid++, "Muffler", 200.00, 5, 1, 20);
        Product product3 = new Product (Product.nextid++, "Oil Filter", 59.95, 20, 3, 50);
        Product product5 = new Product (Product.nextid++, "Tire", 75.89, 8, 2, 12);


        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product5);


        //Part test data
        Inhouse inhouse1 = new Inhouse (Inventory.nextid++, "Headlight",99.99, 8, 1, 15, 5698 );
        Inhouse inhouse2 = new Inhouse (Inventory.nextid++, "Trunk", 1999.99, 3, 1, 5, 8977);
        Inhouse inhouse3 = new Inhouse (Inventory.nextid++, "Radio", 4999.99, 8, 2, 10, 5781);
        Inhouse inhouse4 = new Inhouse (Inventory.nextid++, "Radiator", 3999.99, 2, 1, 5, 4783);

        Inventory.addPart(inhouse1);
        Inventory.addPart(inhouse2);
        Inventory.addPart(inhouse3);
        Inventory.addPart(inhouse4);

        product5.addAssociatedPart(inhouse1);
        product5.addAssociatedPart(inhouse3);

        launch(args);



    }


}



