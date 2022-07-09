package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Inhouse;
import model.Inventory;
import model.Outsource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class initializes the AddPartController.
 *
 * */
public class AddPartController implements Initializable {

    /** Label for machine ID. */
    public Label addLabelMachineID;

    /** AnchorPane for the add part table. */
    public AnchorPane AddPartTable;

    /** Save button. */
    public Button AddPartSaveButton;

    /** Cancel button. */
    public Button CancelPartButton;

    /** Setting the stage and scene.
     * */
    Stage stage;
    Parent scene;

    /** Company Name string. */
    String companyName;

    /**
     * Radio toggle group
     * */
    @FXML
    private ToggleGroup ToggleGroupAddPart;

    /**
     * Radio InHouse button.
     * */
    @FXML
    private RadioButton InHouseRadioBtn;

    /**
     * Radio OutSource button.
     * */
    @FXML
    private RadioButton OutSourceRadioBtn;

    /**
     * Text field auto generate ID.
     * */
    @FXML
    private TextField AddPartAutoGen;

    /**
     * Text field add part name.
     * */
    @FXML
    private TextField AddPartName;

    /**
     * Text field add part stock.
     * */
    @FXML
    private TextField AddPartInv;

    /**
     * Text field add part price.
     * */
    @FXML
    private TextField AddPartPrice;

    /**
     * Text field add part max.
     * */
    @FXML
    private TextField AddPartMax;

    /**
     * Text field add part min.
     * */
    @FXML
    private TextField AddPartMin;

    /**
     * Text field add part machine ID.
     * */
    @FXML
    private TextField AddPartMachineID;

    /**
     * This method is used to save a new part when the user clicks the save button. This method creates a new part using information
     * submitted by a user. Submitted information is added to the Add Part screen and the new part is saved to inventory.
     * @param actionEvent user clicks the save button.
     * @throws IOException
     * @throws NumberFormatException
     *
     * If user name is empty alert activated and user prompted to enter a name.
     * If user enters information that violates stock requirements alert is activated for user to fix errors.
     * @return if an alert is activated user remains on current page to fix error based upon activated alert prompt.
     *
     * */
    public void AddPartSave(ActionEvent actionEvent) throws IOException {
        // Allowing user to input on the add part form

        try {
            String name = AddPartName.getText();
            int stock = Integer.parseInt(AddPartInv.getText());
            double price = Double.parseDouble(AddPartPrice.getText());
            int min = Integer.parseInt(AddPartMin.getText());
            int max = Integer.parseInt(AddPartMax.getText());

            if (name.isEmpty()){
                Alert alert6 = new Alert(Alert.AlertType.ERROR);
                alert6.setTitle("Not Valid");
                alert6.setContentText("Name is empty, please enter a name?");
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


            if (InHouseRadioBtn.isSelected()) {
                int machineid = Integer.parseInt(AddPartMachineID.getText());
                Inhouse newInhousePart = (new Inhouse(Inventory.nextid++, name, price, stock, min, max, machineid));

                Inventory.addPart(newInhousePart);

            }
            if (OutSourceRadioBtn.isSelected()) {
                String companyName = AddPartMachineID.getText();
                Outsource newOutsourcePart = (new Outsource(Inventory.nextid++, name, price, stock, min, max, companyName));
                Inventory.addPart(newOutsourcePart);
                OutSourceRadioBtn.setText("Company Name");

            }

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
 * This method returns to the main menu screen when the user clicks the cancel button.
 * Information is not saved when the user clicks the cancel button.
 * @param event user clicks the cancel button.
 *
 *
 * */
    @FXML
    void CancelOnActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm2.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
/**
 * This is the method that will change the label to Machine ID when the InHouse radio button is selected.
 * @param actionEvent user clicks the InHouse radio button.
 * */
    public void AddPartInHouseRadio(ActionEvent actionEvent) {
        addLabelMachineID.setText("Machine ID");

    }

/**
 * This is the method that will change the label to Company Name when the OutSource radio button is selected.
 *  * @param actionEvent user clicks the OutSource radio button.
 * */
    public void OutSourceRadioBtn(ActionEvent actionEvent) {
        addLabelMachineID.setText("Company Name");
    }
/**
 * This is the method to set the radio buttons to the InHouse by default.
 * @param resourceBundle
 * @param url
 * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InHouseRadioBtn.setSelected(true);
    }


}








