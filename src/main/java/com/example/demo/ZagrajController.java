package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class ZagrajController {

    @FXML
    private Label zagrajlabel;

    @FXML
    private Button nutsButton;

    @FXML
    private Button intervalsButton;

    @FXML
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }

    @FXML
    protected void onNutsButtonClick() {
        Platform.runLater(() -> {

            Stage zagrajStage = (Stage) zagrajlabel.getScene().getWindow();
            Stage nutyStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("nuty-view.fxml"));

            try {
                Parent root = loader.load();
                nutyStage.setScene(new Scene(root));
                nutyStage.setWidth(zagrajStage.getWidth());
                nutyStage.setHeight(zagrajStage.getHeight());

            } catch (IOException e) {
                e.printStackTrace();
            }

            zagrajStage.close();
            nutyStage.show();
        });
    }

    @FXML
    protected void onIntervalButtonClick() {
        Platform.runLater(() -> {

            Stage zagrajStage = (Stage) zagrajlabel.getScene().getWindow();
            Stage nutyStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inter-view.fxml"));

            try {
                Parent root = loader.load();
                nutyStage.setScene(new Scene(root));
                nutyStage.setWidth(zagrajStage.getWidth());
                nutyStage.setHeight(zagrajStage.getHeight());

            } catch (IOException e) {
                e.printStackTrace();
            }

            zagrajStage.close();
            nutyStage.show();
        });
    }

    private void openNutyWindow() {
        try {
            NutyApplication nutyApp = new NutyApplication();
            Stage stage = new Stage();
            nutyApp.start(stage);

            // Close the Zagraj window
            Stage currentStage = (Stage) nutsButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openIntervalWindow() {
        try {
            NutyApplication nutyApp = new NutyApplication();
            Stage stage = new Stage();
            nutyApp.start(stage);

            Stage currentStage = (Stage) intervalsButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
