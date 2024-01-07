package com.example.demo;
import com.example.demo.jooq.Tables;
import com.example.demo.jooq.tables.records.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class IntervalsGameController {

    @FXML
    private Label errorLabel;

    @FXML
    private Label current_question_label;

    @FXML
    private Label incorrect_answers_label;

    @FXML
    private Label correct_answers_label;

    @FXML
    private HBox firstIntervalsHbox;

    @FXML
    private HBox secondIntervalsHbox;

    @FXML
    private HBox thirdIntervalsHbox;

    private List<IntervalsRecord> allIntervalsList;
    private List<NotesRecord> allNotesList;
    private NotesPlayer notesPlayer;

    private OngoingIntervalGame currentGame;

    private enum IntervalTypeEnum {UP, DOWN, TOGETHER}

    private IntervalTypeEnum intervalType;

    private List<Button> allButtonsIntervals;

    private final String buttonIntervalDefaultStyle = "-fx-background-radius: 15;" +
            " -fx-border-radius: 15; -fx-background-color: DED0B6; -fx-border-color: BBAB8C;";
    private final String buttonIntervalCorrectStyle = "-fx-background-radius: 15;" +
            " -fx-border-radius: 15; -fx-background-color: green; -fx-border-color: BBAB8C;";
    private final String buttonIntervalIncorrectStyle = "-fx-background-radius: 15;" +
            " -fx-border-radius: 15; -fx-background-color: red; -fx-border-color: BBAB8C;";

    private IntervalsRecord guessedInterval;
    LocalDate currentDate;
    LevelintervalsRecord intervalLevel;
    UsersRecord currentUser;


    @FXML
    void intervalChosenOnClick(ActionEvent event) throws InterruptedException {
        if (canChooseInterval()){
            Button clickedButton = (Button) event.getSource();
            Button correctButton = getButtonFromInterval(currentGame.getChosenInterval());
            guessedInterval = getIntervalFromButton(clickedButton);
            clickedButton.setStyle(buttonIntervalIncorrectStyle);
            correctButton.setStyle(buttonIntervalCorrectStyle);
            currentGame.updateAnswers(guessedInterval);
            updateLabels();
            if (clickedButton.getText().equals(correctButton.getText())){
                if (currentGame.getAnswers().getSumOfAllAnswers() != currentGame.getRepetitions()){
                    errorLabel.setText("Odpowiedź prawidłowa, przejdź do następnego pytania.");
                }else {
                    errorLabel.setText("Odpowiedź prawidłowa, rozgrywka zakończyła się i możesz ją zapisać.");
                }
            } else {
                if (currentGame.getAnswers().getSumOfAllAnswers() != currentGame.getRepetitions()){
                    errorLabel.setText("Odpowiedź nieprawidłowa, przejdź do następnego pytania.");
                }else {
                    errorLabel.setText("Odpowiedź nieprawidłowa, rozgrywka zakończyła się i możesz ją zapisać.");
                }
            }
        }

    }

    void resetButtonsColor(){
        for (Button button: allButtonsIntervals){
            button.setStyle(buttonIntervalDefaultStyle);
        }
    }

    Button getButtonFromInterval(IntervalsRecord interval){
        for (Button button: allButtonsIntervals){
            if (button.getText().equals(interval.getIntervalname())){
                return button;
            }
        }
        return null;
    }

    IntervalsRecord getIntervalFromButton(Button button){
        for (IntervalsRecord interval: allIntervalsList){
            if (interval.getIntervalname().equals(button.getText())){
                return interval;
            }
        }
        return null;
    }



    boolean canChooseInterval(){
        boolean condition = notesPlayer.isAvailable() && guessedInterval == null &&
                currentGame.getChosenInterval() != null &&
                currentGame.getAnswers().getSumOfAllAnswers() < currentGame.getRepetitions();
        return condition;
    }

    void saveGameToDatabase() throws SQLException {

        Connection connection = DatabaseConnection.getInstance().getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        IntervalsgameRecord game = create.newRecord(Tables.INTERVALSGAME);
        game.setUserid(currentUser.getUserid());
        game.setDateplayed(currentDate);
        game.setIntervallevelid(intervalLevel.getLevelid());
        game.store();

        System.out.println(Integer.toString(game.getIntervalsgameid()));

        currentGame.getAnswersIntervalsGameMap().forEach((k,v) -> {
            AnswersintervalsgameRecord answer = create.newRecord(Tables.ANSWERSINTERVALSGAME);
            answer.setIntervalid(k);
            answer.setIntervalsgameid(game.getIntervalsgameid());
            answer.setIntervaloccurrences(v.getSumOfAllAnswers());
            answer.setIntervalguessedcorrectly(v.getAnsweredCorrectly());
            answer.store();
        });
    }

    @FXML
    void exitOnClick(ActionEvent event) throws IOException {
        ApplicationContext.getInstance().setLevelInterval(null);
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "inter-view.fxml");
    }

    @FXML
    void exitAndSaveOnClick(ActionEvent event) throws SQLException, IOException {
        if (currentGame.getAnswers().getSumOfAllAnswers() != currentGame.getRepetitions()){
            return;
        }

        saveGameToDatabase();
        ApplicationContext.getInstance().setLevelInterval(null);
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "inter-view.fxml");
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
            resetButtonsColor();
            currentGame.prepareNextQuestion();
            updateLabels();
            guessedInterval = null;
            playChosenInterval();
            errorLabel.setText("Wybierz odpowiedź po odsłuchaniu");
        }
    }

    private void updateLabels(){
        correct_answers_label.setText("Poprawne odpowiedzi: " + Integer.toString(
                currentGame.getAnswers().getAnsweredCorrectly()));
        incorrect_answers_label.setText("Niepoprawne odpowiedzi: " + Integer.toString(
                currentGame.getAnswers().getAnsweredIncorrectly()));
        current_question_label.setText(currentGame.getCurrentQuestion() + "/" + currentGame.getRepetitions());
    }

    boolean canNextQuestion(){
        boolean condition = notesPlayer.isAvailable() &&
                (guessedInterval!=null || currentGame.getChosenInterval()==null) &&
                currentGame.getAnswers().getSumOfAllAnswers() < currentGame.getRepetitions();
        return condition;
    }

    public void initialize() throws SQLException {
        // getting connection with database
        Connection connection = DatabaseConnection.getInstance().getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        ApplicationContext context = ApplicationContext.getInstance();

        // initializing allIntervalsList and allNotesList
        allIntervalsList = create.selectFrom(Tables.INTERVALS).fetch();
        allNotesList = create.selectFrom(Tables.NOTES).fetch();

        notesPlayer = new NotesPlayer();

        // TODO change it later to level from context
        Byte zero = 0;
        Byte one = 1;
        intervalLevel = context.getLevelInterval();

        currentGame = new OngoingIntervalGame(intervalLevel, allIntervalsList, allNotesList);

        if (intervalLevel.getUp() == 1){
            intervalType = IntervalTypeEnum.UP;
        } else if (intervalLevel.getDown() == 1) {
            intervalType = IntervalTypeEnum.DOWN;
        } else {
            intervalType = IntervalTypeEnum.TOGETHER;
        }

        currentUser = context.getUser();

        initializeAllButtonsIntervals();

        errorLabel.setText("Rozpocznij rozgrywkę");
        currentDate = LocalDate.now();

    }

    private void initializeAllButtonsIntervals(){
        allButtonsIntervals = new ArrayList<Button>();
        for (Node button: firstIntervalsHbox.getChildren()){
            allButtonsIntervals.add((Button) button);
        }
        for (Node button: secondIntervalsHbox.getChildren()){
            allButtonsIntervals.add((Button) button);
        }
        for (Node button: thirdIntervalsHbox.getChildren()){
            allButtonsIntervals.add((Button) button);
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

    public void updateAnswers(IntervalsRecord guessedInterval){
        if (guessedInterval == null){
            return;
        }

        if (guessedInterval.getIntervalid().equals(chosenInterval.getIntervalid())){
            answers.incrementAnsweredCorrectly();
            answersIntervalsGameMap.get(chosenInterval.getIntervalid()).incrementAnsweredCorrectly();
        }else {
            answers.incrementAnsweredIncorrectly();
            answersIntervalsGameMap.get(chosenInterval.getIntervalid()).incrementAnsweredIncorrectly();
        }
    }


    public HashMap<Integer, Answers> getAnswersIntervalsGameMap() {
        return answersIntervalsGameMap;
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

    public void incrementAnsweredCorrectly(){
        answeredCorrectly++;
    }

    public void incrementAnsweredIncorrectly(){
        answeredIncorrectly++;
    }

    public int getSumOfAllAnswers(){
        return answeredCorrectly+answeredIncorrectly;
    }

    public Answers(){
        answeredCorrectly = 0;
        answeredIncorrectly = 0;
    }
}

class NotesPlayer{

    List<MediaPlayer> players;
    List<Boolean> playersState; // True if player is still playing

    public NotesPlayer(){
        players = new ArrayList<MediaPlayer>();
        playersState = new ArrayList<>();
    }

    public boolean isAvailable(){
        for (Boolean state: playersState){
            if (state.booleanValue()){
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
        playersState = new ArrayList<Boolean>();
        for (NotesRecord note:notesToPlay){
            players.add(new MediaPlayer(new Media(getClass()
                    .getResource("notes_sounds/" + getNoteSoundPath(note)).toExternalForm())));
            playersState.add(true);
        }

        for (int i = 0; i < players.size() - 1; i++) {
            final MediaPlayer player     = players.get(i);
//            final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
            final MediaPlayer nextPlayer = players.get(i + 1);
            final int index = i;
            player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    nextPlayer.play();
                    playersState.set(index, false);
                }
            });
        }

        players.get(players.size()-1).setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playersState.set(playersState.size() - 1, false);
            }
        });

        players.get(0).play();
    }

    public void playNotesSimultaneously(List<NotesRecord> notesToPlay){
        players = new ArrayList<MediaPlayer>();
        playersState = new ArrayList<Boolean>();

        for (NotesRecord note:notesToPlay){
            players.add(new MediaPlayer(new Media(getClass()
                    .getResource("notes_sounds/" + getNoteSoundPath(note)).toExternalForm())));
        }

        for (int i = 0; i < players.size(); i++) {
            final MediaPlayer player = players.get(i);
            final int index = i;
            player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    playersState.set(index, false);
                }
            });
        }

        for (MediaPlayer player: players){
            player.play();
        }
    }

}



