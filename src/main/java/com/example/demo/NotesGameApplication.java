package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NotesGameApplication extends Application{
    private static int startingWave = 1;
    private static int endingWave = 10;
    private static int repetitionsNextWave = 5;
    private static int lowerNoteBound = 25;
    private static int higherNoteBound = 49;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("note-game-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1000, 668);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(667);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
