package com.example.demo;


import com.example.demo.jooq.tables.records.LevelnotesRecord;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.*;
import javafx.util.Duration;

import static com.example.demo.jooq.tables.Answersnotesgame.ANSWERSNOTESGAME;
import static com.example.demo.jooq.tables.Notesgames.NOTESGAMES;
import static com.example.demo.jooq.tables.Levelnotes.LEVELNOTES;
import static com.example.demo.jooq.tables.Notes.NOTES;
import static com.example.demo.jooq.tables.Users.USERS;

import com.example.demo.jooq.tables.records.NotesRecord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.Group;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.Record;
import org.jooq.Result;


import java.io.IOException;
import java.sql.SQLException;

import java.util.Random;



class NotesDbHelper{
    static DSLContext create;
    private static int notesGameID;

    public static void insertIntoNotesGames(int userID, int levelNotesID, java.sql.Date datePlayed) {
        // Finding the largest current notesGameID
        Integer maxId = create.select(DSL.max(NOTESGAMES.NOTESGAMEID))
                .from(NOTESGAMES)
                .fetchOneInto(Integer.class);

        // If the table is empty, start from 1; otherwise use maxId + 1
        int newId = (maxId == null) ? 1 : maxId + 1;

        // Inserting the new record with the new ID
        create.insertInto(NOTESGAMES,
                        NOTESGAMES.NOTESGAMEID, NOTESGAMES.USERID, NOTESGAMES.LEVELNOTESID, NOTESGAMES.DATEPLAYED)
                .values(newId, userID, levelNotesID, datePlayed.toLocalDate())
                .execute();

        // Store the new ID in the notesGameID attribute
        notesGameID = newId;
    }

    public static int getNotesGameID() {
        return notesGameID;
    }

    static String getNoteStringFromDb(int note_id) {
        Result<Record> result = create.select().from(NOTES)
                .where(NOTES.NOTEID.eq(note_id))
                .fetch();

        if (!result.isEmpty()) {
            NotesRecord record = (NotesRecord) result.get(0);
            String noteName = record.getNotename();

            if (noteName.length() > 2) { // Czyli mamy np. C#5/Db5 albo F#4/Gb4 itd.
                String[] parts = noteName.split("/");
                return parts[new Random().nextInt(parts.length)]; // Randomly choose either sharp or flat
            }
            return noteName;
        }
        return null;
    }

    public static void updateNoteStatistics(int notesGameID, int noteID, boolean guessedCorrectly) throws SQLException {

        System.out.println("notesGameID: " + notesGameID + ", noteId: " + noteID
                                + "guessedCorrectly: " + guessedCorrectly);
        // Check if the note already exists for the given game
        Record record = create.select()
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
            Integer maxId = create.select(DSL.max(ANSWERSNOTESGAME.ANSWERSNOTESGAMEID)).from(ANSWERSNOTESGAME).
                                                                        fetchOneInto(Integer.class);
            int newAnswersNotesGameId = (maxId == null) ? 1 : maxId + 1;
            create.insertInto(ANSWERSNOTESGAME, ANSWERSNOTESGAME.ANSWERSNOTESGAMEID, ANSWERSNOTESGAME.NOTESGAMEID, ANSWERSNOTESGAME.NOTEID, ANSWERSNOTESGAME.NOTEOCCURRENCES, ANSWERSNOTESGAME.NOTEGUESSEDCORRECTLY)
                    .values(newAnswersNotesGameId, notesGameID, noteID, 1, guessedCorrectly ? 1 : 0)
                    .execute();
        }
    }
}

class HelperMethods{
//C1, C1H, C_Sharp, D, DH, D_Sharp, E, EH, F, FH, F_Sharp, G, GH, G_Sharp, A, AH, A_Sharp, B, BH, C2, C2H

    public static void insertStartingDatabase(DSLContext create){
        if(!create.fetchExists(NOTES)) {
            create.insertInto(NOTES, NOTES.NOTEID, NOTES.NOTENAME)
                    .values(1, "C2")
                    .values(2, "C#2/Db2")
                    .values(3, "D2")
                    .values(4, "D#2/Eb2")
                    .values(5, "E2")
                    .values(6, "F2")
                    .values(7, "F#2/Gb2")
                    .values(8, "G2")
                    .values(9, "G#2/Ab2")
                    .values(10, "A2")
                    .values(11, "A#2/Bb2")
                    .values(12, "B2")
                    .values(13, "C3")
                    .values(14, "C#3/Db3")
                    .values(15, "D3")
                    .values(16, "D#3/Eb3")
                    .values(17, "E3")
                    .values(18, "F3")
                    .values(19, "F#3/Gb3")
                    .values(20, "G3")
                    .values(21, "G#3/Ab3")
                    .values(22, "A3")
                    .values(23, "A#3/Bb3")
                    .values(24, "B3")
                    .values(25, "C4")
                    .values(26, "C#4/Db4")
                    .values(27, "D4")
                    .values(28, "D#4/Eb4")
                    .values(29, "E4")
                    .values(30, "F4")
                    .values(31, "F#4/Gb4")
                    .values(32, "G4")
                    .values(33, "G#4/Ab4")
                    .values(34, "A4")
                    .values(35, "A#4/Bb4")
                    .values(36, "B4")
                    .values(37, "C5")
                    .values(38, "C#5/Db5")
                    .values(39, "D5")
                    .values(40, "D#5/Eb5")
                    .values(41, "E5")
                    .values(42, "F5")
                    .values(43, "F#5/Gb5")
                    .values(44, "G5")
                    .values(45, "G#5/Ab5")
                    .values(46, "A5")
                    .values(47, "A#5/Bb5")
                    .values(48, "B5")
                    .values(49, "C6")
                    .execute();
            create.insertInto(USERS, USERS.USERID, USERS.NAME, USERS.PASSWORDHASH)
                    .values(1, "User1", "User1Password")
                    .execute();
            create.insertInto(LEVELNOTES, LEVELNOTES.LEVELID, LEVELNOTES.USERID, LEVELNOTES.NAME, LEVELNOTES.LOWERNOTEBOUND, LEVELNOTES.HIGHERNOTEBOUND, LEVELNOTES.STARTINGWAVE, LEVELNOTES.ENDINGWAVE, LEVELNOTES.REPETITIONSNEXTWAVE)
                    .values(1, 1, "name1", 25, 49, 1, 5, 10)
                    .execute();
        }
    }
}


public class NotesGameController {

    //Elementy interfejsu uzytkownika zapisane pod fx:id w pliku fxml
    @FXML
    ImageView ImageView_Klucz;
    @FXML
    Button C1, C1H, C_Sharp, D, DH, D_Sharp, E, EH, F, FH, F_Sharp, G, GH, G_Sharp, A, AH, A_Sharp, B, BH, C2, C2H;
    @FXML
    private Pane PaneLines;
    @FXML
    private Label Label_Already_Guessed, Label_Green, Label_Red, Label_Wave;
    @FXML
    private GridPane GridPane_Green_Red;



    //Wartosci dotyczace aktualnie wyswietlanej nuty
    private Circle currentNoteCircle;
    Group noteSymbolGroup;
    private ImageView currentNoteSharpFlat;
    private String currentNoteString;
    private int currentNoteInt;
    private NotesHelperClass currentNotesHelperClass;

    //Waartosci dotyczace obecnej fali
    private int currentWave;
    private int numberOfValidAnswers;
    private int numberOfAnswers;
    private int greenPoints;
    private TranslateTransition currentTransition;


    //Dane dotyczace wlasciwosci poziomu
    private static DSLContext create;
    private  static int levelId = 0;
    private static int startingWave = 0;
    private static int endingWave = 0;
    private static int repetitionsNextWave = 0;
    private static int lowerNoteBound = 0;
    private static int higherNoteBound = 0;
    private int notesGameId;

    //Do aktualizowania labelu Prawidłowo x/y


    public void initialize() throws SQLException{
        numberOfValidAnswers = 0;
        numberOfAnswers = 0;
        greenPoints = 25;
        // Using Singleton pattern for database connection
        create = DSL.using(DatabaseConnection.getInstance().getConnection(), SQLDialect.MYSQL);
        NotesDbHelper.create = create;


        setUpLevelValuesApplicationContext();


        NotesDbHelper.insertIntoNotesGames(ApplicationContext.getInstance().getUser().getUserid(),
                levelId, new java.sql.Date(System.currentTimeMillis()));
        notesGameId = NotesDbHelper.getNotesGameID();


        //Button[] whiteButtons = new List<Button>
        currentWave = startingWave;

        HelperMethods.insertStartingDatabase(create);

        String changeColor = "darkgray";



        setupButton(C1, C1H, C1.getStyle(), C1H.getStyle(), changeColor);
        setupButton(D, DH, D.getStyle(), DH.getStyle(), changeColor);
        setupButton(E, EH, E.getStyle(), EH.getStyle(), changeColor);
        setupButton(F, FH, F.getStyle(), FH.getStyle(), changeColor);
        setupButton(G, GH, G.getStyle(), GH.getStyle(), changeColor);
        setupButton(A, AH, A.getStyle(), AH.getStyle(), changeColor);
        setupButton(B, BH, B.getStyle(), BH.getStyle(), changeColor);
        setupButton(C2, C2H, C2.getStyle(), C2H.getStyle(), changeColor);



        setupButton(C_Sharp, C_Sharp.getStyle(), changeColor);
        setupButton(D_Sharp, D_Sharp.getStyle(), changeColor);
        setupButton(F_Sharp, F_Sharp.getStyle(), changeColor);
        setupButton(G_Sharp, G_Sharp.getStyle(), changeColor);
        setupButton(A_Sharp, A_Sharp.getStyle(), changeColor);

        generateAndDisplayNote();

    }




    private void setupButton(Button button, String originalStyle, String color) {
        button.setOnMousePressed(event -> handleMousePressed(button, color));
        button.setOnMouseReleased(event -> handleMouseReleased(button, originalStyle));
    }

    private void setupButton(Button button, Button anotherButton,
                                        String originalStyle, String originalStyleAnother,
                                        String color) {
        button.setOnMousePressed(event -> {handleMousePressed(button, color);
                                            handleMousePressed(anotherButton, color);});
        button.setOnMouseReleased(event -> {handleMouseReleased(button, originalStyle);
                                            handleMouseReleased(anotherButton, originalStyleAnother);});
        anotherButton.setOnMousePressed(event -> {handleMousePressed(button, color);
            handleMousePressed(anotherButton, color);});
        anotherButton.setOnMouseReleased(event -> {handleMouseReleased(button, originalStyle);
            handleMouseReleased(anotherButton, originalStyleAnother);});
    }

    private void handleMousePressed(Button button, String color){
        String styleToSet = "-fx-background-color: " + color;
        button.setStyle(styleToSet);
        String currButtonStr = button.getId();
        if(currButtonStr.length() == 2){
            return; //Zapobiega podwójnym reakcjom na wciśnięcie przycisku
            //pianina (tj. pary "F" i "FH", "C1" i "C1H" itd.). Konstrukcja resizable
            //klawiatury wymagała zbudowania białego przycisku z dwóch różnych elementów.
        }
        if(currButtonStr.length() < 4){
            currButtonStr = currButtonStr.charAt(0) + "1";
        } else{
            currButtonStr = currButtonStr.charAt(0) + "#1";
        }
        Label_Already_Guessed.setText(currButtonStr);

        numberOfAnswers += 1;
        boolean ifGuessed = checkIfCorrect(currButtonStr);
        try {
            NotesDbHelper.updateNoteStatistics(notesGameId, currentNoteInt, ifGuessed);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(ifGuessed) {
            generateAndDisplayNote();
            numberOfValidAnswers += 1;
            greenPoints = (greenPoints < 49) ? greenPoints + 1 : 50;
        } else{
            greenPoints = (greenPoints > 0) ? greenPoints - 1 : 0;
        }
        changeWaveIfNeeded();
        refreshAfterAnswer();
    }

    private void changeWaveIfNeeded(){
        if(currentWave == endingWave){
            onBackToMenuButtonClick();
        }
        if(numberOfAnswers % repetitionsNextWave == 0){
            currentWave += 1;
            generateAndDisplayNote();
        }
    }

    private void refreshAfterAnswer(){
        Label_Already_Guessed.setText("Prawidłowo: " + numberOfValidAnswers
                + "/" + numberOfAnswers);
        Label_Wave.setText("Fala: " + currentWave);
        Label_Green.setText("+" + greenPoints);
        Label_Red.setText("-" + (50 - greenPoints));
        GridPane_Green_Red.getColumnConstraints().get(0).setPercentWidth(greenPoints*2);
        GridPane_Green_Red.getColumnConstraints().get(1).setPercentWidth(100 - greenPoints*2);
    }

    private void handleMouseReleased(Button button, String originalStyle) {
        String styleToSet = originalStyle;
        button.setStyle(styleToSet);

    }


    boolean checkIfCorrect(String noteString){
        NotesHelperClass buttonNotesHelperClass = new NotesHelperClass(noteString);
        int currDegree = currentNotesHelperClass.getScaleDegree();
        NotesHelperClass.NoteType currType = currentNotesHelperClass.getNoteType();
        //Czarne klawisze zawsze zwracają wartość z krzyżykiem, więc sprowadzamy
        //wyświetlane nuty z bemolem do ich enharmonicznego odpowiednika z krzyżykiem
        if(currentNotesHelperClass.getNoteType() == NotesHelperClass.NoteType.FLAT){
            currDegree = currDegree - 1;
            currType = NotesHelperClass.NoteType.SHARP;
        }
        return (currDegree == buttonNotesHelperClass.getScaleDegree() &&
                currType == buttonNotesHelperClass.getNoteType());
    }



    public void generateAndDisplayNote() {
        currentNoteInt = new Random().nextInt((higherNoteBound - lowerNoteBound) + 1) + lowerNoteBound;
        //System.out.println("currentNoteInt: " + currentNoteInt);
        currentNoteString = NotesDbHelper.getNoteStringFromDb(currentNoteInt);
        assert currentNoteString != null;
        currentNotesHelperClass = new NotesHelperClass(currentNoteString);
        updateClefImage(currentNoteInt);

        displayNoteOnStaff();


        //System.out.println(currentNoteString);
        //System.out.println("Note generated: " + currentNotesHelperClass.getNoteLetter() + " " + currentNotesHelperClass.getNoteType() + currentNotesHelperClass.getOctave());
    }

    private void displayNoteOnStaff() {
        int currentNoteOffsetY = getCurrentNoteOffsetY();

        PaneLines.getChildren().remove(noteSymbolGroup);

        noteSymbolGroup = new Group();
        currentNoteSharpFlat = new ImageView();
        currentNoteCircle = new Circle(8);
        currentNoteCircle.setFill(Color.WHITE);
        currentNoteCircle.setStroke(Color.BLACK);
        currentNoteCircle.setStrokeWidth(2);
        currentNoteCircle.setLayoutX(750); // Set a fixed X position for simplicity
        currentNoteCircle.setLayoutY(currentNoteOffsetY);

        noteSymbolGroup.getChildren().add(currentNoteCircle);

        if(currentNoteOffsetY >= 160){
            Line lineUnderStaff = new Line(735, 160, 765, 160);
            lineUnderStaff.setStrokeWidth(2);
            noteSymbolGroup.getChildren().add(lineUnderStaff);
            if(currentNoteOffsetY > 160){
                Line lineUnderStaff1 = new Line(735, 180, 765, 180);
                lineUnderStaff1.setStrokeWidth(2);
                noteSymbolGroup.getChildren().add(lineUnderStaff1);
            }
        }
        if(currentNoteOffsetY <= 40){
            Line lineUnderStaff = new Line(735, 40, 765, 40);
            lineUnderStaff.setStrokeWidth(2);
            noteSymbolGroup.getChildren().add(lineUnderStaff);
            if(currentNoteOffsetY < 40){
                Line lineUnderStaff1 = new Line(735, 20, 765, 20);
                lineUnderStaff1.setStrokeWidth(2);
                noteSymbolGroup.getChildren().add(lineUnderStaff1);
            }
        }
        //Poniższy kod dodaje krzyżyki i bemole do grupy zawierającej kółko symbolizujące
        //nutę (o ile jest to konieczne)
        NotesHelperClass.NoteType noteType = currentNotesHelperClass.getNoteType();
        if(noteType != NotesHelperClass.NoteType.NONE){
            if(noteType == NotesHelperClass.NoteType.SHARP){
                currentNoteSharpFlat.setImage(new Image(getClass().getResourceAsStream("krzyzyk.png")));
            } else {
                currentNoteSharpFlat.setImage(new Image(getClass().getResourceAsStream("bemol.png")));
            }
            currentNoteSharpFlat.setLayoutX(720); // Example offset
            currentNoteSharpFlat.setLayoutY(currentNoteOffsetY - 15);
            noteSymbolGroup.getChildren().add(currentNoteSharpFlat);
        }




        PaneLines.getChildren().add(noteSymbolGroup);
        animateNoteGroup(noteSymbolGroup);

        Line line = new Line(80, 120, 120, 120);
        PaneLines.getChildren().add(line);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
    }

    private void animateNoteGroup(Group noteGroup) {
        if(currentTransition != null){
            currentTransition.stop();
        }
        int baseTime = 10;
        int thisAnimationNumberOfAnswers = numberOfValidAnswers + 1;
        currentTransition = new TranslateTransition(Duration.seconds(Math.pow(0.9, currentWave) * baseTime), noteGroup);
        currentTransition.setInterpolator(Interpolator.LINEAR);
        currentTransition.setByX(-600); // For example, move 300 units to the left
        currentTransition.play();


        currentTransition.setOnFinished(event -> {
            try {
                NotesDbHelper.updateNoteStatistics(notesGameId, currentNoteInt, false);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            numberOfAnswers += 1;
            greenPoints = (greenPoints > 0) ? greenPoints - 1 : 0;
            changeWaveIfNeeded();
            refreshAfterAnswer();
            generateAndDisplayNote();
        });
    }

    private int getCurrentNoteOffsetY(){
        int octave = currentNotesHelperClass.getOctave();
        int offsetY = 20;
        if(isBassClef(currentNoteInt)){
            offsetY = offsetY + 20;
        }
        if(octave == 6){
            return offsetY;
        } else {
            if((octave % 2) == 0) {
                offsetY += 70;
            }
            offsetY += 10 * (8 - currentNotesHelperClass.getScaleDegree());
            return offsetY;
        }
    }

    public NotesGameController() throws SQLException {


    }

    public static int getLevelId(){
        return levelId;
    }
    public static int getStartingWave(){
        return startingWave;
    }
    public static int getEndingWave(){
        return endingWave;
    }
    public static int getRepetitionsNextWave(){
        return repetitionsNextWave;
    }
    public static int getLowerNoteBound(){
        return lowerNoteBound;
    }
    public static int getHigherNoteBound(){
        return higherNoteBound;
    }
    public static void setUpLevelValues(int levelID){
        levelId = levelID;
        Record levelRecord = (Record) create.select().
                from(LEVELNOTES).
                where(LEVELNOTES.LEVELID.eq(levelId)).
                fetchOne();
        if(levelRecord != null) {
            startingWave = levelRecord.get(LEVELNOTES.STARTINGWAVE);
            endingWave = levelRecord.get(LEVELNOTES.ENDINGWAVE);
            repetitionsNextWave = levelRecord.get(LEVELNOTES.REPETITIONSNEXTWAVE);
            lowerNoteBound = levelRecord.get(LEVELNOTES.LOWERNOTEBOUND);
            higherNoteBound = levelRecord.get(LEVELNOTES.HIGHERNOTEBOUND);
        }
    }

    public static void setUpLevelValuesApplicationContext(){
        LevelnotesRecord levelRecord = (LevelnotesRecord) ApplicationContext.getInstance().getLevelNotes();
        if(levelRecord != null) {
            levelId = levelRecord.get(LEVELNOTES.LEVELID);
            startingWave = levelRecord.get(LEVELNOTES.STARTINGWAVE);
            endingWave = levelRecord.get(LEVELNOTES.ENDINGWAVE);
            repetitionsNextWave = levelRecord.get(LEVELNOTES.REPETITIONSNEXTWAVE);
            lowerNoteBound = levelRecord.get(LEVELNOTES.LOWERNOTEBOUND);
            higherNoteBound = levelRecord.get(LEVELNOTES.HIGHERNOTEBOUND);
        } else {
            //levelId = 1;
            //startingWave = 1;
            //endingWave = 20;
            //repetitionsNextWave = 10;
            //lowerNoteBound = 1;
            //higherNoteBound = 49;
        }
    }
    @FXML
    private void onBackToMenuButtonClick() {
        try
        {
            currentTransition.stop();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Menu");
            stage.setScene(new Scene(root, 700, 500));

            Stage currentStage = (Stage) Label_Already_Guessed.getScene().getWindow();
            currentStage.close();

            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void updateClefImage(int noteArgument) {
        Image clefImage;
        if (isBassClef(noteArgument)) {
            clefImage = new Image(getClass().getResourceAsStream("bass_clef.jpg"));
            ImageView_Klucz.setLayoutY(45);
        } else {
            clefImage = new Image(getClass().getResourceAsStream("violin_cleff.png"));
            ImageView_Klucz.setLayoutY(45);
        }

        ImageView_Klucz.setImage(clefImage);
    }

    private boolean isBassClef(int noteArgument) {
        if (noteArgument <= 24){
            return true;
        }
        return false;
    }
}
