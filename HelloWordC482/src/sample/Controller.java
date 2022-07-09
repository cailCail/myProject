package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
        public Label theLabel;

        public void onCreate(ActionEvent actionEvent) {
                System.out.println("On Create");
                theLabel.setText("Create");
        }


       @FXML
        private AnchorPane ModifyPartTableView;

        @FXML
        private ToggleGroup addPartToggle;

        @FXML
        private TextField modifyAutoGenTxt;

        @FXML
        private RadioButton modifyInHouse;

        @FXML
        private TextField modifyInvTxt;

        @FXML
        private TextField modifyMachineIdTxt;

        @FXML
        private TextField modifyMaxTxt;

        @FXML
        private TextField modifyMinTxt;

        @FXML
        private TextField modifyNameTxt;

        @FXML
        private RadioButton modifyOutsource;

        @FXML
        private TextField modifyPriceTxt;

        @FXML
        void onInHouse(ActionEvent event) {

        }

        @FXML
        void onOutsourced(ActionEvent event) {

        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                System.out.println("I am initialized");
        }



}







