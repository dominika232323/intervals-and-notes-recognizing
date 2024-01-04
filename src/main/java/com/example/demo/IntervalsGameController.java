package com.example.demo;
import com.example.demo.jooq.Tables;
import com.example.demo.jooq.tables.records.IntervalsRecord;
import com.example.demo.jooq.tables.records.NotesRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class IntervalsGameController {
    @FXML
    private Label current_question_label;

    @FXML
    private Label incorrect_answers_label;

    @FXML
    private Label correct_answers_label;

    private List<IntervalsRecord> allIntervalsList;
    private List<NotesRecord> allNotesList;

    @FXML
    void intervalChosenOnClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        System.out.println(button.getText());
    }

    @FXML
    void exitOnClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "inter-view.fxml");
    }

    @FXML
    void exitAndSaveOnClick(ActionEvent event) {

    }

    public void initialize() throws SQLException {
        // getting connection with database
        Connection connection = DatabaseConnection.getInstance().getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        // initializing allIntervalsList and allNotesList
        allIntervalsList = create.selectFrom(Tables.INTERVALS).fetch();
        allNotesList = create.selectFrom(Tables.NOTES).fetch();

    }

    private IntervalsRecord chooseRandomInterval(){
        Random random = new Random();
        int randomIndex = random.nextInt(allIntervalsList.size());
        return allIntervalsList.get(randomIndex);
    }

    private NotesRecord getRandomHigherNoteFromInterval(IntervalsRecord interval){
        int semitones = interval.getIntervalid() - 1;
        Random random = new Random();
        int randomIndex = random.nextInt(allIntervalsList.size() - semitones) + semitones;

        return allNotesList.get(randomIndex);
    }

    private NotesRecord getLowerNoteFromHigherNote(IntervalsRecord interval, NotesRecord highNote){
        int semitones = interval.getIntervalid() - 1;
        return allNotesList.get(highNote.getNoteid() - 1 - semitones);
    }

}
