
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class creates the modifyPartController.
 * */
public class modifyPartController implements Initializable {

    /**
     * Sets the stage and the scene.
     * */
    Stage stage;
    Parent scene;

    /**
     * set the index.
     * */
    int index;

    /**
     * set the selectedPart.
     * */
    Part selectedPart;

    /**
     *set machineid.
     * */
    int machineid;

    /**
     * set string companyName.
     * */
    String companyName;

    /**
     * set the label for machineid.
     * */
    public Label modifyMachineId;

    /**
     * Toggle button in a group for InHouse and Outsource.
     * */
    @FXML
    private ToggleGroup addPartToggle;

    /**
     * Radio button Inhouse.
     * */
    @FXML
    private RadioButton modifyInHouse;

    /**
     * Radio button Outsource.
     * */
    @FXML
    private RadioButton modifyOutsource;

    /**
     * Text field for auto generated ID.
     * */
    @FXML
    private TextField modifyAutoGenTxt;

    /**
     * Text field name.
     * */
    @FXML
    private TextField modifyNameTxt;

    /**
     * Text field stock.
     * */
    @FXML
    private TextField modifyInvTxt;

    /**
     * Text field price.
     * */
    @FXML
    private TextField modifyPriceTxt;

    /**
     * Text field max.
     * */
    @FXML
    private TextField modifyMaxTxt;

    /**
     * Text field min.
     * */
    @FXML
    private TextField modifyMinTxt;

    /**
     * Text field machineid.
     * */
    @FXML
    private TextField modifyMachineIdTxt;


    /**
     * This is the method to change the label to Machine ID when the user clicks the InHouse radio button.
     * @param actionEvent user clicks the InHouse radio button.
     * */
    public void onInHouse(ActionEvent actionEvent) {

        modifyMachineId.setText("Machine ID");
    }

    /**
     * This is the method to modify a preexisting part when the user clicks the save button.
     * This method will allow the user to submit changes to information entered for the part selected.
     * This method will take the preexisting part that is changed and replace it in inventory using the part ID.
     *
     * @param actionEvent user clicks the save button.
     * @throws IOException
     *
     * If name is null. Alert is activated and user must enter a name in the text field. User remains on current screen.
     * If invalid min and max requirements are entered. Alert is activated and user must enter valid min and max information. User remains on current field to repair errors.
     * If invalid stock requirements are entered. Alert is activated. User remains on current screen to enter valid stock amounts.
     *
     * @return user remains on screen if alert is activated for user to repair errors.
     * @throws NumberFormatException
     * */
    public void onActionSaveModifyPart(ActionEvent actionEvent) throws IOException {

        try {
            int id = Integer.parseInt(modifyAutoGenTxt.getText());
            String name = modifyNameTxt.getText();
            int stock = Integer.parseInt(modifyInvTxt.getText());
            double price = Double.parseDouble(modifyPriceTxt.getText());
            int min = Integer.parseInt(modifyMinTxt.getText());
            int max = Integer.parseInt(modifyMaxTxt.getText());

            if (modifyInHouse.isSelected()) {
                modifyMachineId.setText("Machine ID");
                Inhouse newInhousePart = (new Inhouse(id, name, price, stock, min, max, machineid));
                Inventory.addPart(newInhousePart);
            }

            if (modifyOutsource.isSelected()) {
                modifyMachineId.setText("Company Name");
                    Outsource newOutsourcePart = (new Outsource(id, name, price, stock, min, max, companyName));
                    Inventory.addPart(newOutsourcePart);

            }
            Inventory.deletePart(selectedPart);

            if (name.isEmpty()){
                Alert alert6 = new Alert(Alert.AlertType.ERROR);
                alert6.setTitle("Not Valid");
                alert6.setContentText("Name is empty, please enter a name.");
                alert6.showAndWait();
                return;
            }

            if (stock < min || stock >max){

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

        }

        catch (NumberFormatException e){
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("Error");
            alert3.setContentText("Please enter valid value for each field");
            alert3.showAndWait();
            return;

        }    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm2.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

    }

    /**
     * This is the method to return to the main screen when the user clicks the Cancel button.
     * Information that the user submitted is not saved.
     *
     * @throws IOException
     * @param actionEvent user clicks cancel button.
     *
     * */
    public void modifyPartCancelBtn(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm2.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This method takes the part selected on the main screen and sends the parts data to the fields on the Modify Part screen.
     * @param selectedP the part selected on the main screen to be modified.
     *
     * */
    public void sendModify(Part selectedP) {
        selectedPart = selectedP;

        modifyAutoGenTxt.setText(String.valueOf(selectedPart.getId()));
        modifyNameTxt.setText(selectedPart.getName());
        modifyInvTxt.setText(String.valueOf(selectedPart.getStock()));
        modifyAutoGenTxt.setText(String.valueOf(selectedPart.getId()));
        modifyPriceTxt.setText(String.valueOf(selectedPart.getPrice()));
        modifyMinTxt.setText(String.valueOf(selectedPart.getMin()));
        modifyMaxTxt.setText(String.valueOf(selectedPart.getMax()));

        if (selectedPart instanceof Inhouse) {
            modifyInHouse.setSelected(true);
            modifyMachineId.setText("Machine ID");
            modifyMachineIdTxt.setText(String.valueOf(((Inhouse) selectedPart).getMachineid()));
        }

        if (selectedPart instanceof Outsource) {
            modifyOutsource.setSelected(true);
            modifyMachineId.setText("Company Name");
            modifyMachineIdTxt.setText(((Outsource) selectedPart).getCompanyName());

        }
    }

    /**
     * This is the method to change the label to Company Name when the user clicks the Outsource radio button.
     * @param actionEvent user clicks the Outsource radio button.
     * */

    public void onOutsource(ActionEvent actionEvent) {
        modifyMachineId.setText("Company Name");
    }
    /**
     * This method initializes modify part class.
     * @param resourceBundle
     * @param url
     * */
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
