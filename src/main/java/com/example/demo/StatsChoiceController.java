package com.example.demo;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class StatsChoiceController {
    @FXML
    private Label statsLabel;

    @FXML
    protected void initialize() {
        if (statsLabel != null) {
            statsLabel.setText("Statystyki");
        }
    }

    @FXML
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;

        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onNotesButtonClick(){
        StatsNotesApplication app = new StatsNotesApplication();
        try
        {
            app.start(new Stage());

            Stage currentStage = (Stage)statsLabel.getScene().getWindow();
            currentStage.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onIntervalsButtonClick(){

    }
    // Add other methods for handling actions in the "stats_view.fxml" scene
}
