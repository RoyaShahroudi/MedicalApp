package medicalApp.Patient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import medicalApp.Doctor.Doctor;
import medicalApp.common.ChatPage;
import medicalApp.common.DataSource;
import medicalApp.common.LoadPage;
import medicalApp.common.Visit;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class PaVisit implements Initializable {
    @FXML private AnchorPane anchorPane;
    @FXML private ImageView paProImage;
    @FXML private Label nameLabel;
    @FXML private ComboBox<String> drSpecialityCombo;
    @FXML private ComboBox<String> drNameCombo;
    @FXML private TextArea visitDescription;
    @FXML private Label errorLabel;

    private Patient patient;
    private FileChooser fileChooser = new FileChooser();
    private String filePath;
    private LoadPage loadPage = new LoadPage();
    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private void editProBtn(ActionEvent event) {
        try {
            fxmlLoader = loadPage.loadP(event, "../Patient/editProfileInfo.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        EditProfileInfo editProfileInfo = fxmlLoader.getController();
        editProfileInfo.setPatient(patient);
    }
    @FXML
    private void chatBtn(ActionEvent event) {
        try {
            fxmlLoader = loadPage.loadP(event, "../common/chatPage.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChatPage chatPage = fxmlLoader.getController();
        chatPage.user = "patient";
        chatPage.firstClick = true;
        chatPage.setPatient(patient);


    }
    @FXML
    private void medicationReminder(ActionEvent event) throws IOException, SQLException, ParseException {
        fxmlLoader = loadPage.loadP(event, "../Patient/medicationReminder.fxml");
        MedicationReminder medicationReminder = fxmlLoader.getController();
        medicationReminder.setPatient(patient);
    }
    @FXML
    private void searchDrBtn(ActionEvent event){
        try {
            fxmlLoader = loadPage.loadP(event, "../Patient/searchDr.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchDr searchDr = fxmlLoader.getController();
        searchDr.setPatient(patient);
    }
    @FXML
    private void exitBtn(ActionEvent event) {
        DataSource.getInstance().close();
        System.exit(0);
    }

    @FXML
    private void drSpecialityCombo(ActionEvent event) throws SQLException {

        List<String> nameList = DataSource.getInstance().setDrNameCombo(drSpecialityCombo.getValue());

        if (nameList != null) {
            ObservableList<String> observableList = FXCollections.observableList(nameList);

            drNameCombo.setItems(observableList);

            drNameCombo.setDisable(false);
        }
    }

    @FXML
    private void fileVisit(ActionEvent event) {
        File file = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        filePath = file.getPath();

    }

    @FXML
    private void sendBtn(ActionEvent event) throws SQLException {
        int drId = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (drSpecialityCombo.getValue().isEmpty() ||
                drNameCombo.getValue().isEmpty()) {

        } else if (filePath != null) {
            String str = drNameCombo.getValue();
            String[] arrStr = str.split("  ");
//            System.out.println(arrStr[0] + arrStr[1]);
            drId = DataSource.getInstance().findDrId(arrStr[0], arrStr[1]);

            try {
                File vfile = new File(filePath);
                FileInputStream fis = new FileInputStream(vfile);
                byte[] buf = new byte[1024];
                for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                    bos.write(buf, 0, readNum);
                }
                fis.close();

            } catch (IOException e) {
                System.out.println("Img in signInPatient: " + e.getMessage());
            }
            Visit visit = new Visit(patient.getId(), drId,
                    visitDescription.getText(), bos.toByteArray());
            DataSource.getInstance().insertVisit(visit);
        } else {
            String str = drNameCombo.getValue();
            String[] arrStr = str.split("  ");
//            System.out.println(arrStr[0] + arrStr[1]);
            drId = DataSource.getInstance().findDrId(arrStr[0], arrStr[1]);
            Visit visit = new Visit(patient.getId(), drId,
                    visitDescription.getText(), null);
            DataSource.getInstance().insertVisit(visit);
        }

        errorLabel.setText("درخواست ویزیت ارسال شد.");

    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        Image img = new Image(new ByteArrayInputStream(patient.getImg()));
        paProImage.setImage(img);
        nameLabel.setText(patient.getName() + " " + patient.getFamilyName());
    }

    ObservableList<String> specialtyList = FXCollections.observableArrayList(
            "متخصص پوست",
            "متخصص داخلی" ,
            "متخصص دهان و فک و صورت" ,
            "متخصص قلب و عروق" ,
            "متخصص بیهوشی" ,
            "متخصص دهان و دندان" ,
            "متخصص جراحی عمومی" ,
            "متخصص چشم" ,
            "متخصص کودکان" ,
            "فوق تخصص ریه" ,
            "متخصص مغز و اعصاب"
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        drSpecialityCombo.setItems(specialtyList);

    }
}
