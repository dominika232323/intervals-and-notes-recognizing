package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class HistoriaController {
    @FXML
    private Label historyLabel;

//    @FXML
//    protected void initialize() {
//        if (historyLabel != null) {
//            historyLabel.setText("Historia gier");
//        }
//    }

    @FXML
    private void onBackToMenuButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Menu");
            stage.setScene(new Scene(root, 700, 500));

            Stage currentStage = (Stage) historyLabel.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Add other methods for handling actions in the "stats_view.fxml" scene
}
