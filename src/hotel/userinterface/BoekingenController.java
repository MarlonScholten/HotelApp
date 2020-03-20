package hotel.userinterface;

import hotel.model.KamerType;
import hotel.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import hotel.model.Hotel;

import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class BoekingenController {
    private Hotel hotel = Hotel.getHotel();

    @FXML
    private TextField naamInput;
    @FXML
    private TextField adresInput;
    @FXML
    private DatePicker aankomstDatumInput;
    @FXML
    private DatePicker vertrekDatumInput;
    @FXML
    private ChoiceBox kamertypeInput;
    @FXML
    private Label messageField;
    @FXML
    private Button resetBoekingKnop;
    @FXML
    private Button confirmBoekingKnop;

    public void initialize() {
        ObservableList<String> kamerTypen = FXCollections.observableArrayList();
        List<KamerType> alleKamerTypen = hotel.getKamerTypen();

        for (KamerType type: alleKamerTypen){
            kamerTypen.add(type.toString());
        }
        kamertypeInput.setItems(kamerTypen);
    }

    @FXML
    void confirmBoeking(ActionEvent event) {
        StringBuilder messageBuilder = new StringBuilder();
        ArrayList<String> fouteInputs = new ArrayList<>();

        // Naam input check
        if (Utils.textFieldCheck(naamInput)){
            Utils.setTrimmedText(naamInput);
        } else {
            fouteInputs.add("naam");
        }

        // Adres input check
        if (Utils.textFieldCheck(adresInput)){
            Utils.setTrimmedText(adresInput);
        } else {
            fouteInputs.add("adres");
        }

        // Aankomst datum input check
        boolean aankomstDatumLeeg = aankomstDatumInput.getValue() == null;
        if (aankomstDatumLeeg){
            fouteInputs.add("aankomstdatum");
        }

        // Vertrek datum input check
        boolean vertrekDatumLeeg = vertrekDatumInput.getValue() == null;
        if (vertrekDatumLeeg){
            fouteInputs.add("vertrekdatum");
        }

        // Kamertype input check
        boolean kamerTypeLeeg = kamertypeInput.getSelectionModel().isEmpty();
        if (kamerTypeLeeg){
            fouteInputs.add("kamertype");
        }

        String textErrorString = " niet ingevuld.";
        if(fouteInputs.size() == 0){
            if (Utils.dateInPast(aankomstDatumInput)){
                messageBuilder.append("aankomst datum kan niet in in het verleden zijn");
            }

            if (Utils.dateInPast(vertrekDatumInput)){
                messageBuilder.append("vertrek datum kan niet in in het verleden zijn");
            }
        }
        else if (fouteInputs.size() == 1){
            fouteInputs.set(0, Utils.capitalizeString(fouteInputs.get(0)));
            messageBuilder.append(fouteInputs.get(0) + " is" + textErrorString);
        }
        else {
            fouteInputs.set(0, Utils.capitalizeString(fouteInputs.get(0)));

            for (String fouteInput : fouteInputs){
                if(fouteInput.equals(fouteInputs.get(fouteInputs.size() - 1))){
                    messageBuilder.append(fouteInput);
                }
                else if(fouteInput.equals(fouteInputs.get(fouteInputs.size() - 2))){
                    messageBuilder.append(fouteInput + " en ");
                }
                else {
                    messageBuilder.append(fouteInput + ", ");
                }
            }
            messageBuilder.append(" zijn" + textErrorString);
        }
        messageField.setText(messageBuilder.toString());
    }

    @FXML
    void resetBoeking(ActionEvent event) {
        naamInput.setText("");
        adresInput.setText("");
        aankomstDatumInput.setValue(null);
        vertrekDatumInput.setValue(null);
        kamertypeInput.setValue(null);
    }

}
