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
import model.*;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class creates the modifyProductFormController.
 * */
public class modifyProductFormController implements Initializable {

    /**
     * set the stage and scene.
     * */
    Stage stage;
    Parent scene;

    /**
     * set index.
     * */
    int index;

    /**
     * set selectedProduct.
     * */
    Product selectedProduct;

    /**
     * set observable list to copy products from top parts table to associated parts table.
     * */
    private ObservableList<Part> bottomList = FXCollections.observableArrayList();

    /**
     * top table view.
     * */
    @FXML
    private TableView<Part> ModifyProduct;

    /**
     * bottom table view.
     * */
    @FXML
    private TableView<Part> addAssociatedPart;

    /**
     * top table ID.
     * */
    @FXML
    private TableColumn<Part, Integer> PartIDModifyProductAdd;

    /**
     * top table name.
     * */
    @FXML
    private TableColumn<Part, String> PartNameModifyPartAdd;

    /**
     * top table stock.
     * */
    @FXML
    private TableColumn<Part, Integer> InvLevelModifyPartAdd;

    /**
     * top table price.
     * */
    @FXML
    private TableColumn<Part, Double> CostModifyProductAdd;

    /**
     * bottom table id.
     * */
    @FXML
    private TableColumn<Part, Integer> PartIdModifyProductRemove;

    /**
     * bottom table name.
     * */
    @FXML
    private TableColumn<Part, String> PartNameModifyProductRemove;

    /**
     * bottom table stock.
     * */
    @FXML
    private TableColumn<Part, Integer> InvModifyProductRemove;

    /**
     * bottom table price.
     * */
    @FXML
    private TableColumn<Part, Double> PriceModifyProductRemove;

    /**
     * Text fields auto generated id.
     * */
    @FXML
    private TextField modifyProductAutoGen;

    /**
     * Text field name.
     * */
    @FXML
    private TextField modifyProductName;

    /**
     * Text field stock.
     * */
    @FXML
    private TextField modifyProductInv;

    /**
     * Text field price.
     * */
    @FXML
    private TextField modifyProductPrice;

    /**
     * Text field min.
     * */
    @FXML
    private TextField modifyProductMin;

    /**
     * Text field max.
     * */
    @FXML
    private TextField modifyProductMax;

    /**
     * Text field for search.
     * */
    public TextField modifyProductSearchTxt;

    /**
     * This is the method to add an associated part to the product being modified when the user clicks the Add button.
     * A part is taken from the inventory in the top table and copies the part to the associated parts table view.
     * @param actionEvent user clicks the Add button.
     * */
    public void onActionAddAssociatedParts(ActionEvent actionEvent) {
        Part selectedPart = ModifyProduct.getSelectionModel().getSelectedItem();
        bottomList.add(selectedPart);
    }

    /**
     * This method will remove an associated part from the product being modified when the user clicks the Remove Associated Parts button.
     * The associated part is removed from the bottom associated parts table. The part remains in inventory.
     * @param actionEvent clicks the Removed Associated Part button.
     * */
    public void onActionRemoveAssociatedPartsModifyParts(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected part. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Part selectedPart = addAssociatedPart.getSelectionModel().getSelectedItem();
            bottomList.remove(selectedPart);
        }
    }

    /**
     *This method will modify an exiting product when the user clicks the Save button. This method updates the preexisting product using submitted information.
     * This method takes the preexisting product being modified and replaces that product with the inventory using the product ID.
     * @throws IOException
     *
     * If the user omits the name. Alert is activated and user must submit a valid name. User remains on screen until enter valid name.
     * If user does not enter a valid min and max number based on the requirements. Alert is activated. User remains on current screen until valid min and max are entered.
     * If user submits invalid information. Alert is activated and user remains on current screen to repair invalid fields.
     *
     * */
    public void onActionSaveModifyParts(ActionEvent actionEvent) throws IOException {
        try{

            int id = Integer.parseInt(modifyProductAutoGen.getText());
            String name = modifyProductName.getText();
            int stock = Integer.parseInt(modifyProductInv.getText());
            double price = Double.parseDouble(modifyProductPrice.getText());
            int max = Integer.parseInt(modifyProductMax.getText());
            int min = Integer.parseInt(modifyProductMin.getText());


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
            if(min < 0 || min >= max){
                Alert alert6 = new Alert(Alert.AlertType.ERROR);
                alert6.setTitle("Not Valid");
                alert6.setContentText("Min must be greater than 0 and less than or equal to max");
                alert6.showAndWait();
                return;
            }

            Product newProduct = (new Product(id, name, price, stock, min, max));
            newProduct.getAllAssociatedParts().addAll(bottomList);
            Inventory.addProduct(newProduct);
            Inventory.deleteProduct(selectedProduct);

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm2.fxml"));
            stage.setScene(new Scene(root));
            stage.show();

        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each text field");
            alert.showAndWait();

        }
    }

    /**
     * This is the method to return to the main screen when the user clicks the Cancel button.
     * Input is not saved when Cancel is clicked.
     * @param actionEvent user clicked the Cancel button
     * @throws IOException
     * */
    public void onActionCancelModifyParts(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm2.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This is the method to search the top table for a part by ID or name.
     * If user searches by ID or name (full or partial) the table view will populate based upon submitted parameters.
     * If user searches the text field and it is empty all parts will populate in the top table.
     * If user searches for an ID or name that is not in inventory. Alert is activated.
     * @param actionEvent user clicks the search text field.
     * */
    public void onActionModifySearch(ActionEvent actionEvent) {

        String partQ = modifyProductSearchTxt.getText();

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
            ModifyProduct.setItems(searchPart);
        } else {
            Alert alert6 = new Alert (Alert.AlertType.ERROR);
            alert6.setTitle("Not Found");
            alert6.setContentText("Item or ID not found in search");
            alert6.showAndWait();
        }
        modifyProductSearchTxt.setText("");
    }
    /**
     *This method takes the product selected on the main screen and sends the product data to the fields on the Modify Product screen.
     * @param selectedProd the product selected on the main screen to be modified
     *
     * */
    public void sendModifyProduct(Product selectedProd) {
        selectedProduct = selectedProd;

        modifyProductAutoGen.setText(String.valueOf(selectedProduct.getId()));
        modifyProductName.setText(selectedProduct.getName());
        modifyProductInv.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPrice.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMin.setText(String.valueOf(selectedProduct.getMin()));
        modifyProductMax.setText(String.valueOf(selectedProduct.getMax()));

        bottomList.addAll(selectedProduct.getAllAssociatedParts());


    }

    /**
     * This method initializes the modifyProductFormController.
     * @param url
     * @param resourceBundle
     *
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ModifyProduct.setItems(Inventory.getAllParts());
        addAssociatedPart.setItems(bottomList);

        PartIDModifyProductAdd.setCellValueFactory(new PropertyValueFactory("id"));
        PartNameModifyPartAdd.setCellValueFactory(new PropertyValueFactory("name"));
        InvLevelModifyPartAdd.setCellValueFactory(new PropertyValueFactory("stock"));
        CostModifyProductAdd.setCellValueFactory(new PropertyValueFactory("price"));

        PartIdModifyProductRemove.setCellValueFactory(new PropertyValueFactory("id"));
        PartNameModifyProductRemove.setCellValueFactory(new PropertyValueFactory("name"));
        InvModifyProductRemove.setCellValueFactory(new PropertyValueFactory("stock"));
        PriceModifyProductRemove.setCellValueFactory(new PropertyValueFactory("price"));

    }


}


