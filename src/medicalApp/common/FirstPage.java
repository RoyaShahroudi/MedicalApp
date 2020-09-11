package medicalApp.common;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import medicalApp.Doctor.Doctor;
import medicalApp.Doctor.Visit.DrVisit;
import medicalApp.Patient.PaVisit;
import medicalApp.Patient.Patient;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FirstPage implements Initializable {

    @FXML private Label drErrorLabel;
    @FXML private Label paErrorLabel;

    @FXML private TextField drEmailField;
    @FXML private TextField drPasswordField;

    @FXML private TextField paEmailField;
    @FXML private TextField paPasswordField;

    private FXMLLoader fxmlLoader = new FXMLLoader();
    private LoadPage loadPage = new LoadPage();

    @FXML
    private void signInDrBtn(ActionEvent event) throws IOException {
        loadPage.loadP(event, "../Doctor/SignIn/signInDr.fxml");

//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Doctor/signInDr.fxml"));
//        Parent parent = fxmlLoader.load();
//        Scene scene = new Scene(parent);
//        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
//        window.setScene(scene);
//        window.show();
    }

    @FXML
    private void signInPaBtn(ActionEvent event) throws IOException {
        loadPage.loadP(event, "../Patient/signInPatient.fxml");
    }

    @FXML
    private void enterDrBtn(ActionEvent event) {
        String drEmail = drEmailField.getText();
        String drPass = drPasswordField.getText();
        Doctor doctor = new Doctor();
        doctor = DataSource.getInstance().getDrFromDB(drEmail, drPass);

        if (doctor == null) {
            drErrorLabel.setText("ایمیل یا پسورد اشتباه است.");
        } else {

            try {

                fxmlLoader = loadPage.loadP(event, "../Doctor/Visit/drVisit.fxml");
                DrVisit drVisit = fxmlLoader.getController();
                drVisit.setDr(doctor);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void enterPatientBtn(ActionEvent event) {
        String paEmail = paEmailField.getText();
        String paPassword = paPasswordField.getText();
        Patient patient = new Patient();
        patient = DataSource.getInstance().getPaFromDB(paEmail, paPassword);

        if (patient == null) {
            paErrorLabel.setText("ایمیل یا پسورد اشتباه است.");
        } else {

            try {

                fxmlLoader = loadPage.loadP(event, "../Patient/paVisit.fxml");
                PaVisit paVisit = fxmlLoader.getController();
                paVisit.setPatient(patient);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
