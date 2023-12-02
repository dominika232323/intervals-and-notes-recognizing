package com.example.demo;

import javafx.event.ActionEvent;
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

import java.io.IOException;

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
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        sharedFunctionsController menuButton = new sharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }
    // Add other methods for handling actions in the "stats_view.fxml" scene
}
