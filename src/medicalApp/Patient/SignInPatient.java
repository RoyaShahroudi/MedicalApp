package medicalApp.Patient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import medicalApp.common.DataSource;
import medicalApp.common.LoadPage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInPatient implements Initializable {

    @FXML private AnchorPane anchorPane;

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

    private String gender = "مذکر";
    private String paImage;
    private FileChooser chooserPaImg = new FileChooser();
    private LoadPage loadPage = new LoadPage();
    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private void paImgBtn(ActionEvent event) {

        File file = chooserPaImg.showOpenDialog(anchorPane.getScene().getWindow());
        chooserPaImg.setTitle("عکس بیمار");
        paImage = file.getPath();
    }

    @FXML
    private void enterBtn(ActionEvent event) throws SQLException, IOException {

        if (paFemaleRB.isSelected()) gender ="مونث";

        if (paNameField.getText().isEmpty() || paFamilyNField.getText().isEmpty() || paEmailField.getText().isEmpty() ||
                paPasswordField.getText().isEmpty() || paBirthD.getText().isEmpty() || paBirthM.getText().isEmpty() ||
                paBirthY.getText().isEmpty()|| paWeightField.getText().isEmpty()
                || paImage == null) {
            errorLabel.setText("تمام قسمت ها را پر کنید.");
        } else if (paEmailField.getText().endsWith("@gmail.com") || paEmailField.getText().endsWith("@yahoo.com")) {
            ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
            try {

                File paImg = new File(paImage);
                FileInputStream fis1 = new FileInputStream(paImg);
                byte[] buf1 = new byte[1024];
                for (int readNum; (readNum = fis1.read(buf1)) != -1; ) {
                    bos1.write(buf1, 0, readNum);
                }
                fis1.close();

            } catch (IOException e) {
                System.out.println("Img in signInPatient: " + e.getMessage());
            }

            Patient patient = new Patient(paNameField.getText(), paFamilyNField.getText(),
                    paEmailField.getText(), paPasswordField.getText(), Integer.parseInt(paBirthD.getText()),
                    Integer.parseInt(paBirthM.getText()), Integer.parseInt(paBirthY.getText()),
                    gender, paWeightField.getText(), bos1.toByteArray());

            DataSource.getInstance().insertPatientInfo(patient);
            patient = DataSource.getInstance().getPaFromDB(patient.getEmail(), patient.getPassword());

            fxmlLoader = loadPage.loadP(event, "../Patient/paVisit.fxml");
            PaVisit paVisit = fxmlLoader.getController();
            paVisit.setPatient(patient);
        } else{
            errorLabel.setText("ایمیل را به درستی وارد کنید.");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
