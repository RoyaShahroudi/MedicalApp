package medicalApp.Patient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import medicalApp.common.ChatPage;
import medicalApp.common.DataSource;
import medicalApp.common.LoadPage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

public class EditProfileInfo implements Initializable {
    @FXML private AnchorPane anchorPane;
    @FXML private ImageView paProImage;
    @FXML private Label nameLabel;

    @FXML private TextField paNameField;
    @FXML private TextField paFamilyNField;
    @FXML private TextField paEmailField;
    @FXML private TextField paPasswordField;
    @FXML private TextField paBirthD;
    @FXML private TextField paBirthM;
    @FXML private TextField paBirthY;
    @FXML private RadioButton paMaleRB;
    @FXML private RadioButton paFemaleRB;
    @FXML private TextField paWeightField;
    @FXML private Label errorLabel;

    private Patient patient;
    private String paImage;
    private FileChooser chooserPaImg = new FileChooser();
    private LoadPage loadPage = new LoadPage();
    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private void visitBtn(ActionEvent event) throws IOException {
        fxmlLoader = loadPage.loadP(event, "../Patient/paVisit.fxml");
        PaVisit paVisit = fxmlLoader.getController();
        paVisit.setPatient(patient);
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
    private void medicationReminder(ActionEvent event) throws SQLException, ParseException {
        try {
            fxmlLoader = loadPage.loadP(event, "../Patient/medicationReminder.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        MedicationReminder medicationReminder = fxmlLoader.getController();
        medicationReminder.setPatient(patient);
    }
    @FXML
    private void searchDrBtn(ActionEvent event) {
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
    private void paImgBtn(ActionEvent event) {

        File file = chooserPaImg.showOpenDialog(anchorPane.getScene().getWindow());
        chooserPaImg.setTitle("عکس بیمار");
        paImage = file.getPath();
    }

    @FXML
    private void enterBtn(ActionEvent event) throws SQLException, IOException {

        String newEmail = null;
        String newPassword = null;
        boolean check = true;
        if (!paNameField.getText().isEmpty()) {
            DataSource.getInstance().updatePaName(paNameField.getText(), patient.getId());
        }
        if (!paFamilyNField.getText().isEmpty()) {
            DataSource.getInstance().updatePaLastN(paFamilyNField.getText(), patient.getId());
        }
        if (!paEmailField.getText().isEmpty()) {
            if (paEmailField.getText().endsWith("@gmail.com") || paEmailField.getText().endsWith("@yahoo.com")) {
                DataSource.getInstance().updatePaEmail(paEmailField.getText(), patient.getId());
                newEmail = paEmailField.getText();
            } else check = false;
        }
        if (!paPasswordField.getText().isEmpty()) {
            DataSource.getInstance().updatePaPassword(paPasswordField.getText(), patient.getId());
            newPassword = paPasswordField.getText();
        }
        if (!paBirthD.getText().isEmpty()) {
            DataSource.getInstance().updatePaBirthD(paBirthD.getText(), patient.getId());
        }
        if (!paBirthM.getText().isEmpty()) {
            DataSource.getInstance().updatePaBirthM(paBirthM.getText(), patient.getId());
        }
        if (!paBirthY.getText().isEmpty()) {
            DataSource.getInstance().updatePaBirthY(paBirthY.getText(), patient.getId());
        }
        if (paMaleRB.isSelected()) {
            DataSource.getInstance().updatePaGender("مذکر", patient.getId());
        }
        if (paFemaleRB.isSelected()) {
            DataSource.getInstance().updatePaGender("مونث", patient.getId());
        }
        if (!paWeightField.getText().isEmpty()) {
            DataSource.getInstance().updatePaWeight(paWeightField.getText(), patient.getId());
        }
        if (paImage != null) {
            ByteArrayOutputStream bos1 = new ByteArrayOutputStream();

            File paImg = new File(paImage);
            FileInputStream fis1 = new FileInputStream(paImg);
            byte[] buf1 = new byte[1024];
            for (int readNum; (readNum = fis1.read(buf1)) != -1; ) {
                bos1.write(buf1, 0, readNum);
            }
            fis1.close();

            DataSource.getInstance().updatePaImg(bos1.toByteArray(), patient.getId());
        }
        if (check == true)
            errorLabel.setText("ویرایش اطلاعات با موفقیت انجام شد.");
        else errorLabel.setText("ایمیل نامعتبر است.");
        //Update information
        patient = DataSource.getInstance().getPaFromDBbyId(patient.getId());

    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        Image img = new Image(new ByteArrayInputStream(patient.getImg()));
        paProImage.setImage(img);
        nameLabel.setText(patient.getName() + " " + patient.getFamilyName());
        if (patient.getGender().equals("مذکر")) paMaleRB.setSelected(true);
        else paFemaleRB.setSelected(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
