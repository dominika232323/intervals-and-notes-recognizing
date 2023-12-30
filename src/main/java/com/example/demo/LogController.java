package com.example.demo;

import com.example.demo.jooq.Tables;
import com.example.demo.jooq.tables.Notes;
import com.example.demo.jooq.tables.Users;
import static com.example.demo.jooq.tables.Users.USERS;
import static com.example.demo.jooq.tables.Notes.NOTES;
import com.example.demo.jooq.tables.records.UsersRecord;
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
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LogController {
    private int userID = 0;
    private String userName = "";
    private String userHash = "";

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
    public void onLogInButtonClick(ActionEvent event) throws IOException, SQLException {
        boolean correctLogIn = checkLogIn();

        if(correctLogIn)
        {
            UserTableOperations.setUserInApplicationContext(userID, userName, userHash);

            SharedFunctionsController clickedButton = new SharedFunctionsController();
            clickedButton.changeStage(event, "hello-view.fxml");
        }
        else
        {
            incorrectLoginPassword.setText("Niepoprawny login lub hasło!!!");

            userID = 0;
            userName = "";
            userHash = "";
        }
    }

    private boolean checkLogIn() throws SQLException {
        DSLContext create = SharedFunctionsController.getDLCContex();

        String login = enterLogin.getText();
        String password = enterPassword.getText();

        Result<Record> userInfo = UserTableOperations.getUserRecordByLogin(login, create);

        if (userInfo.size() == 1) {
            Record r = userInfo.get(0);

            userID = r.get(USERS.USERID);
            userName = r.get(USERS.NAME);
            userHash = r.get(USERS.PASSWORDHASH);
        }
        else {
            return false;
        }

        String passwordHash = HashPassword.hashPassword(password);
        return passwordHash.equals(userHash);
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
