package medicalApp.Doctor.Visit;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import medicalApp.Doctor.Doctor;
import medicalApp.Patient.Patient;
import medicalApp.common.DataSource;
import medicalApp.common.LoadPage;
import medicalApp.common.Visit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class VisitMessage extends HBox {
    private Patient patient;
    private Visit visit;
    private ScrollPane scrollPane;
    private LoadPage loadPage;


    public VisitMessage(Visit visit, int index, Doctor doctor) throws SQLException {
        this.visit = visit;
        Patient patient = DataSource.getInstance().getPaFromDBbyId(visit.getPaId());

        //pane
        Pane mainPane = new Pane();
        mainPane.getStyleClass().add("visitListPanes");
        mainPane.setPrefWidth(665);
        mainPane.setPrefHeight(90);
        mainPane.setLayoutX(0);
        mainPane.setLayoutY(index * 90);

        //label for patient name
        Label nameLabel = new Label(patient.getName() + " " + patient.getFamilyName());
        nameLabel.getStyleClass().add("namePaLabelVisitList");
        nameLabel.setAlignment(Pos.CENTER_RIGHT);
        nameLabel.setLayoutX(353);
        nameLabel.setLayoutY(30);
        nameLabel.setPrefWidth(185);
        nameLabel.setPrefHeight(30);

        //ImageView for patient image
        Image img = new Image(new ByteArrayInputStream(patient.getImg()));
        ImageView imageView = new ImageView(img);
        imageView.getStyleClass().add("paImageVisitList");
        imageView.setLayoutX(570);
        imageView.setLayoutY(10);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);

        mainPane.getChildren().addAll(nameLabel, imageView);
        getChildren().addAll(mainPane);

        setOnMouseClicked(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisitRequest.fxml"));
            Parent parent = null;
            try {
                parent = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            VisitRequest visitRequest = fxmlLoader.getController();
            visitRequest.setVisit(visit);
            visitRequest.setPatient(patient);
            visitRequest.setDr(doctor);
            window.setScene(scene);
            window.show();

        });

    }

}
