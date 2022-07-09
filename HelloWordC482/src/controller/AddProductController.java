
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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class initializes the AddProductController.
 * */
public class AddProductController implements Initializable {

    /**
     * Observable list for the bottom table to add associated parts.
     *
     * */
    private ObservableList<Part> bottomListAdd = FXCollections.observableArrayList();

    /**
     * set stage and scene.
     * */
    Stage stage;
    Parent scene;

    /**
     * Product object.
     * */
    Product selectedProduct;

    /**
     * Product table view for the top table.
     * */
    public TableView<Part> TableAddProductAdd;

    /**
     * Product table view for the bottom table.
     * */
    public TableView<Part> TableAddProductRemove;

    /**
     * Top table product ID.
     * */
    @FXML
    private TableColumn<Part, Integer> AddProductColumnPartID;

    /**
     * Top table product name.
     * */
    @FXML
    private TableColumn<Part, String> AddProductColumnPartName;

    /**
     * Top table product stock.
     * */
    @FXML
    private TableColumn<Part, Integer> AddProductInvColumn;

    /**
     * Top table product price.
     * */
    @FXML
    private TableColumn<Part, Double> AddProductPriceColumn;

    /**
     * Bottom table ID.
     * */
    @FXML
    private TableColumn<Part, String> AddProductRemoveColumn;

    /**
     * Bottom table name.
     * */
    @FXML
    private TableColumn<Part, String> AddProductRemovePartName;

    /**
     * Bottom table stock.
     * */
    @FXML
    private TableColumn<Part, Integer> AddProductRemoveInvColumn;

    /**
     *Bottom table price.
     * */
    @FXML
    private TableColumn<Part, Double> AddPartPriceRemoveColumn;

    /**
     * Auto generated ID number.
     * */
    @FXML
    private TextField addProductIdAutoGen;

    /**
     * Text field name.
     * */
    @FXML
    private TextField addProductName;

    /**
     * Text field stock.
     * */
    @FXML
    private TextField addProductInv;

    /**
     * Text field price.
     * */
    @FXML
    private TextField addProductPrice;

    /**
     *Text field max.
     * */
    @FXML
    private TextField addProductMax;

    /**
     *Text field min.
     * */
    @FXML
    private TextField addProductMin;

    /**
     * Text field search product.
     * */
    @FXML
    private TextField addProductSearch;


    /**
     * This is the method to add an associated part to the product when the user selects a part and then clicks the add button.
     * The part is copied from the top table into associated parts or bottom table.
     * @param actionEvent user clicks the add button.
     * @throws NumberFormatException
     * */
    public void onActionAddAddPart(ActionEvent actionEvent) {
        Part selectedPart = TableAddProductAdd.getSelectionModel().getSelectedItem();
        bottomListAdd.add(selectedPart);
    }

    /**
     * This method removes an associated part from selected product. When the user clicks remove associated part and then the save button the part is removed as an associated part.
     * The part remains in the inventory.
     * @param actionEvent user clicks the Remove Associated Parts button
     * When user clicks to the remove associated parts button a confirmation alert is activated and user must confirm if they wish to continue with deletion of the associated part.
     * */
    public void onActionRemoveAssociatedPart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected part. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Part selectedPart = TableAddProductRemove.getSelectionModel().getSelectedItem();
            bottomListAdd.remove(selectedPart);
        }
    }

    /**
     * This is the method to save a new product when the user clicks the Save button. This method will create a new product
     * when the user submits new information. On click the new information is saved to the product inventory and user is returned to the main menu screen.
     * @param actionEvent user clicks the Save button.
     * @throws IOException
     *
     * @return user remains on current screen to remediate errors.
     *
     * If product name is empty alert is activated and requires user to enter a product name. User remains on current screen.
     * If user submitted information violates min and max requirements alert is activated and user is prompted to submit a valid min and max amounts. User remains on current screen.
     * If user submitted information that violates stock requirements alert is activated and user is prompted ot submit valid stock amounts. User remains on current screen.
     *
     * */
    public void onActionSaveAddProduct(ActionEvent actionEvent) throws IOException {

        try{
            String name = addProductName.getText();
            int stock = Integer.parseInt(addProductInv.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int min = Integer.parseInt(addProductMin.getText());
            int max = Integer.parseInt(addProductMax.getText());

            if (name.isEmpty()){
                Alert alert6 = new Alert(Alert.AlertType.ERROR);
                alert6.setTitle("Not Valid");
                alert6.setContentText("Name is empty, please enter a name.");
                alert6.showAndWait();
                return;
            }

            if (stock < min || stock > max){

                Alert alert6 = new Alert(Alert.AlertType.ERROR);
                alert6.setTitle("Not Valid");
                alert6.setContentText("Inventory must be greater than min and less than max");
                alert6.showAndWait();
                return;
            }
            if(min < 0 || min > max){
                Alert alert6 = new Alert(Alert.AlertType.ERROR);
                alert6.setTitle("Not Valid");
                alert6.setContentText("Min must be greater than 0 and less than or equal to max");
                alert6.showAndWait();
                return;
            }

            Product newProduct = (new Product(Product.nextid++, name, price, stock, min, max));
            newProduct.getAllAssociatedParts().addAll(bottomListAdd);
            Inventory.addProduct(newProduct);
            Inventory.deleteProduct(selectedProduct);

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm2.fxml"));
            stage.setScene(new Scene(root));
            stage.show();

        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each text field");
            alert.showAndWait();
        }
    }

    /**
     * This is the method to return to the main screen when the user clicks the cancel button.
     * Information submitted is not saved.
     * @param actionEvent user clicks the cancel button
     * @throws Exception
     * */
    public void CancelButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm2.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This is the method to search products in the top table. If user submits an empty text field search returns all products in inventory.
     * If user submits an ID or name (full or partial) that is in inventory that item is returned in the table.
     * If user submits an ID or name of a product that is not in inventory an alert is activated.
     * @param actionEvent user clicks the search button
     * */
    public void onActionProductSearchTxt(ActionEvent actionEvent) {
        String partQ = addProductSearch.getText();

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
            TableAddProductAdd.setItems(searchPart);
        }
        else {
            Alert alert6 = new Alert(Alert.AlertType.ERROR);
            alert6.setTitle("Not Found");
            alert6.setContentText("Item or ID not found in search");
            alert6.showAndWait();
        }
        addProductSearch.setText("");
    }

    /**
     * Bottom table table view.
     * */
    public void TableAddProductRemove(SortEvent<TableView> tableViewSortEvent) {

    }
    /**
     * This method sends the products to the modifyProductFormController.
     * @param selectedPro gets the selected products.
     *
     * */
    public void sendModifyProduct(Product selectedPro) {
        selectedProduct = selectedPro;

        addProductIdAutoGen.setText(String.valueOf(selectedProduct.getId()));
        addProductName.setText(selectedProduct.getName());
        addProductInv.setText(String.valueOf(selectedProduct.getStock()));
        addProductPrice.setText(String.valueOf(selectedProduct.getPrice()));
        addProductMax.setText(String.valueOf(selectedProduct.getMax()));
        addProductMin.setText(String.valueOf(selectedProduct.getMin()));

        bottomListAdd.addAll(selectedProduct.getAllAssociatedParts());

    }
    /**
     * This method is to set the top parts table view and the bottom table associated parts table view.
     * @param url
     * @param resourceBundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TableAddProductRemove.setItems(bottomListAdd);

        TableAddProductAdd.setItems(Inventory.getAllParts());
        AddProductColumnPartID.setCellValueFactory(new PropertyValueFactory("id"));
        AddProductColumnPartName.setCellValueFactory(new PropertyValueFactory("name"));
        AddProductInvColumn.setCellValueFactory(new PropertyValueFactory("stock"));
        AddProductPriceColumn.setCellValueFactory(new PropertyValueFactory("price"));

        AddProductRemoveColumn.setCellValueFactory(new PropertyValueFactory("id"));
        AddProductRemovePartName.setCellValueFactory(new PropertyValueFactory("name"));
        AddProductRemoveInvColumn.setCellValueFactory(new PropertyValueFactory("stock"));
        AddPartPriceRemoveColumn.setCellValueFactory(new PropertyValueFactory("price"));

    }


}


