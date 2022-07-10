
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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @ author
 * Sonya Cail
 *
 *
 */

/** This class is the main screen of the application.
 *
 * A runtime error occurs if no part is selected when the user clicks the modify button. The error occurs due to a null pointer.
 * This runtime error is prevented by using an alert that requires user to select a part to modify. This can be found in the
 * ModifyOnActionParts of this class.
 * */
public class MainFormController<newPart> implements Initializable {

    /** Build stage and scene. */
    Stage stage;
    Parent scene;

    /** Search text field for part. */
    public TextField searchPartQuery;

    /** Search text field for product. */
    public TextField searchProductQuery;

    /** Table view part. */
    public TableView<Part> MainFormParts;

    /** Table view product. */
    public TableView<Product> MainFormProducts;

    /** The part main menu id column. */
    @FXML
    private TableColumn<Part, Integer> MainMenuPartsPartID;

    /** The part main menu name column. */
    @FXML
    private TableColumn<Part, String> MainMenuPartsPartName;

    /** The part main menu inventory column. */
    @FXML
    private TableColumn<Part, Integer> MainMenuPartsInventory;

    /** The part main menu price column. */
    @FXML
    private TableColumn<Part, Double> MainMenuPartsPrice;

    /** The product main menu product ID column. */
    @FXML
    private TableColumn<Part, Integer> MainMenuProductsID;

    /** The product main menu product name. */
    @FXML
    private TableColumn<Part, String> MainMenuProductsProductName;

    /** The product main menu product stock. */
    @FXML
    private TableColumn<Part, Integer> MainMenuProductsInventory;

    /** The product main menu product price column. */
    @FXML
    private TableColumn<Part, Double> MainMenuProductsPrice;

    /** This method loads the Add Product screen when the user clicks the add button under the product table.
     * @param actionEvent user clicks the Add button in the under the product table.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    public void AddProductMainForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("/view/AddProduct.fxml")));
        loader.load();
        Parent scene = loader.getRoot();
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method loads the Modify Products screen when the user clicks the modify button under the products table.
     *
     * The method displays an error message if product not selected before selecting the modify button.
     *
     * @param event product modify action button.
     * @throws IOException from FXMLLoader.
     * @return remains on the main page if an alert is activated so that user can correct error.
     *
     * If product not selected before user selects modify alert is activated. User alerted to select for modify.
     * */
    @FXML
    public void ModifyProductionsOnAction(ActionEvent event) throws IOException {
        Product selectedProd = MainFormProducts.getSelectionModel().getSelectedItem();
        if (selectedProd == null) {
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("Product not selected from table");
            alert3.setContentText("Please select a product from the product table before selecting modify button.");
            alert3.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("/view/modifyProductForm.fxml")));
        loader.load();
        Parent scene = loader.getRoot();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));

        stage.show();

        modifyProductFormController MPFController = loader.getController();

        MPFController.sendModifyProduct(selectedProd);
    }

    /** This method deletes a product from inventory when the user clicks the Delete button under the product table.
     *
     * @param event  user clicks the delete button under the product table.
     * @return  returns to current page due to alert activation.
     *
     * If product not selected and user selected the delete button alert activated and user remains on main menu page.
     * If product selected for delete has associated parts, alert activated and product not deleted due to associated parts.
     * If user selects delete confirmation of delete alert activated and user must confirm before deletion.
     *
     * */
    @FXML
    void DeleteProductionsOnAction(ActionEvent event) {
        Product selectedPart = MainFormProducts.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert7 = new Alert(Alert.AlertType.ERROR);
            alert7.setTitle("Alert!");
            alert7.setContentText("Product not selected for delete");
            alert7.showAndWait();
            return;
        }

        if (selectedPart.getAllAssociatedParts().size() > 0){
            Alert alert7 = new Alert(Alert.AlertType.ERROR);
            alert7.setTitle("Alert!");
            alert7.setContentText("Product has associated parts cannot delete");
            alert7.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected product. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            MainFormProducts.getItems().remove(selectedPart);
        }
    }

    /** This method loads the Modify Parts screen when the user clicks the modify button under the parts table.
     *
     * The method displays an error message if part not selected before selecting the modify button.
     *
     * @param event part modify action button.
     * @throws IOException from FXMLLoader.
     * @return remains on the main page if an alert is activated so that user can correct error.
     * */
    @FXML
    public void ModifyOnActionParts(ActionEvent event) throws IOException {
        Part selectedP = MainFormParts.getSelectionModel().getSelectedItem();
        if (selectedP == null) {
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("Part not selected from table");
            alert3.setContentText("Please select a part from the table before selecting modify.");
            alert3.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("/view/modifyPart.fxml")));
        loader.load();
        Parent scene = loader.getRoot();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();

        modifyPartController MPController = loader.getController();

        MPController.sendModify(selectedP);
    }

    /** Loads the AddPartController.
     *
     * @param event Add button action.
     * @throws IOException from FXMLLoader.
     *
     * */
    @FXML
    void ActionParts(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** This method delete the part selected by the user in the part table.
     *
     * The method displays an error message if no part is selected.
     * The method displays a confirmation dialog box before deleting the user selected part. User must confirm deletion, once
     * confirmed deletion is completed.
     *
     * @param event DeleteOnActionParts button action.
     *
     * @return  returns to current page due to alert activation.
     *
     *
     * */
    @FXML
    void DeleteOnActionParts(ActionEvent event) {

        Part selectedPart = MainFormParts.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert8 = new Alert(Alert.AlertType.ERROR);
            alert8.setTitle("Alert!");
            alert8.setContentText("Part not selected for delete");
            alert8.showAndWait();
            return;
        }

        else {
            Alert alert5 = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected part. Do you want to continue?");
            Optional<ButtonType> result = alert5.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
            {

                MainFormParts.getItems().remove(selectedPart);
            }
        }
    }

    /**
     * Exits the program.
     *
     * @param event Exits button action.
     * Confirmation alert for user to exit program.
     *
     * */
    @FXML
    void ExitPartsOnAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        System.exit(0);
    }

    /**
     * This method performs a search based on the user's input value in part search text field and refreshes the parts table view based on search results.
     *
     * Parts can be searched by name or ID.
     *
     * @param actionEvent onActionSearchTxtPart by button action.
     *
     * If user searched a name or ID that is not found alert is activated and an error dialog box is displayed.
     * If text box is searched and it is empty all items in parts are displayed.
     * */
    public void onActionSearchTxtPart(ActionEvent actionEvent) {
        String partQ = searchPartQuery.getText();

        ObservableList<Part> searchPart = Inventory.lookupPart(partQ);

        if (searchPart.size() == 0) {
            try {
                int id = Integer.parseInt(partQ);
                Part p = Inventory.lookupPart(id);
                if (p != null)
                    searchPart.add(p);
            } catch (NumberFormatException e) {
            }
        }

        if (searchPart.size() > 0) {
            MainFormParts.setItems(searchPart);
        } else {
            Alert alert6 = new Alert (Alert.AlertType.ERROR);
            alert6.setTitle("Not Found");
            alert6.setContentText("Item or ID not found in search");
            alert6.showAndWait();
        }
        searchPartQuery.setText("");
    }

    /**
     * This method performs a search based on user input value in product search text field and refreshes the product table view based on search results.
     *
     * Products can be searched by name or ID.
     *
     * @param actionEvent onActionSearchTxtProduct by button action.
     *
     * If user searched a name or ID that is not found alert is activated and an error dialog box is displayed.
     * If text box is searched and it is empty all items in product menu are displayed.
     * */
    public void onActionSearchTxtProduct(ActionEvent actionEvent) {
        // public void onActionSearchTxtProduct  (ActionEvent actionEvent){
        String prodP = searchProductQuery.getText();

        ObservableList<Product> searchProduct = Inventory.lookupProduct(prodP);

        if (searchProduct.size() == 0) {
            try {
                int id = Integer.parseInt(prodP);
                Product prod = Inventory.lookupProduct(id);
                if (prod != null)
                    searchProduct.add(prod);
            } catch (NumberFormatException e) {
            }
        }

        if (searchProduct.size() > 0) {
            MainFormProducts.setItems(searchProduct);
        } else {

            Alert alert6 = new Alert (Alert.AlertType.ERROR);
            alert6.setTitle("Not Found");
            alert6.setContentText("Item or ID not found in search");
            alert6.showAndWait();
        }
        searchProductQuery.setText("");
    }

    /** This method is to set all parts table views and products table views.
     * @param url
     * @param resourceBundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainFormParts.setItems(Inventory.getAllParts());

        MainMenuPartsPartID.setCellValueFactory(new PropertyValueFactory("id"));
        MainMenuPartsPartName.setCellValueFactory(new PropertyValueFactory("name"));
        MainMenuPartsInventory.setCellValueFactory(new PropertyValueFactory("stock"));
        MainMenuPartsPrice.setCellValueFactory(new PropertyValueFactory("price"));

        MainFormProducts.setItems(Inventory.getAllProducts());
        MainMenuProductsID.setCellValueFactory(new PropertyValueFactory("id"));
        MainMenuProductsProductName.setCellValueFactory(new PropertyValueFactory("name"));
        MainMenuProductsInventory.setCellValueFactory(new PropertyValueFactory("stock"));
        MainMenuProductsPrice.setCellValueFactory(new PropertyValueFactory("price"));

    }
}







