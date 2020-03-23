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

    public ArrayList<String> inputCheck(){
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

        return fouteInputs;
    }

    @FXML
    void confirmBoeking(ActionEvent event) throws Exception {
        StringBuilder messageBuilder = new StringBuilder("");
        ArrayList<String> fouteInputs = inputCheck();
        String textErrorString = " niet ingevuld.";

        // Als alles is ingevuld
        if(fouteInputs.size() == 0){
            if (Utils.dateInPast(aankomstDatumInput)){
                messageBuilder.append("Aankomst datum kan niet in in het verleden zijn \n");
            }

            if (Utils.dateInPast(vertrekDatumInput)){
                messageBuilder.append("Vertrek datum kan niet in in het verleden zijn\n");
            }

            if (vertrekDatumInput.getValue().isBefore(aankomstDatumInput.getValue())){
                messageBuilder.append("Vertrek datum kan niet voor de aankomst datum zijn");
            }

            // Datum is goed ingevuld en we hebben geen errors meer
            if (messageBuilder.toString().equals("")){

                // Selecteer een kamertype gebaseerd op geselecteerde string van kamertype
                List<KamerType> alleKamerTypen = hotel.getKamerTypen();
                KamerType kamerType = null;
                
                for (KamerType type : alleKamerTypen){
                    if (kamertypeInput.getValue().equals(type.toString())){
                        kamerType = type;
                    }
                }

                // Voeg een nieuwe boeking toe en geef een melding dat die boeking geslaagd is
                hotel.voegBoekingToe(aankomstDatumInput.getValue(), vertrekDatumInput.getValue(), naamInput.getText(), adresInput.getText(), kamerType);
                messageBuilder.append("Boeking is geslaagd!");
            }
        }
        // Als er maar 1 input niet is ingevuld
        else if (fouteInputs.size() == 1){
            fouteInputs.set(0, Utils.capitalizeString(fouteInputs.get(0)));
            messageBuilder.append(fouteInputs.get(0) + " is" + textErrorString);
        }
        // Als er meerdere inputs niet zijn ingevuld
        else {
            // Capitalize het eerste item in foutmeldingen
            fouteInputs.set(0, Utils.capitalizeString(fouteInputs.get(0)));

            for (String fouteInput : fouteInputs){
                // Zet geen komma achter het laatste error item
                if(fouteInput.equals(fouteInputs.get(fouteInputs.size() - 1))){
                    messageBuilder.append(fouteInput);
                }
                // Zet een "en" tussen de laatste twee error items
                else if(fouteInput.equals(fouteInputs.get(fouteInputs.size() - 2))){
                    messageBuilder.append(fouteInput + " en ");
                }
                // Zet een komma tussen error items
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
        messageField.setText(null);
    }

}
