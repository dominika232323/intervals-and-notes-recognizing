package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



import java.sql.DriverManager;
import java.sql.Connection;

import java.io.IOException;
import java.sql.SQLException;

public class HistoriaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("history-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try
        {
            String url = "jdbc:mysql://localhost:3306/db";
            Connection conn = DriverManager.getConnection(url);
        }
        catch(SQLException e)
        {

        }
        launch();
    }
}
