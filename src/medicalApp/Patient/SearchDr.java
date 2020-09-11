package medicalApp.Patient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import medicalApp.Doctor.Doctor;
import medicalApp.common.ChatPage;
import medicalApp.common.DataSource;
import medicalApp.common.LoadPage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

public class SearchDr implements Initializable {
    @FXML private ImageView paProImage;
    @FXML private Label nameLabel;
    @FXML private Label errorLabel;
    @FXML private TextField searchDrNameField;
    @FXML private TextField searchDrLastNField;

    private LoadPage loadPage;
    private FXMLLoader fxmlLoader;
    private Patient patient;
    private Doctor doctor;

    @FXML
    private void visitBtn(ActionEvent event) throws IOException {
//        try {
//            fxmlLoader = loadPage.loadP(event, "../Patient/paVisit.fxml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Patient/paVisit.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        PaVisit paVisit = fxmlLoader.getController();
        paVisit.setPatient(patient);
    }
    @FXML private void editProBtn(ActionEvent event) throws IOException {
//        try {
//            fxmlLoader = loadPage.loadP(event, "../Patient/editProfileInfo.fxml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Patient/editProfileInfo.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        EditProfileInfo editProfileInfo = fxmlLoader.getController();
        editProfileInfo.setPatient(patient);
    }
    @FXML
    private void chatBtn(ActionEvent event) {
        try {
//            fxmlLoader = loadPage.loadP(event, "../common/chatPage.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../common/chatPage.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

            ChatPage chatPage = fxmlLoader.getController();
            chatPage.user = "patient";
            chatPage.firstClick = true;
            chatPage.setPatient(patient);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void medicationReminder(ActionEvent event) throws IOException, SQLException, ParseException {
//        fxmlLoader = loadPage.loadP(event, "../Patient/medicationReminder.fxml");
//        MedicationReminder medicationReminder = fxmlLoader.getController();
//        medicationReminder.setPatient(patient);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Patient/medicationReminder.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        MedicationReminder medicationReminder = fxmlLoader.getController();
        medicationReminder.setPatient(patient);
    }
    @FXML
    private void exitBtn(ActionEvent event) {
        DataSource.getInstance().close();
        System.exit(0);
    }


    public void setPatient(Patient patient) {
        this.patient = patient;
        Image img = new Image(new ByteArrayInputStream(patient.getImg()));
        paProImage.setImage(img);
        nameLabel.setText(patient.getName() + " " + patient.getFamilyName());
    }

    @FXML
    private void infoBtn(ActionEvent event) throws IOException, SQLException {
        if (!searchDrNameField.getText().isEmpty() && !searchDrLastNField.getText().isEmpty()) {
            doctor = DataSource.getInstance().getDrFromDBbyName(searchDrNameField.getText(), searchDrLastNField.getText());
            if (doctor != null) {
//            FXMLLoader fxmlLoader = loadPage.loadP(event, "../Patient/showDrInfo.fxml");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Patient/showDrInfo.fxml"));
                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent);
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();

                ShowDrInfo showDrInfo = fxmlLoader.getController();
                showDrInfo.setPatient(patient);
                showDrInfo.setDr(doctor);
            } else errorLabel.setText("این نام در لیست پزشکان موجود نیست.");
        }
    }

    @FXML
    private void chatToDrBtn(ActionEvent event) throws IOException, SQLException {
        if (!searchDrNameField.getText().isEmpty() && !searchDrLastNField.getText().isEmpty()) {

            doctor = DataSource.getInstance().getDrFromDBbyName(searchDrNameField.getText(), searchDrLastNField.getText());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Patient/firstChat.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

            FirstChat firstChat  = fxmlLoader.getController();
            firstChat.setPatient(patient);
            firstChat.setDr(doctor);
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
