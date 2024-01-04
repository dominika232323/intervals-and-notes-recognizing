package com.example.demo;
import com.example.demo.jooq.Tables;
import com.example.demo.jooq.tables.records.IntervalsRecord;
import com.example.demo.jooq.tables.records.LevelintervalsRecord;
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
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    void intervalChosenOnClick(ActionEvent event) throws InterruptedException {
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

}

class OngoingIntervalGame{
    private Answers answers;
    private int repetitions;
    private int currentQuestion;
    private LevelintervalsRecord intervalLevel;
    private HashMap<Integer, Answers> answersIntervalsGameMap;
    private List<IntervalsRecord> allIntervalsList;
    private List<NotesRecord> allNotesList;

    public OngoingIntervalGame(LevelintervalsRecord intervalLevel,
                               List<IntervalsRecord> allIntervalsList,
                               List<NotesRecord> allNotesList){
        answers = new Answers();
        repetitions = intervalLevel.getNumberofrepetitions();
        currentQuestion = 1;
        this.intervalLevel = intervalLevel;
        this.allIntervalsList = allIntervalsList;
        this.allNotesList = allNotesList;
        initializeAnswersMap();
    }

    private void initializeAnswersMap(){
        for (IntervalsRecord interval:allIntervalsList){
            answersIntervalsGameMap.put(interval.getIntervalid(), new Answers());
        }
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

class Answers{
    private int answeredCorrectly;
    private int answeredIncorrectly;

    public int getAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(int answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

    public int getAnsweredIncorrectly() {
        return answeredIncorrectly;
    }

    public void setAnsweredIncorrectly(int answeredIncorrectly) {
        this.answeredIncorrectly = answeredIncorrectly;
    }

    public Answers(){
        answeredCorrectly = 0;
        answeredIncorrectly = 0;
    }
}
