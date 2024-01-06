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

public class NotesGameApplication extends Application{


    @Override
    public void start(Stage primaryStage) throws IOException{
        //REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE
        DSLContext create = null;
        try {
            create = DSL.using(DatabaseConnection.getInstance().getConnection(), SQLDialect.MYSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        LevelnotesRecord levelRecord = (LevelnotesRecord) create.select().
                from(LEVELNOTES).
                where(LEVELNOTES.LEVELID.eq(46)).
                fetchOne();
        ApplicationContext.getInstance().setLevelNotes(levelRecord);
        ApplicationContext.getInstance().setUser((UsersRecord)create.select().from(USERS).where(USERS.USERID.eq(2)).fetchOne());
        //REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE

        FXMLLoader loader = new FXMLLoader(getClass().getResource("note-game-view.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root, 1000, 668);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(667);
        primaryStage.setScene(scene);
        primaryStage.show();

        NotesGameController.setUpLevelValuesApplicationContext();

        //NotesGameController ngc = new NotesGameController();

    }
}
