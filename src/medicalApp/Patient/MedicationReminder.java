package medicalApp.Patient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import medicalApp.common.ChatPage;
import medicalApp.common.DataSource;
import medicalApp.common.LoadPage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class MedicationReminder implements Initializable {

    @FXML private ImageView paProImage;
    @FXML private Label nameLabel;
    @FXML private Label errorLabel;
    @FXML private ScrollPane forgottenScroll;
    @FXML private ScrollPane allMedicinesScroll;

    @FXML private TextField medicineNameField;
    @FXML private ComboBox<String> hourCombo;
    @FXML private ComboBox<String> minCombo;

    private LoadPage loadPage;
    private FXMLLoader fxmlLoader;
    private Patient patient;


    @FXML
    private void addBtn(ActionEvent event) throws SQLException, ParseException {

        if (medicineNameField.getText().isEmpty() || hourCombo.getSelectionModel().isEmpty() ||
                minCombo.getSelectionModel().isEmpty()) {
            errorLabel.setText("نام یا زمان را وارد نکرده اید.");
        } else {


        Medicine medicine = new Medicine(patient.getId(), medicineNameField.getText(),
                hourCombo.getValue(), minCombo.getValue());

            DataSource.getInstance().insertMedicine(medicine);
            //refresh page
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Patient/medicationReminder.fxml"));
                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent);
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();

                MedicationReminder medicationReminder = fxmlLoader.getController();
                medicationReminder.setPatient(patient);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    @FXML
    private void visitBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Patient/paVisit.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        PaVisit paVisit = fxmlLoader.getController();
        paVisit.setPatient(patient);
    }
    @FXML
    private void editProBtn(ActionEvent event) throws IOException {

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
    private void chatBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../common/chatPage.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        ChatPage chatPage = fxmlLoader.getController();
        chatPage.user = "patient";
        chatPage.firstClick = true;
        chatPage.setPatient(patient);

        window.setScene(scene);
        window.show();

    }

    @FXML
    private void searchBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Patient/searchDr.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        SearchDr searchDr = fxmlLoader.getController();
        searchDr.setPatient(patient);

        window.setScene(scene);
        window.show();

    }
    @FXML
    private void exitBtn(ActionEvent event) {
        DataSource.getInstance().close();
        System.exit(0);
    }

    public void setPatient(Patient patient) throws SQLException, ParseException {
        this.patient = patient;
        Image img = new Image(new ByteArrayInputStream(patient.getImg()));
        paProImage.setImage(img);
        nameLabel.setText(patient.getName() + " " + patient.getFamilyName());

        //fill panes of forgotten medicine and Should be consumed
        SimpleDateFormat formatMinutes = new SimpleDateFormat("mm");
        int currentMin = Integer.parseInt(formatMinutes.format(new Date()));

        SimpleDateFormat formatHours = new SimpleDateFormat("HH");
        int currentHour = Integer.parseInt(formatHours.format(new Date()));


        ArrayList<Medicine> medicineList = DataSource.getInstance().getMedicine(patient.getId());
        VBox allVBox = new VBox();
        VBox forgottenVBox = new VBox();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String today = simpleDateFormat.format(new Date());

        for (int i = 0; i < medicineList.size() ; i++) {
            MedicineAlarm medicineAlarm = new MedicineAlarm(medicineList.get(i), i, today, patient);
            allVBox.getChildren().add(medicineAlarm);

            if (!medicineList.get(i).getConsumedDate().equals(today) &&
                    currentHour >= Integer.parseInt(medicineList.get(i).getHour())) {
                if (currentMin >= Integer.parseInt(medicineList.get(i).getMin())){
                    MedicineAlarm medicineAlarm2 = new MedicineAlarm(medicineList.get(i), i, today, patient);
                    forgottenVBox.getChildren().add(medicineAlarm2);
                }

            }
        }
        allMedicinesScroll.setContent(allVBox);
        forgottenScroll.setContent(forgottenVBox);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] hour = new String[24];
        String[] min = new String[60];

        for (int i = 0; i < 24; i++) {
            hour[i] = i + "";

        }
        for (int i = 0; i < 60; i++) {
            min[i] = i + "";
        };

        ObservableList<String> hourComboList = FXCollections.observableArrayList(hour);
        ObservableList<String> minComboList = FXCollections.observableArrayList(min);

        hourCombo.setItems(hourComboList);
        minCombo.setItems(minComboList);


    }
}
