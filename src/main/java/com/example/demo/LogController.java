package com.example.demo;

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
    public void onLogInButtonClick()
    {
        try
        {
            boolean correctLogIn = checkLogIn();

            if(correctLogIn)
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Menu");
                stage.setScene(new Scene(root, 700, 500));

                Stage currentStage = (Stage) logInButton.getScene().getWindow();
                currentStage.close();

                stage.show();
            }
            else
            {
                incorrectLoginPassword.setText("Incorrect login or password!!!");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private boolean checkLogIn()
    {
        if(enterLogin.getText().toString().equals("guest") && enterPassword.getText().toString().equals("guest"))
        {
            return true;
        }
        return false;
    }
}
