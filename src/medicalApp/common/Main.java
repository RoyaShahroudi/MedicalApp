package medicalApp.common;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import medicalApp.common.DataSource;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("firstPage.fxml"));
        primaryStage.setTitle("Medical App");
        primaryStage.setScene(new Scene(root, 900, 550));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        DataSource.getInstance().open();
        DataSource.getInstance().createTable();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DataSource.getInstance().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
