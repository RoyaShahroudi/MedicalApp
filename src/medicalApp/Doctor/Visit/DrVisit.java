package medicalApp.Doctor.Visit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import medicalApp.Doctor.Doctor;
import medicalApp.Doctor.DrDescription;
import medicalApp.common.ChatPage;
import medicalApp.common.DataSource;
import medicalApp.common.LoadPage;
import medicalApp.common.Visit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DrVisit implements Initializable {

    @FXML private ImageView drProImage;
    @FXML private Label drNameLabel;
    @FXML private ScrollPane scrollPane;
    @FXML private Label numberLabel;

    private Doctor doctor;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private LoadPage loadPage = new LoadPage();
    private Group visitGroup = new Group();

    @FXML
    private void chatBtn(ActionEvent event) throws IOException {
        try {
            fxmlLoader = loadPage.loadP(event, "../common/chatPage.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChatPage chatPage = fxmlLoader.getController();
        chatPage.user = "doctor";
        chatPage.firstClick = true;
        chatPage.setDr(doctor);
    }
    @FXML
    private void exitBtn(ActionEvent event) {
        DataSource.getInstance().close();
        System.exit(0);
    }

    @FXML
    private void descriptionBtn(ActionEvent event) throws IOException {
        fxmlLoader = loadPage.loadP(event, "../Doctor/drDescription.fxml");
        DrDescription drDescription = fxmlLoader.getController();
        drDescription.setDr(doctor);
    }

    public void setDr(Doctor doctor) throws SQLException {
        this.doctor = doctor;
        Image img = new Image(new ByteArrayInputStream(doctor.getImg()));
        drProImage.setImage(img);
        drNameLabel.setText(doctor.getName() + " " + doctor.getFamilyName());

        ArrayList<Visit> visitList = new ArrayList<>();
        VBox vBox = new VBox();
        visitList = DataSource.getInstance().getVisitsFromDB(doctor.getId());
        for (int i = 0; i < visitList.size() ; i++) {
            VisitMessage visitMessage = new VisitMessage(visitList.get(i), i, doctor);
            vBox.getChildren().add(visitMessage);
        }

        numberLabel.setText(visitList.size() + "");

        scrollPane.setContent(vBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
