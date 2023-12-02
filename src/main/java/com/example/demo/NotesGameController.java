package com.example.demo;

import static com.example.demo.jooq.tables.Answersnotesgame.ANSWERSNOTESGAME;
import static com.example.demo.jooq.tables.Levelnotes.LEVELNOTES;
import static com.example.demo.jooq.tables.Notes.NOTES;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.Record;
import org.jooq.Result;

import java.sql.Connection;
import java.sql.DriverManager;

import java.io.IOException;
import java.sql.SQLException;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import javafx.util.Duration;
import javafx.animation.FillTransition;


class HelperMethods{
//C1, C1H, C_Sharp, D, DH, D_Sharp, E, EH, F, FH, F_Sharp, G, GH, G_Sharp, A, AH, A_Sharp, B, BH, C2, C2H
    private static final Map<String, String> noteNames = new HashMap<>() {
    {
        put("C1", "C");
        put("C1H", "C");
        put("D", "D");
        put("DH", "D");
        put("E", "E");
        put("EH", "E");
        put("F", "F");
        put("FH", "F");
        put("G", "G");
        put("GH", "G");
        put("A", "A");
        put("AH", "A");
        put("B", "B");
        put("BH", "B");

        put("C_Sharp", "C#");
        put("D_Sharp", "D#");
        put("F_Sharp", "F#");
        put("G_Sharp", "G#");
        put("A_Sharp", "A#");

    }};

    public static String getStringFromButton(Button button){
        return noteNames.get(button.getId());
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
    private Label Label_Already_Guessed;



    //Wartosci dotyczace aktualnie wyswietlanej nuty
    private Circle currentNoteCircle;
    private Text currentNoteSharpFlat;
    private String currentNoteString;
    private int currentNoteInt;


    //Dane dotyczace wlasciwosci poziomu
    private static DSLContext create;
    private  static int levelId = 1;
    private static int startingWave = 1;
    private static int endingWave = 25;
    private static int repetitionsNextWave = 100;
    private static int lowerNoteBound = 25;
    private static int higherNoteBound = 30;


    //Do aktualizowania labelu Prawidłowo x/y
    private int prawidloweOdpowiedzi;
    private int liczbaOdpowiedzi;


    //Dane dotyczace offsetu nuty (kola)
    private static final Map<String, Integer> notePositionsViolin = new HashMap<>() {{
        int position = 20;
        put("C6", position);
        put("B5", position + 10);
        put("A5", position + 10);
        put("G5", position + 10);
        put("F5", position + 10);
        put("E5", position + 10);
        put("D5", position + 10);
        put("C5", position + 10);
        put("B4", position + 10);
        put("A4", position + 10);
        put("G4", position + 10);
        put("F4", position + 10);
        put("E4", position + 10);
        put("D4", position + 10);
        put("C4", position + 10);
    }};

    private static final Map<String, Integer> notePositionsBass = new HashMap<>() {{
        int position = 20;
        put("C4", position);
        put("B3", position + 10);
        put("A3", position + 10);
        put("G3", position + 10);
        put("F3", position + 10);
        put("E3", position + 10);
        put("D3", position + 10);
        put("C3", position + 10);
        put("B2", position + 10);
        put("A2", position + 10);
        put("G2", position + 10);
        put("F2", position + 10);
        put("E2", position + 10);
        put("D2", position + 10);
        put("C2", position + 10);
    }};


    public void initialize(){
        //Button[] whiteButtons = new List<Button>
        String changeColor = "darkgray";
        displayNoteOnStaff(26);



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

    private void handleMousePressed(Button button, String color) {
        Label_Already_Guessed.setText(HelperMethods.getStringFromButton(button));
        String styleToSet = "-fx-background-color: " + color;
        button.setStyle(styleToSet);
        checkIfCorrect(button.getText());
    }

    private void handleMouseReleased(Button button, String originalStyle) {
        String styleToSet = originalStyle;
        button.setStyle(styleToSet);
    }


    void checkIfCorrect(String noteString){
        int noteid = new Random().nextInt(49) + 1;
        System.out.println(getNoteStringFromDb(noteid));
    }



    public void generateAndDisplayNote() {
        currentNoteInt = new Random().nextInt((higherNoteBound - lowerNoteBound) + 1) + higherNoteBound;

        displayNoteOnStaff(currentNoteInt);
    }

    private void displayNoteOnStaff(int midiNote) {
        currentNoteCircle = new Circle(8);
        currentNoteCircle.setLayoutX(100); // Set a fixed X position for simplicity
        currentNoteCircle.setLayoutY(20);
        PaneLines.getChildren().add(currentNoteCircle);
        currentNoteCircle.setFill(Color.WHITE);
        currentNoteCircle.setStroke(Color.BLACK);
        currentNoteCircle.setStrokeWidth(2);
        Line line = new Line(85, 20, 115, 20);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        PaneLines.getChildren().add(line);


    }


    private String getNoteStringFromDb(int note_id) {
        Result<Record> result = create.select().from(NOTES)
                .where(NOTES.NOTEID.eq(note_id))
                .fetch();

        if (!result.isEmpty()) {
            Record record = result.get(0);
            String noteName = (String) record.get(1);
            return resolveSharpFlat(noteName);
        }
        return null;
    }

    private String resolveSharpFlat(String noteName) {
        if (noteName.length() > 2) { // Czyli mamy np. C#5/Db5 albo F#4/Gb4 itd.
            String[] parts = noteName.split("/");
            return parts[new Random().nextInt(parts.length)]; // Randomly choose either sharp or flat
        }
        return noteName;
    }

    public NotesGameController() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/db";
        String username = "user";
        String password = "password";
        Connection connection = DriverManager.getConnection(url, username, password);
        create = DSL.using(connection, SQLDialect.MYSQL);
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

        startingWave = levelRecord.get(LEVELNOTES.STARTINGWAVE);
        endingWave = levelRecord.get(LEVELNOTES.ENDINGWAVE);
        repetitionsNextWave = levelRecord.get(LEVELNOTES.REPETITIONSNEXTWAVE);
        lowerNoteBound = levelRecord.get(LEVELNOTES.LOWERNOTEBOUND);
        higherNoteBound = levelRecord.get(LEVELNOTES.HIGHERNOTEBOUND);
    }
    @FXML
    private void onBackToMenuButtonClick() {
        // Code to navigate back to the main menu
        HelloApplication helloApplication = new HelloApplication();
        try {
            helloApplication.start(new Stage());
            //te

            // Close the current stage (Stats page)
            Stage currentStage = (Stage) ImageView_Klucz.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateClefImage(int noteArgument) {
        Image clefImage;
        if (isBassClef(noteArgument)) {
            clefImage = new Image(getClass().getResourceAsStream("bass_clef.png"));
        } else {
            clefImage = new Image(getClass().getResourceAsStream("violin_clef.png"));
        }
        ImageView_Klucz.setImage(clefImage);
    }

    private boolean isBassClef(int noteArgument) {
        if (noteArgument <= 24){
            return true;
        }
        return false;
    }


    public void updateNoteStatistics(int notesGameID, int noteID, boolean guessedCorrectly) throws SQLException {

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
