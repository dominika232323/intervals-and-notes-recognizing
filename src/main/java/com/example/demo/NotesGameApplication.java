package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class NotesGameApplication extends Application{

    private int level_id = 1;
    private static int startingWave = 1;
    private static int endingWave = 10;
    private static int repetitionsNextWave = 5;
    private static int lowerNoteBound = 25;
    private static int higherNoteBound = 49;

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("note-game-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1000, 668);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(667);
        primaryStage.setScene(scene);
        primaryStage.show();

        NotesGameController ngc = new NotesGameController();
        NotesGameController.setUpLevelValues(1);
        System.out.println(NotesGameController.getHigherNoteBound());
    }
}
