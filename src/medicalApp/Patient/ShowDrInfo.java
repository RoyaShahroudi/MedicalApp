package medicalApp.Patient;

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
import medicalApp.common.LoadPage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowDrInfo implements Initializable {
    @FXML private Label drNameLabel;
    @FXML private Label drLastNLabel;
    @FXML private Label drSpecialityLabel;
    @FXML private TextArea drDescription;
    @FXML private ImageView drImage;

    private LoadPage loadPage;
    private FXMLLoader fxmlLoader;
    private Patient patient;
//    private Doctor doctor;

    @FXML
    private void backBtn(ActionEvent event) throws IOException {
//        try {
//            fxmlLoader = loadPage.loadP(event, "../Patient/searchDr.fxml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Patient/searchDr.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        SearchDr searchDr = fxmlLoader.getController();
        searchDr.setPatient(patient);
    }

    public void setDr(Doctor doctor) {
//        this.doctor = doctor;
        Image img = new Image(new ByteArrayInputStream(doctor.getImg()));
        drImage.setImage(img);
        drNameLabel.setText(doctor.getName());
        drLastNLabel.setText(doctor.getFamilyName());
        drSpecialityLabel.setText(doctor.getSpeciality());
        drDescription.setText(doctor.getDescription());

    }

    public void setPatient(Patient patient) {
        this.patient = patient;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
