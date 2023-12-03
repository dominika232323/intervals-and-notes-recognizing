package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SharedFunctionsController {
    @FXML
    public void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        changeStage(event, "hello-view.fxml");
    }

    @FXML
    public void onLogOutButtonClick(ActionEvent event) throws IOException {
        // Code to navigate back to the main menu
        HelloApplication helloApplication = new HelloApplication();
        helloApplication.start(new Stage());

        // Close the current stage (Stats page)
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void changeStage(ActionEvent event, String fxml_path) throws IOException{
        // Method changes stage to fxml file specified in argument
        Stage stage;
        Scene scene;
        Parent root;

        root = FXMLLoader.load(getClass().getResource(fxml_path));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
