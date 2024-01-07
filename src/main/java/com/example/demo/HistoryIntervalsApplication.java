package com.example.demo;

import com.example.demo.jooq.tables.records.LevelnotesRecord;
import com.example.demo.jooq.tables.records.UsersRecord;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.demo.jooq.tables.Levelnotes.LEVELNOTES;
import static com.example.demo.jooq.tables.Users.USERS;

public class HistoryIntervalsApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        //REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE
        DSLContext create = null;
        try {
            create = DSL.using(DatabaseConnection.getInstance().getConnection(), SQLDialect.MYSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        LevelnotesRecord levelRecord = (LevelnotesRecord) create.select().
                from(LEVELNOTES).
                where(LEVELNOTES.LEVELID.eq(9)).
                fetchOne();
        ApplicationContext.getInstance().setLevelNotes(levelRecord);
        ApplicationContext.getInstance().setUser((UsersRecord)create.select().from(USERS).where(USERS.USERID.eq(1)).fetchOne());
        //REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE
        FXMLLoader loader = new FXMLLoader(getClass().getResource("history-view_interwaly.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 720, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

