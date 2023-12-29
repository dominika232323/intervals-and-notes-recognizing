package com.example.demo;

import com.example.demo.jooq.Tables;
import com.example.demo.jooq.tables.Users;
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
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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

    public LogController() throws SQLException {
    }

    @FXML
    public void onLogInButtonClick(ActionEvent event) throws IOException {
        boolean correctLogIn = checkLogIn();

        if(correctLogIn)
        {
            // For test purposes only, implement correct version later
            UsersRecord guest = new UsersRecord(1, "guest", "hash123");
            ApplicationContext context = ApplicationContext.getInstance();
            context.setUser(guest);

            SharedFunctionsController clickedButton = new SharedFunctionsController();
            clickedButton.changeStage(event, "hello-view.fxml");
        }
        else
        {
            incorrectLoginPassword.setText("Niepoprawny login lub hasło!!!");
        }
    }

    private boolean checkLogIn() throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        String login = enterLogin.getText();
        String password = enterPassword.getText();

        Result<Record> userInfo = create.select().from(Tables.USERS).where(Tables.USERS.NAME.like(login)).fetch();

        for (Record r : userInfo) {
            int userID = r.get(Tables.USERS.USERID);
            String userName = r.get(Tables.USERS.NAME);
            String userHash = r.get(Tables.USERS.PASSWORDHASH);
//            String userSalt = r.get(Tables.USERS.SALT);
        }

        return true;
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
