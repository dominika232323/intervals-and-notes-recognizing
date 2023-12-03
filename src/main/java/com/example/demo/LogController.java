package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
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

public class LogController {
    @FXML
    private TextField enterLogin;

    @FXML
    private PasswordField enterPassword;

    @FXML
    private Label incorrectLoginPassword;

    @FXML
    private Button logInButton;

    @FXML
    private Button addAccountButton;

    @FXML
    private Button deleteAccountButton;

    @FXML
    private Button exitAppButton;

    @FXML
    public void onLogInButtonClick(ActionEvent event) throws IOException {
        boolean correctLogIn = checkLogIn();

        if(correctLogIn)
        {
            SharedFunctionsController clickedButton = new SharedFunctionsController();
            clickedButton.changeStage(event, "hello-view.fxml");
        }
        else
        {
            incorrectLoginPassword.setText("Niepoprawny login lub hasło!!!");
        }
    }

    private boolean checkLogIn()
    {
        return enterLogin.getText().toString().equals("guest") && enterPassword.getText().toString().equals("guest");
    }

    @FXML
    public void onAddAccountButton(ActionEvent event) throws IOException {
        SharedFunctionsController clickedButton = new SharedFunctionsController();
        clickedButton.changeStage(event, "add-account-view.fxml");
    }

    @FXML
    public void onRemoveAccountButton(ActionEvent event) throws IOException {
        SharedFunctionsController clickedButton = new SharedFunctionsController();
        clickedButton.changeStage(event, "remove-account-view.fxml");
    }

    @FXML
    public void onExitAppButton()
    {
        Platform.exit();
    }
}
