package medicalApp.Patient;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import medicalApp.common.DataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class MedicineAlarm extends HBox {

    public MedicineAlarm(Medicine medicine, int index, String today, Patient patient) {


        //pane
        Pane mainPane = new Pane();
        mainPane.getStyleClass().add("medicineListPane");
        mainPane.setPrefWidth(198);
        mainPane.setPrefHeight(149);
        mainPane.setLayoutX(0);
        mainPane.setLayoutY(index * 149);
        mainPane.setStyle("-fx-background-color:  #354552;" +
                "-fx-border-color: #243441;" +
                "-fx-border-width: 4;");



        //label for medicine name
        Label nameLabel = new Label(medicine.getMedicineName());
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setLayoutX(10);
        nameLabel.setLayoutY(15);
        nameLabel.setPrefWidth(177);
        nameLabel.setPrefHeight(30);

        //label for time1
        Label timeLabel = new Label(medicine.getHour() + " : " + medicine.getMin());
        timeLabel.setAlignment(Pos.CENTER);
        timeLabel.setLayoutX(50);
        timeLabel.setLayoutY(62);
        timeLabel.setPrefWidth(77);
        timeLabel.setPrefHeight(25);

        Button consumedBtn = new Button("مصرف شد");
        consumedBtn.setAlignment(Pos.CENTER);
        consumedBtn.setLayoutX(49);
        consumedBtn.setLayoutY(110);
        consumedBtn.setPrefWidth(100);
        consumedBtn.setPrefHeight(25);

        mainPane.getChildren().addAll(nameLabel, timeLabel, consumedBtn);
        getChildren().add(mainPane);

        consumedBtn.setOnMouseClicked(event -> {
            medicine.setConsumedDate(today);
            try {
                DataSource.getInstance().setMedicineConsumed(medicine.getId(), today);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Patient/medicationReminder.fxml"));
                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent);

                MedicationReminder medicationReminder = fxmlLoader.getController();
                medicationReminder.setPatient(patient);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            } catch (SQLException | ParseException | IOException throwables) {
                throwables.printStackTrace();
            }


        });

    }
}
