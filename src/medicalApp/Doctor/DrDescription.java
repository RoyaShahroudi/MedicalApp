package medicalApp.Doctor;

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
import medicalApp.Doctor.Visit.DrVisit;
import medicalApp.common.ChatPage;
import medicalApp.common.DataSource;
import medicalApp.common.LoadPage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DrDescription implements Initializable {

    @FXML private ImageView drProImage;
    @FXML private Label nameLabel;
    @FXML private TextArea descriptionArea;

    private Doctor doctor;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private LoadPage loadPage = new LoadPage();
    @FXML
    private void visitBtn(ActionEvent event) throws IOException, SQLException {
//        loadPage(event, "drVisit.fxml");
//        fxmlLoader = loadPage.loadP(event, "../Doctor/Visit/drVisit.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Doctor/Visit/drVisit.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        DrVisit drVisit = fxmlLoader.getController();
        drVisit.setDr(doctor);

    }


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
    private void enterBtn(ActionEvent event) throws SQLException, IOException {
        DataSource.getInstance().updateDrDescription(descriptionArea.getText(), doctor.getId());
        doctor.setDescription(descriptionArea.getText());

        fxmlLoader = loadPage.loadP(event, "../Doctor/drDescription.fxml");
        DrDescription drDescription = fxmlLoader.getController();
        drDescription.setDr(doctor);
    }

    public void setDr(Doctor doctor) {
        this.doctor = doctor;
        Image img = new Image(new ByteArrayInputStream(doctor.getImg()));
        drProImage.setImage(img);
        nameLabel.setText(doctor.getName() + " " + doctor.getFamilyName());
        descriptionArea.setText(doctor.getDescription());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}

