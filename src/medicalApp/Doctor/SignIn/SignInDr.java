package medicalApp.Doctor.SignIn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import medicalApp.Doctor.Doctor;
import medicalApp.Doctor.Visit.DrVisit;
import medicalApp.common.DataSource;
import medicalApp.common.LoadPage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInDr implements Initializable {

    @FXML private AnchorPane anchorPane;

    @FXML private TextField drNameField;
    @FXML private TextField drFamilyNField;
    @FXML private TextField drEmailField;
    @FXML private TextField drPasswordField;
    @FXML private TextField drBirthD;
    @FXML private TextField drBirthM;
    @FXML private TextField drBirthY;
    @FXML private ComboBox<String> specialtyCombo;
    @FXML private TextField drMSNoField;

    @FXML private Label errorLabel;

    private String msImage;
    private String image;
    private FileChooser chooserMSImg = new FileChooser();
    private FileChooser chooserDrImg = new FileChooser();
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private LoadPage loadPage = new LoadPage();

    @FXML
    private void MSImg(ActionEvent event) {
        File file = chooserMSImg.showOpenDialog(anchorPane.getScene().getWindow());
        chooserMSImg.setTitle("عکس از کارت نظام پزشکی");
       msImage = file.getPath();
    }

    @FXML
    private void drImg(ActionEvent event) {
        File file = chooserDrImg.showOpenDialog(anchorPane.getScene().getWindow());
        chooserDrImg.setTitle("عکس پزشک");
        image = file.getPath();
    }

    @FXML
    private void enterBtn(ActionEvent event) throws SQLException, IOException {

        if (drNameField.getText().isEmpty() || drFamilyNField.getText().isEmpty()  || drEmailField.getText().isEmpty()  ||
        drPasswordField.getText().isEmpty()  || drBirthD.getText().isEmpty() || drBirthM.getText().isEmpty() ||
        drBirthY.getText().isEmpty() || drMSNoField.getText().isEmpty() ||
        specialtyCombo.getSelectionModel().isEmpty() || msImage == null || image == null) {
            errorLabel.setText("تمام قسمت ها را پر کنید.");

        }else if (drEmailField.getText().endsWith("@gmail.com") || drEmailField.getText().endsWith("@yahoo.com")) {
            ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
            ByteArrayOutputStream bos2 = new ByteArrayOutputStream();

            try {

                File msImg = new File(msImage);
                FileInputStream fis1 = new FileInputStream(msImg);
                byte[] buf1 = new byte[1024];
                for (int readNum; (readNum = fis1.read(buf1)) != -1;) {
                    bos1.write(buf1, 0, readNum);
                }

                File img = new File(image);
                FileInputStream fis2 = new FileInputStream(img);
                byte[] buf2 = new byte[1024];
                for (int readNum; (readNum = fis2.read(buf2)) != -1;) {
                    bos2.write(buf2, 0, readNum);
                }

                fis1.close();
                fis2.close();
            } catch (IOException e) {
                System.out.println("MSImg in enterDoctorBtn: " + e.getMessage());
            }


            Doctor doctor = new Doctor(drNameField.getText(), drFamilyNField.getText(),
                    drEmailField.getText(), drPasswordField.getText(), Integer.parseInt(drBirthD.getText()),
                    Integer.parseInt(drBirthM.getText()), Integer.parseInt(drBirthY.getText()),
                    specialtyCombo.getValue(), drMSNoField.getText(), bos1.toByteArray(), bos2.toByteArray());

            DataSource.getInstance().insertDoctorInfo(doctor);
            fxmlLoader = loadPage.loadP(event, "../Doctor/Visit/drVisit.fxml");
            DrVisit drVisit = fxmlLoader.getController();
            drVisit.setDr(doctor);

        } else{
            errorLabel.setText("ایمیل را به درستی وارد کنید.");
        }
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

        specialtyCombo.setItems(specialtyList);
    }
}
