package medicalApp.Doctor.Visit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import medicalApp.Doctor.Doctor;
import medicalApp.Patient.Patient;
import medicalApp.common.DataSource;
import medicalApp.common.LoadPage;
import medicalApp.common.Visit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class VisitRequest implements Initializable {
    private Doctor doctor;
    private Patient patient;
    private LoadPage loadPage;
    private Visit visit;

    @FXML private Label nameLabel, lastNLabel, ageLabel, weightLabel;
    @FXML private ImageView imageView;
    @FXML private TextArea descriptionArea;

    @FXML
    private void fileBtn(ActionEvent event){

    }

    @FXML private void readBtn(ActionEvent event) throws SQLException {
        DataSource.getInstance().setVisitAsRead(visit.getVisitId());
    }

    @FXML
    private void backBtn(ActionEvent event) throws IOException, SQLException {
//        FXMLLoader fxmlLoader = loadPage.loadP(event, "drVisit.fxml");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("drVisit.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        DrVisit drVisit = fxmlLoader.getController();
        drVisit.setDr(doctor);

        window.setScene(scene);
        window.show();

    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        Image img = new Image(new ByteArrayInputStream(patient.getImg()));
        imageView.setImage(img);
        nameLabel.setText(patient.getName());
        lastNLabel.setText(patient.getFamilyName());
        weightLabel.setText(patient.getWeight());

        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(formatYear.format(new Date()));
        int rightAge = year - 621 - patient.getBirthY();
        ageLabel.setText(rightAge + "");

    }
    public void setVisit(Visit visit) {
        this.visit = visit;
        descriptionArea.setText(visit.getDescription());
    }

    public void setDr(Doctor doctor) {
        this.doctor = doctor;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
