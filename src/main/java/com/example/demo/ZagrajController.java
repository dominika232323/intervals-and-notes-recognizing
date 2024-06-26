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
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("nuty-view.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) zagrajlabel.getScene().getWindow();

            Stage nutyStage = new Stage();
            nutyStage.setTitle("Choose notes game level");
            nutyStage.setScene(new Scene(root));
            nutyStage.setWidth(currentStage.getWidth());
            nutyStage.setHeight(currentStage.getHeight());

            currentStage.close();

            nutyStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onIntervalButtonClick() {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inter-view.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) zagrajlabel.getScene().getWindow();

            Stage nutyStage = new Stage();
            nutyStage.setTitle("Choose interval game level");
            nutyStage.setScene(new Scene(root));
            nutyStage.setWidth(currentStage.getWidth());
            nutyStage.setHeight(currentStage.getHeight());

            currentStage.close();

            nutyStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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
