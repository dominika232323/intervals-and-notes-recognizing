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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
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

public class NotesGameController {

    //Elementy interfejsu uzytkownika zapisane pod fx:id w pliku fxml
    @FXML
    ImageView ImageView_Klucz;
    @FXML
    Button C1;
    @FXML
    private Pane PaneLines;



    //Wartosci dotyczace aktualnie wyswietlanej nuty
    private Circle currentNoteGraphic;
    private Text currentAccidentalGraphic;
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
    private static final Map<String, Integer> notePositions = new HashMap<>() {{
        put("E", 50);
        put("E#", 50);
        put("F", 45);
        put("F#", 45);
        put("Gb", 40);
        put("G", 40);
        put("G#", 40);
        put("Ab", 35);
        put("A", 35);
        put("A#", 35);
        put("Bb", 30);
        put("B", 30);
        put("B#", 30);
        put("C", 25);
        put("C#", 25);
        put("Db", 20);
        put("D", 20);
        put("D#", 20);
        put("Eb", 15);
    }};


    public void generateAndDisplayNote() {
        currentNoteInt = new Random().nextInt((higherNoteBound - lowerNoteBound) + 1) + higherNoteBound;

        displayNoteOnStaff(currentNoteInt);
    }

    private void displayNoteOnStaff(int midiNote) {
        String a = """
        
        // Calculate the position of the note on the staff
        double yPos = calculateNotePosition(midiNote);

        // Create a circle to represent the note
        Circle noteCircle = new Circle(5); // Radius of 5
        noteCircle.setLayoutX(50); // Set a fixed X position for simplicity
        noteCircle.setLayoutY(yPos);

        // Add the circle to the pane
        PaneLines.getChildren().add(noteCircle);
        """;
    }

    private double calculateNotePosition(int midiNote) {
        // Implement the logic to calculate the Y position based on the MIDI note number
        // This will depend on how you've set up your staff lines in the FXML
        // For example, you might have a fixed distance between lines and calculate the position accordingly
        return 0; // Placeholder return value
    }

    private String getRandomNoteString() {
        Result<Record> result = create.select().from(NOTES)
                .where(NOTES.NOTEID.between(25, 49))
                .fetch();

        if (!result.isEmpty()) {
            int randomIndex = new Random().nextInt(result.size());
            String noteName = result.get(randomIndex).getValue(NOTES.NOTENAME);
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
