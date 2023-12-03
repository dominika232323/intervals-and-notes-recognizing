package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateLevelNotesApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("create-level-notes-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
