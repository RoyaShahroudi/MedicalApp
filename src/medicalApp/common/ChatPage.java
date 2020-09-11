package medicalApp.common;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import medicalApp.Doctor.Doctor;
import medicalApp.Doctor.Visit.DrVisit;
import medicalApp.Patient.FirstChat;
import medicalApp.Patient.PaVisit;
import medicalApp.Patient.Patient;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatPage implements Initializable {
    @FXML private ScrollPane messagesScrollPane;
    @FXML private ScrollPane usersScrollPane;
    @FXML private TextField messageField;
    @FXML private Label chatNameLabel;


    private LoadPage loadPage = new LoadPage();
    public Doctor doctor;
    public Patient patient;
    public String user;
    public boolean firstClick = false;

    @FXML
    private void backBtn(ActionEvent event) throws IOException, SQLException {

        if (user.equals("patient")) {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Patient/paVisit.fxml"));
//            Parent parent = fxmlLoader.load();
//            Scene scene = new Scene(parent);
//            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
//            window.setScene(scene);
//            window.show();
            FXMLLoader fxmlLoader = loadPage.loadP(event, "../Patient/paVisit.fxml");
            PaVisit paVisit = fxmlLoader.getController();
            paVisit.setPatient(patient);
        }else {
            FXMLLoader fxmlLoader = loadPage.loadP(event, "../Doctor/Visit/drVisit.fxml");
            DrVisit drVisit = fxmlLoader.getController();
            drVisit.setDr(doctor);
        }
    }
    @FXML
    private void sendBtn(ActionEvent event) throws IOException, SQLException {
        if (!messageField.getText().isEmpty()) {
            Chat chat = new Chat(doctor.getId(), patient.getId(), user, messageField.getText());
            DataSource.getInstance().insertMessage(chat);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chatPage.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            ChatPage chatPage = fxmlLoader.getController();
            if (user.equals("patient")) {
                chatPage.user = "patient";
                chatPage.doctor = doctor;
                chatPage.setPatient(patient);
                chatPage.setPatientMessage(chat.getDrId(), chat.getPaID());
            } else {
                chatPage.user = "doctor";
                chatPage.patient = patient;
                chatPage.setDr(doctor);
                chatPage.setDrMessage(chat.getDrId(), chat.getPaID());
            }
        }
    }

    public void setDr(Doctor doctor) {
        this.doctor = doctor;

        ArrayList<Chat> chats;
        try {
            chats = DataSource.getInstance().getDrChatFromDB(doctor.getId());
            if (chats != null) {
                VBox vBox = new VBox();
                ArrayList<Integer> arrayList = new ArrayList<>();
                for (int i = 0; i < chats.size(); i++) {
                    boolean check = true;
                    for (int j = 0; j < arrayList.size(); j++) {
                        if (chats.get(i).getPaID() == arrayList.get(j)){
                          check = false;
                          break;
                        }
                    }
                    if (check == true) {
                        ChatList chatList = new ChatList(i, chats.get(i), user);
                        if (!firstClick) {
                            if (patient.getId() == chats.get(i).getPaID())
                                chatList.setAsSelected();
                        }
                        vBox.getChildren().add(chatList);
                        arrayList.add(chats.get(i).getPaID());
                    }
                }
                usersScrollPane.setContent(vBox);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    // messages which doctor should read them
    public void setDrMessage(int drId, int paId) throws SQLException {
        user = "doctor";
        Patient patient = DataSource.getInstance().getPaFromDBbyId(paId);
        chatNameLabel.setText(patient.getName() + " " + patient.getFamilyName());
        ArrayList<Chat> chatList;
        try {
            chatList = DataSource.getInstance().getChatFromDB(drId, paId);
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

    public void setPatient(Patient patient) {
        this.patient = patient;

        ArrayList<Chat> chats;
        try {
            chats = DataSource.getInstance().getPaChatFromDB(patient.getId());
            if (chats != null) {
                VBox vBox = new VBox();
                ArrayList<Integer> arrayList = new ArrayList<>();
                for (int i = 0; i < chats.size(); i++) {
                    boolean check = true;
                    for (int j = 0; j < arrayList.size(); j++) {
                        if (chats.get(i).getDrId() == arrayList.get(j)){
                            check = false;
                            break;
                        }
                    }
                    if (check == true) {
                        ChatList chatList = new ChatList(i, chats.get(i), user);
                        if (!firstClick) {
                            if (doctor.getId() == chats.get(i).getDrId())
                                chatList.setAsSelected();
                        }
                        vBox.getChildren().add(chatList);
                        arrayList.add(chats.get(i).getDrId());
                    }
                }
                usersScrollPane.setContent(vBox);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    // messages which patient should read them
    public void setPatientMessage(int drId, int paId) throws SQLException {
        user = "patient";
        Doctor doctor = DataSource.getInstance().getDrFromDBbyId(drId);
        chatNameLabel.setText(doctor.getName() + " " + doctor.getFamilyName());
        ArrayList<Chat> chatList;
        try {
            chatList = DataSource.getInstance().getChatFromDB(drId, paId);
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
