package com.example.demo;

import com.example.demo.jooq.tables.records.LevelnotesRecord;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import org.jooq.*;

import org.jooq.Record;
import org.jooq.impl.DSL;


import static com.example.demo.jooq.tables.Intervalsgame.INTERVALSGAME;
import static com.example.demo.jooq.tables.Levelintervals.LEVELINTERVALS;
import static com.example.demo.jooq.tables.Levelnotes.LEVELNOTES;

import static com.example.demo.jooq.tables.Notesgames.NOTESGAMES;
import com.example.demo.jooq.tables.records.NotesgamesRecord;

import static com.example.demo.jooq.tables.Answersnotesgame.ANSWERSNOTESGAME;


import java.time.LocalDate;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;







public class HistoryNotesController <R extends Record> {
    @FXML
    private Label statsLabel, C1, CSharp, D, DSharp, E, F, FSharp, G, GSharp, A, ASharp, B, C2;
    @FXML
    private ScrollPane Lista, ListaGames;
    @FXML
    private DSLContext create;
    @FXML
    private DatePicker DateFrom, DateTo;
    @FXML
    private Button ZaznaczWszystko, OdznaczWszystko;

    public HistoryNotesController() {

    }


    private ArrayList<LevelBox<LevelnotesRecord>> levelBoxArrayList =
            new ArrayList<LevelBox<LevelnotesRecord>>();
    private ArrayList<LevelBox<NotesgamesRecord>> gamesBoxArrayList =
            new ArrayList<LevelBox<NotesgamesRecord>>();
    private HashSet<Integer> chosenLevelIDs = new HashSet<Integer>();
    private HashSet<Integer> chosenGameIDs = new HashSet<Integer>();
    private HashMap<Integer, Integer> numberOfAnswers;
    private HashMap<Integer, Integer> numberOfValidAnswers;
    private HashMap<Integer, Label> integerToLabel;

    public void initialize() {
        chosenLevelIDs.clear();
        numberOfAnswers = new HashMap<>();
        numberOfValidAnswers = new HashMap<Integer, Integer>();
        for (int i = 0; i < 12; i += 1) {
            numberOfAnswers.put(i, 0);
            numberOfValidAnswers.put(i, 0);
        }

        integerToLabel = new HashMap<Integer, Label>();
        integerToLabel.put(0, C1);
        integerToLabel.put(1, CSharp);
        integerToLabel.put(2, D);
        integerToLabel.put(3, DSharp);
        integerToLabel.put(4, E);
        integerToLabel.put(5, F);
        integerToLabel.put(6, FSharp);
        integerToLabel.put(7, G);
        integerToLabel.put(8, GSharp);
        integerToLabel.put(9, A);
        integerToLabel.put(10, ASharp);
        integerToLabel.put(11, B);
        integerToLabel.put(12, C2);

        try {
            create = DSL.using(DatabaseConnection.getInstance().getConnection(), SQLDialect.MYSQL);
            StatsSharedFunctions.create = create;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadLevelNotesIntoScrollPane();
        StatsSharedFunctions.putEmptyTextInEachLabel(
                new HashSet<Label>(Arrays.asList(integerToLabel.values().toArray(new Label[0]))));
        setZaznaczOdznaczAction();

        DateFrom.valueProperty().addListener((obj, oldData, newData) -> refreshResultsAndBarChart());
        DateTo.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldDate, LocalDate newDate) {
                refreshResultsAndBarChart();
            }
        }); //Do DateFrom użyliśmy funkcji lambda, która jest funkcjonalnie równa użyciu
        //klasy anonimowej, jak w przypadku DateTo. Poczytaj o interfejsach SAM

        StatsSharedFunctions.setLabelKeysAction(Arrays.asList(C1, CSharp, D, DSharp, E, F,
                FSharp, G, GSharp, A, ASharp, B, C2), statsLabel);
    }

    private void refreshResultsAndBarChart() {
        Result<NotesgamesRecord> noteGamesRecords =
                StatsSharedFunctions.StatsDbHelper.getGamesBetweenDatesAndWithLevelIDs(
                        DateFrom.getValue(), DateTo.getValue(), chosenLevelIDs,
                        NOTESGAMES, NOTESGAMES.DATEPLAYED, NOTESGAMES.LEVELNOTESID,
                        NOTESGAMES.USERID
                );

        loadGamesIntoScrollPanel(noteGamesRecords);

        numberOfAnswers = StatsSharedFunctions.
                StatsDbHelper.getAnswersIntoDictFilterByGameIDs(chosenGameIDs,
                        ANSWERSNOTESGAME.NOTESGAMEID, ANSWERSNOTESGAME,
                        ANSWERSNOTESGAME.NOTEOCCURRENCES,
                        ANSWERSNOTESGAME.NOTEID, true);

        numberOfValidAnswers = StatsSharedFunctions.
                StatsDbHelper.getAnswersIntoDictFilterByGameIDs(chosenGameIDs,
                        ANSWERSNOTESGAME.NOTESGAMEID, ANSWERSNOTESGAME,
                        ANSWERSNOTESGAME.NOTEGUESSEDCORRECTLY,
                        ANSWERSNOTESGAME.NOTEID, true);

        StatsSharedFunctions.loadResultsIntoLabels(integerToLabel, numberOfAnswers,
                numberOfValidAnswers);

        if (DateFrom.getValue() == null || DateTo.getValue() == null || DateFrom.getValue().isAfter(DateTo.getValue()) ||
                DateFrom.getValue().equals(DateTo.getValue()) || chosenLevelIDs.isEmpty()) {
            StatsSharedFunctions.putEmptyTextInEachLabel(
                    new HashSet<Label>(Arrays.asList(integerToLabel.values().toArray(new Label[0]))));
        }
    }

    private void loadGamesIntoScrollPanel(Result<NotesgamesRecord> noteGamesRecords) {
        //ArrayList<LevelBox<NotesgamesRecord>> oldGamesBoxArrayList = gamesBoxArrayList;
        ArrayList<NotesgamesRecord> previousNoteGamesRecords = new ArrayList<>();
        for (LevelBox<NotesgamesRecord> levelBox : gamesBoxArrayList) {
            previousNoteGamesRecords.add(levelBox.getRecord());
        }
        for(LevelBox<NotesgamesRecord> levelBox : gamesBoxArrayList){
            if(previousNoteGamesRecords.contains(levelBox.getRecord()) &&
                                !noteGamesRecords.contains(levelBox.getRecord()))
            {
                levelBox.getCheckBox().setSelected(false);
                chosenGameIDs.remove(levelBox.getRecord().getNotesgameid());
            }
        }
        gamesBoxArrayList =
                StatsSharedFunctions.History.loadGamesIntoScrollPane(ListaGames,
                        noteGamesRecords, NOTESGAMES.DATEPLAYED, NOTESGAMES.NOTESGAMEID,
                        NOTESGAMES.USERID, NOTESGAMES.LEVELNOTESID, LEVELNOTES, LEVELNOTES.NAME,
                        LEVELNOTES.LEVELID);


        for (LevelBox<NotesgamesRecord> levelBox : gamesBoxArrayList) {
            CheckBox checkBox = levelBox.getCheckBox();

            checkBox.setOnMouseClicked(event -> {
                if (checkBox.isSelected()) {
                    chosenGameIDs.add(levelBox.getRecord().getNotesgameid());
                    refreshResultsAndBarChart();

                } else {
                    chosenGameIDs.remove(levelBox.getRecord().getNotesgameid());
                    refreshResultsAndBarChart();
                }
            });
            if(chosenGameIDs.contains(levelBox.getRecord().getNotesgameid())){
                levelBox.getCheckBox().setSelected(true);
            }
        }
        System.out.println("chosenGamesIDs: " + chosenGameIDs);
    }

    private void loadLevelNotesIntoScrollPane(){
        levelBoxArrayList = StatsSharedFunctions.loadLevelsIntoScrollPane(Lista, LEVELNOTES, LEVELNOTES.NAME,
                LEVELNOTES.USERID);
        VBox vbox = (VBox)Lista.getContent();


        for(LevelBox<LevelnotesRecord> levelBox : levelBoxArrayList){
            CheckBox checkBox = levelBox.getCheckBox();
            checkBox.setOnMouseClicked(event -> {
                if (checkBox.isSelected()) {
                    chosenLevelIDs.add(levelBox.getRecord().getLevelid());
                    refreshResultsAndBarChart();

                } else {
                    chosenLevelIDs.remove(levelBox.getRecord().getLevelid());
                    refreshResultsAndBarChart();
                }
            });
        }
    }

    private void setZaznaczOdznaczAction(){
        ZaznaczWszystko.setOnMouseClicked(event ->
        {
            StatsSharedFunctions.selectAllCheckboxes(
                    levelBoxArrayList, chosenLevelIDs,
                    LEVELNOTES.LEVELID, true);
            refreshResultsAndBarChart();
        });

        OdznaczWszystko.setOnMouseClicked(event ->
        {
            StatsSharedFunctions.selectAllCheckboxes(
                    levelBoxArrayList, chosenLevelIDs,
                    LEVELNOTES.LEVELID, false);
            refreshResultsAndBarChart();
        });
    }


    @FXML
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }
}
