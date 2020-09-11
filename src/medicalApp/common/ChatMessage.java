package medicalApp.common;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ChatMessage extends HBox {

    public ChatMessage(int index, Chat chat) {
        if (chat.getSender().equals("patient")) {
            //pane
            Pane mainPane = new Pane();
            mainPane.setPrefWidth(300);
            mainPane.setPrefHeight(90);
            mainPane.setLayoutX(0);
            mainPane.setLayoutY(index * 90);
            mainPane.setStyle("-fx-background-color:  #354552;" +
                    "-fx-border-radius: 14;" +
                    "-fx-background-radius: 20;" +
                    "-fx-border-color: #243441;" +
                    "-fx-border-width: 7;");

            Label label = new Label(chat.getMessage());

            label.setAlignment(Pos.CENTER_LEFT);
            label.setLayoutX(15);
            label.setLayoutY(15);
            label.setWrapText(true);
            label.setMaxWidth(260);
            label.setMaxHeight(59);
            label.setStyle("-fx-text-fill: white;");

            mainPane.getChildren().add(label);
            getChildren().add(mainPane);
        } else {

            //pane
            Pane mainPane = new Pane();
            mainPane.setPrefWidth(300);
            mainPane.setPrefHeight(90);
            mainPane.setLayoutX(100);
            mainPane.setLayoutY(index * 90);
            mainPane.setStyle("-fx-background-color:   #132330;" +
                    "-fx-border-radius: 14;" +
                    "-fx-background-radius: 20;" +
                    "-fx-border-color: #243441;" +
                    "-fx-border-width: 7;");

            Label label = new Label(chat.getMessage());

            label.setAlignment(Pos.CENTER_LEFT);
            label.setLayoutX(15);
            label.setLayoutY(15);
            label.setWrapText(true);
            label.setMaxWidth(260);
            label.setMaxHeight(59);
            label.setStyle("-fx-text-fill: white;");

            mainPane.getChildren().add(label);
            getChildren().add(mainPane);

        }

    }
}
