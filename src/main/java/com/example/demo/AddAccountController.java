package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    public void onBackToLogInButton(ActionEvent event) throws IOException {
        SharedFunctionsController logOutButton = new SharedFunctionsController();
        logOutButton.onLogOutButtonClick(event);
    }

    @FXML
    public void onExitAppButton()
    {
        Platform.exit();
    }
}
