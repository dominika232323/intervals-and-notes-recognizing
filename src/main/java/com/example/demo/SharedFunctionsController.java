package com.example.demo;

import com.example.demo.jooq.tables.records.UsersRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static com.example.demo.jooq.tables.Users.USERS;

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

    static public DSLContext getDLCContex() throws SQLException  {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        return DSL.using(connection, SQLDialect.MYSQL);
    }
}
