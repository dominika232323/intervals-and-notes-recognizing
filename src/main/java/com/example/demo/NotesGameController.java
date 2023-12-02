package com.example.demo;

import static com.example.demo.jooq.tables.Answersnotesgame.ANSWERSNOTESGAME;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;

import java.io.IOException;
import java.sql.SQLException;

public class NotesGameController {
    @FXML
    ImageView ImageView_Klucz;

    @FXML
    Button C1;

    @FXML
    private void onBackToMenuButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Menu");
            stage.setScene(new Scene(root, 700, 500));

            Stage currentStage = (Stage) C1.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateClefImage(String note) {
        Image clefImage;
        if (isBassClef(note)) {
            clefImage = new Image(getClass().getResourceAsStream("bass_clef.png"));
        } else {
            clefImage = new Image(getClass().getResourceAsStream("violin_clef.png"));
        }
        ImageView_Klucz.setImage(clefImage);
    }

    private boolean isBassClef(String note) {
        // Assuming note is a string like "C4", "D#3", etc.
        // Implement logic to determine if it's in the bass clef range (C2 to B3)
        // Return true if it's in the bass clef range, false otherwise
        // ...
        return false;
    }

    // Assuming you have a JOOQ context set up
// ...

    public void updateNoteStatistics(int notesGameID, int noteID, boolean guessedCorrectly) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db";
        String username = "username";
        String password = "password";

        Connection connection = DriverManager.getConnection(url, username, password);

        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
        // Check if the note already exists for the given game
        Record record = (Record) create.select()
                .from(ANSWERSNOTESGAME)
                .where(ANSWERSNOTESGAME.NOTESGAMEID.eq(notesGameID)
                        .and(ANSWERSNOTESGAME.NOTEID.eq(noteID)))
                .fetchOne();

        if (record != null) {
            // Update the existing record
            create.update(ANSWERSNOTESGAME)
                    .set(ANSWERSNOTESGAME.NOTEOCCURRENCES, ANSWERSNOTESGAME.NOTEOCCURRENCES.plus(1))
                    .set(ANSWERSNOTESGAME.NOTEGUESSEDCORRECTLY, guessedCorrectly ? ANSWERSNOTESGAME.NOTEGUESSEDCORRECTLY.plus(1) : ANSWERSNOTESGAME.NOTEGUESSEDCORRECTLY)
                    .where(ANSWERSNOTESGAME.NOTESGAMEID.eq(notesGameID)
                            .and(ANSWERSNOTESGAME.NOTEID.eq(noteID)))
                    .execute();
        } else {
            // Insert a new record
            create.insertInto(ANSWERSNOTESGAME, ANSWERSNOTESGAME.NOTESGAMEID, ANSWERSNOTESGAME.NOTEID, ANSWERSNOTESGAME.NOTEOCCURRENCES, ANSWERSNOTESGAME.NOTEGUESSEDCORRECTLY)
                    .values(notesGameID, noteID, 1, guessedCorrectly ? 1 : 0)
                    .execute();
        }
    }
}
