package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
public class HelloController {

    @FXML
    private Button statystykiButton;

    @FXML
    private Button wylogujButton;

    @FXML
    private Button zagrajButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button levelCreatorButton;

    @FXML
    protected void onWylogujButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("end_view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Wyloguj");
            stage.setScene(new Scene(root, 700, 500));

            Stage currentStage = (Stage) wylogujButton.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onStatystykiButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("wybierz-stat-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Wybor Statystyk");
            stage.setScene(new Scene(root, 700, 500));

            Stage currentStage = (Stage) statystykiButton.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onHistoryButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("history-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Historia gier");
            stage.setScene(new Scene(root, 700, 500));

            Stage currentStage = (Stage) historyButton.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onZagrajButtonClick() {
        try {
            // Load the Zagraj FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("zagraj-view.fxml"));
            Parent root = loader.load();

            // Create a new stage with a fixed size
            Stage stage = new Stage();
            stage.setTitle("Zagraj");
            stage.setScene(new Scene(root, 700, 500)); // Set a fixed size for the scene

            // Close the current stage (Main Menu)
            Stage currentStage = (Stage) zagrajButton.getScene().getWindow();
            currentStage.close();

            // Show the Zagraj stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void levelCreatorOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("level-creator-view.fxml"));
        Parent root = loader.load();

        // Create a new stage with a fixed size
        Stage stage = new Stage();
        stage.setTitle("Kreator poziomów");
        stage.setScene(new Scene(root, 700, 500)); // Set a fixed size for the scene

        // Close the current stage (Main Menu)
        Stage currentStage = (Stage) levelCreatorButton.getScene().getWindow();
        currentStage.close();

        // Show the LevelCreator stage
        stage.show();
    }

//    @FXML
//    protected void onZagrajButtonClick() {
//        ZagrajApplication zagrajApplication = new ZagrajApplication();
//        try {
//            zagrajApplication.start(new Stage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
