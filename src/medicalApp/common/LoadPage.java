package medicalApp.common;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadPage {

    public FXMLLoader loadP(ActionEvent event, String fileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        return fxmlLoader;
    }

}
