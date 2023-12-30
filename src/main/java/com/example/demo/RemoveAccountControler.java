package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.demo.jooq.tables.Users.USERS;

public class RemoveAccountControler {
    @FXML
    private TextField enterLogin;
    @FXML
    private PasswordField enterPassword;
    @FXML
    private Label incorrectLoginPassword;
    @FXML
    private Button removeAccountButton;
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

    @FXML
    public void onRemoveAccountButton(ActionEvent event) throws IOException, SQLException {
        DSLContext create = SharedFunctionsController.getDLCContex();

        String login = enterLogin.getText();
        String password = enterPassword.getText();

        Result<Record> userInfo = UserTableOperations.getUserRecordByLogin(login, create);

        if (UserTableOperations.checkIfLoginExists(userInfo)) {
            Record r = userInfo.get(0);

            int userID = r.get(USERS.USERID);
            String userName = r.get(USERS.NAME);
            String userHash = r.get(USERS.PASSWORDHASH);

            String passwordHash = HashPassword.hashPassword(password);

            if (passwordHash.equals(userHash)) {
                UserTableOperations.deleteUser(userID, create);
                incorrectLoginPassword.setText("Konto " + login + " zostało usunięte.");
            }
            else {
                incorrectLoginPassword.setText("Niepoprawne hasło!!!");
            }
        }
        else {
            incorrectLoginPassword.setText("Konto " + login + " nie istnieje!!!");
        }
    }
}
