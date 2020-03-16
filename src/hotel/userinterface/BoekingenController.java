package hotel.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class BoekingenController {

    @FXML
    private TextField adresInput;

    @FXML
    private TextField naamInput;

    @FXML
    private DatePicker aankomstDatumInput;

    @FXML
    private DatePicker vertrekDatumInput;

    @FXML
    private ChoiceBox<?> kamertypeInput;

    @FXML
    private Button resetBoekingKnop;

    @FXML
    private Button confirmBoekingKnop;

    @FXML
    void confirmBoeking(ActionEvent event) {

    }

    @FXML
    void resetBoeking(ActionEvent event) {

    }

}
