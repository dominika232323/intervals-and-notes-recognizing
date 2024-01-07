package com.example.demo;
import com.example.demo.jooq.Tables;
import com.example.demo.jooq.tables.records.IntervalsRecord;
import com.example.demo.jooq.tables.records.LevelintervalsRecord;
import com.example.demo.jooq.tables.records.NotesRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class IntervalsGameController {
    @FXML
    private Label current_question_label;

    @FXML
    private Label incorrect_answers_label;

    @FXML
    private Label correct_answers_label;

    private List<IntervalsRecord> allIntervalsList;
    private List<NotesRecord> allNotesList;
    private NotesPlayer notesPlayer;

    private OngoingIntervalGame currentGame;

    private enum IntervalTypeEnum {UP, DOWN, TOGETHER}

    private IntervalTypeEnum intervalType;

    @FXML
    void intervalChosenOnClick(ActionEvent event) throws InterruptedException {
        Button button = (Button) event.getSource();
        System.out.println(button.getText());

        if (notesPlayer.isAvailable()) {
            notesPlayer.playNotesSequentially(allNotesList);
        }
    }

    @FXML
    void exitOnClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "inter-view.fxml");
    }

    @FXML
    void exitAndSaveOnClick(ActionEvent event) {

    }

    private void playChosenInterval(){
        switch (intervalType){
            case UP:
                notesPlayer.playNotesSequentially(new ArrayList<>(Arrays.asList(
                        currentGame.getChosenLowerNote(), currentGame.getChosenHigherNote())));
                break;
            case DOWN:
                notesPlayer.playNotesSequentially(new ArrayList<>(Arrays.asList(
                        currentGame.getChosenHigherNote(), currentGame.getChosenLowerNote())));
                break;
            default:
                notesPlayer.playNotesSimultaneously(new ArrayList<>(Arrays.asList(
                        currentGame.getChosenLowerNote(), currentGame.getChosenHigherNote())));
                break;
        }
    }

    @FXML
    void nextQuestionOnClick(ActionEvent event) {
        if (canNextQuestion()){
            currentGame.prepareNextQuestion();
            playChosenInterval();
        }
    }

    boolean canNextQuestion(){
        return true;
    }

    public void initialize() throws SQLException {
        // getting connection with database
        Connection connection = DatabaseConnection.getInstance().getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        // initializing allIntervalsList and allNotesList
        allIntervalsList = create.selectFrom(Tables.INTERVALS).fetch();
        allNotesList = create.selectFrom(Tables.NOTES).fetch();

        notesPlayer = new NotesPlayer();

        // TODO change it later to level from context
        Byte zero = 0;
        Byte one = 1;
        LevelintervalsRecord testLevel = new LevelintervalsRecord(2137, 1, "Test", 20, one, zero, zero);

        currentGame = new OngoingIntervalGame(testLevel, allIntervalsList, allNotesList);

        if (testLevel.getUp() == 1){
            intervalType = IntervalTypeEnum.UP;
        } else if (testLevel.getDown() == 1) {
            intervalType = IntervalTypeEnum.DOWN;
        } else {
            intervalType = IntervalTypeEnum.TOGETHER;
        }

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
    private IntervalsRecord chosenInterval;
    private NotesRecord chosenHigherNote;
    private NotesRecord chosenLowerNote;


    public OngoingIntervalGame(LevelintervalsRecord intervalLevel,
                               List<IntervalsRecord> allIntervalsList,
                               List<NotesRecord> allNotesList){
        answers = new Answers();
        repetitions = intervalLevel.getNumberofrepetitions();
        currentQuestion = 0;
        this.intervalLevel = intervalLevel;
        this.allIntervalsList = allIntervalsList;
        this.allNotesList = allNotesList;
        answersIntervalsGameMap = new HashMap<Integer, Answers>();
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

    private void changeRandomIntervalAndNotes(){
        chosenInterval = chooseRandomInterval();
        chosenHigherNote = getRandomHigherNoteFromInterval(chosenInterval);
        chosenLowerNote = getLowerNoteFromHigherNote(chosenInterval, chosenHigherNote);
    }

    public void prepareNextQuestion(){
        changeRandomIntervalAndNotes();
        currentQuestion++;
    }

    public Answers getAnswers() {
        return answers;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public IntervalsRecord getChosenInterval() {
        return chosenInterval;
    }

    public NotesRecord getChosenHigherNote() {
        return chosenHigherNote;
    }

    public NotesRecord getChosenLowerNote() {
        return chosenLowerNote;
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

class NotesPlayer{

    List<MediaPlayer> players;

    public NotesPlayer(){
        players = new ArrayList<MediaPlayer>();
    }

    public boolean isAvailable(){
        for (MediaPlayer player: players){
            if (player.getStatus().equals(MediaPlayer.Status.PLAYING)){
                return false;
            }
        }
        return true;
    }

    private String getNoteSoundPath(NotesRecord note){
        String noteName = note.getNotename();
        String path;
        // if note contains '#' or 'b'
        if (noteName.length() > 2){
            path = noteName.substring(0, 3);
        }else {
            path = noteName;
        }

        return path + ".wav";
    }

    public void playNotesSequentially(List<NotesRecord> notesToPlay){
        players = new ArrayList<MediaPlayer>();
        for (NotesRecord note:notesToPlay){
            players.add(new MediaPlayer(new Media(getClass()
                    .getResource("notes_sounds/" + getNoteSoundPath(note)).toExternalForm())));
        }

        for (int i = 0; i < players.size() - 1; i++) {
            final MediaPlayer player     = players.get(i);
//            final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
            final MediaPlayer nextPlayer = players.get(i + 1);
            player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    nextPlayer.play();
                }
            });
        }
        players.get(0).play();
    }

    public void playNotesSimultaneously(List<NotesRecord> notesToPlay){
        players = new ArrayList<MediaPlayer>();
        for (NotesRecord note:notesToPlay){
            players.add(new MediaPlayer(new Media(getClass()
                    .getResource("notes_sounds/" + getNoteSoundPath(note)).toExternalForm())));
        }

        for (MediaPlayer player: players){
            player.play();
        }
    }


    }



