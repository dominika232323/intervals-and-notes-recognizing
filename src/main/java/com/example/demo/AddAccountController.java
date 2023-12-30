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

    @FXML
    public void onAddAccountButton(ActionEvent event) throws IOException, SQLException {
        DSLContext create = SharedFunctionsController.getDLCContex();

        String login = enterLogin.getText();
        String password = enterPassword.getText();

        if (checkIfLoginExists(login, create)) {
            incorrectLoginPassword.setText("Login jest już zajęty!!!");
        }
        else {
            int ID = getNextUserID(create);

            create.insertInto(USERS)
                    .columns(USERS.USERID, USERS.NAME, USERS.PASSWORDHASH)
                    .values(ID, login, HashPassword.hashPassword(password))
                    .execute();

            UserTableOperations.setUserInApplicationContext(login, create);

            SharedFunctionsController clickedButton = new SharedFunctionsController();
            clickedButton.changeStage(event, "hello-view.fxml");
        }
    }

    private boolean checkIfLoginExists(String login, DSLContext create) {
        Result<Record> userInfo = UserTableOperations.getUserRecordByLogin(login, create);
        return !userInfo.isEmpty();
    }

    private int getNextUserID(DSLContext create) {
        Result<Record> userInfo = UserTableOperations.getUsersRecords(create);

        int nextID = 1;

        for (Record r : userInfo) {
            int userID = r.get(USERS.USERID);

            if (userID != nextID) {
                return nextID;
            }

            nextID++;
        }

        return nextID;
    }
}
