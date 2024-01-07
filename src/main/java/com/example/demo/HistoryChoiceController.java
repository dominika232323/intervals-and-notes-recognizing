package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HistoryChoiceController {
    private Label statsLabel;

    @FXML
    protected void initialize() {
        if (statsLabel != null) {
            statsLabel.setText("Statystyki");
        }
    }

    @FXML
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }



    @FXML
    private void onNotesButtonClick(ActionEvent event) throws IOException{
        (new SharedFunctionsController()).changeStage(event, "history-view_nuty.fxml");
    }

    @FXML
    private void onIntervalsButtonClick(ActionEvent event) throws IOException{
        (new SharedFunctionsController()).changeStage(event, "history-view_interwaly.fxml");
    }
    // Add other methods for handling actions in the "stats_view.fxml" scene
}
