package medicalApp.Patient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import medicalApp.Doctor.Doctor;
import medicalApp.common.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FirstChat implements Initializable {

    @FXML private ImageView paProImage;
    @FXML private Label nameLabel;
    @FXML private ScrollPane messagesScrollPane;
    @FXML private TextField messageField;

    private LoadPage loadPage;
    private FXMLLoader fxmlLoader;
    private Patient patient;
    private Doctor doctor;

    @FXML
    private void sendBtn(ActionEvent event) throws IOException, SQLException {
//        try {
//            fxmlLoader = loadPage.loadP(event, "../Patient/paVisit.fxml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if (!messageField.getText().isEmpty()) {
            Chat chat = new Chat(doctor.getId(), patient.getId(), "patient", messageField.getText());
            DataSource.getInstance().insertMessage(chat);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Patient/firstChat.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            FirstChat firstChat = fxmlLoader.getController();
            firstChat.setPatient(patient);
            firstChat.setDr(doctor);
        }

//        PaVisit paVisit = fxmlLoader.getController();
//        paVisit.setPatient(patient);
    }
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
    private void chatBtn(ActionEvent event) throws IOException {
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
    }
    @FXML
    private void medicationReminder(ActionEvent event) throws IOException, SQLException, ParseException {
//        fxmlLoader = loadPage.loadP(event, "../Patient/medicationReminder.fxml");
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
    private void searchDrBtn(ActionEvent event) throws IOException {
//            fxmlLoader = loadPage.loadP(event, "../Patient/searchDr.fxml");
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
    public void setPatient(Patient patient) {
        this.patient = patient;
        Image img = new Image(new ByteArrayInputStream(patient.getImg()));
        paProImage.setImage(img);
        nameLabel.setText(patient.getName() + " " + patient.getFamilyName());
    }
    public void setDr(Doctor doctor)  {
        this.doctor = doctor;
        ArrayList<Chat> chatList;
        try {
            chatList = DataSource.getInstance().getChatFromDB(doctor.getId(), patient.getId());
            if (chatList != null) {
                VBox vBox = new VBox();
                for (int i = 0; i < chatList.size(); i++) {
                    ChatMessage chatMessage = new ChatMessage(i, chatList.get(i));
                    vBox.getChildren().add(chatMessage);
                }
                messagesScrollPane.setContent(vBox);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
