package medicalApp.common;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import medicalApp.Doctor.Doctor;
import medicalApp.Doctor.Visit.VisitRequest;
import medicalApp.Patient.Patient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class ChatList extends HBox {

    private LoadPage loadPage;
    private Pane mainPane = new Pane();


    public ChatList(int index, Chat chat, String user) throws SQLException {

        Doctor doctor;
        Patient patient;
        if (user.equals("patient")) {
            doctor = DataSource.getInstance().getDrFromDBbyId(chat.getDrId());
            patient = DataSource.getInstance().getPaFromDBbyId(chat.getPaID());

            //pane
            mainPane.setPrefWidth(230);
            mainPane.setPrefHeight(70);
            mainPane.setLayoutX(0);
            mainPane.setLayoutY(index * 70);
            mainPane.setStyle("-fx-background-color:  #354552;" +
                    "-fx-border-color: #243441;" +
                    "-fx-border-width: 3;");

            //label for patient name
            Label nameLabel = new Label(doctor.getName() + " " + doctor.getFamilyName());
            nameLabel.setAlignment(Pos.CENTER_RIGHT);
            nameLabel.setLayoutX(0);
            nameLabel.setLayoutY(30);
            nameLabel.setPrefWidth(144);
            nameLabel.setPrefHeight(29);

            //ImageView for patient image
            Image img = new Image(new ByteArrayInputStream(doctor.getImg()));
            ImageView imageView = new ImageView(img);
            imageView.setLayoutX(163);
            imageView.setLayoutY(10);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setStyle("-fx-border-radius: 10;" +
                    "-fx-background-radius: 13;");

            mainPane.getChildren().addAll(nameLabel, imageView);
            getChildren().addAll(mainPane);

            setOnMouseClicked(event -> {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chatPage.fxml"));
                Parent parent = null;
                try {
                    parent = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(parent);
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

                ChatPage chatPage = fxmlLoader.getController();
                chatPage.user = "patient";
                chatPage.doctor = doctor;
                chatPage.setPatient(patient);
                try {
                    chatPage.setPatientMessage(chat.getDrId(), chat.getPaID());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                window.setScene(scene);
                window.show();

            });
        } else {

            patient = DataSource.getInstance().getPaFromDBbyId(chat.getPaID());
            doctor = DataSource.getInstance().getDrFromDBbyId(chat.getDrId());

            //pane
            mainPane.setPrefWidth(230);
            mainPane.setPrefHeight(70);
            mainPane.setLayoutX(0);
            mainPane.setLayoutY(index * 70);
            mainPane.setStyle("-fx-background-color:  #354552;" +
                    "-fx-border-color: #243441;" +
                    "-fx-border-width: 3;");

            //label for patient name
            Label nameLabel = new Label(patient.getName() + " " + patient.getFamilyName());
            nameLabel.setAlignment(Pos.CENTER_RIGHT);
            nameLabel.setLayoutX(0);
            nameLabel.setLayoutY(30);
            nameLabel.setPrefWidth(144);
            nameLabel.setPrefHeight(29);

            //ImageView for patient image
            Image img = new Image(new ByteArrayInputStream(patient.getImg()));
            ImageView imageView = new ImageView(img);
            imageView.setLayoutX(163);
            imageView.setLayoutY(10);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setStyle("-fx-border-radius: 10;" +
                    "-fx-background-radius: 13;");

            mainPane.getChildren().addAll(nameLabel, imageView);
            getChildren().addAll(mainPane);

            setOnMouseClicked(event -> {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chatPage.fxml"));
                Parent parent = null;
                try {
                    parent = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(parent);
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

                ChatPage chatPage = fxmlLoader.getController();
                chatPage.user = "doctor";
                chatPage.patient = patient;
                chatPage.setDr(doctor);
                try {
                    chatPage.setDrMessage(chat.getDrId(), chat.getPaID());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                window.setScene(scene);
                window.show();
            });
        }
    }

    public void setAsSelected() {
        mainPane.setStyle("-fx-background-color:  #132330;" +
                "-fx-border-color: #243441;" +
                "-fx-border-width: 3;");
    }
}