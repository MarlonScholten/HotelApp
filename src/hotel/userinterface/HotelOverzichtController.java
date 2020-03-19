package hotel.userinterface;

import hotel.HotelApp;
import hotel.model.Boeking;
import hotel.model.Hotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class HotelOverzichtController{
    @FXML private Label hotelnaamLabel;
    @FXML private ListView boekingenListView;
    @FXML private DatePicker overzichtDatePicker;

    private Hotel hotel = Hotel.getHotel();

    public void initialize() {
        hotelnaamLabel.setText("Boekingen hotel " + hotel.getNaam());
        overzichtDatePicker.setValue(LocalDate.now());
        toonBoekingen();
    }

    public void toonVorigeDag(ActionEvent actionEvent) {
        LocalDate dagEerder = overzichtDatePicker.getValue().minusDays(1);
        overzichtDatePicker.setValue(dagEerder);
        toonBoekingen();
    }

    public void toonVolgendeDag(ActionEvent actionEvent) {
        LocalDate dagLater = overzichtDatePicker.getValue().plusDays(1);
        overzichtDatePicker.setValue(dagLater);
        toonBoekingen();
    }

    public void nieuweBoeking(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Boekingen.fxml"));
            Parent root1 = fxmlLoader.load();
            stage.initOwner(HotelApp.primaryStage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
        stage.setOnCloseRequest((WindowEvent event1) -> {
            toonBoekingen();
        });
    }

    public void toonBoekingen() {
        ObservableList<String> boekingen = FXCollections.observableArrayList();
        List<Boeking> alleBoekingen = hotel.getBoekingen();
        LocalDate currentDate = overzichtDatePicker.getValue();

        for (Boeking boeking : alleBoekingen){
            LocalDate aankomstDatum = boeking.getAankomstDatum();

            if (aankomstDatum.equals(currentDate)){
                LocalDate vertrekDatum = boeking.getVertrekDatum();
                int kamerNummer = boeking.getKamer().getKamerNummer();
                String klantNaam = boeking.getBoeker().getNaam();

                boekingen.add("A: " + aankomstDatum + "   V: " + vertrekDatum + "   Kamer: " + kamerNummer + "   Naam: " + klantNaam);
            }
        }
        boekingenListView.setItems(boekingen);
    }
}