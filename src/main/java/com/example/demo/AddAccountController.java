package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAccountController {
    @FXML
    private TextField enterLogin;

    @FXML
    private PasswordField enterPassword;

    @FXML
    private Label incorrectLoginPassword;

    @FXML
    private Button addAccountButton;

    @FXML
    private Button exitAppButton;

    @FXML
    private Button backToLogInButton;

    @FXML
    public void onBackToLogInButton()
    {
        // Code to navigate back to the main menu
        HelloApplication helloApplication = new HelloApplication();
        try {
            helloApplication.start(new Stage());

            // Close the current stage (Stats page)
            Stage currentStage = (Stage) backToLogInButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onExitAppButton()
    {
        Platform.exit();
    }
}
