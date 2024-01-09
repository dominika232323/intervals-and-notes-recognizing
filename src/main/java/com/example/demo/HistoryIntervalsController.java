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


import static com.example.demo.jooq.tables.Levelintervals.LEVELINTERVALS;
import com.example.demo.jooq.tables.records.LevelintervalsRecord;

import static com.example.demo.jooq.tables.Intervalsgame.INTERVALSGAME;
import com.example.demo.jooq.tables.records.IntervalsgameRecord;

import static com.example.demo.jooq.tables.Answersintervalsgame.ANSWERSINTERVALSGAME;
import com.example.demo.jooq.tables.records.AnswersintervalsgameRecord;


import java.time.LocalDate;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;







public class HistoryIntervalsController <R extends Record> {
    @FXML
    private Label statsLabel, Label_PrymaCzysta, Label_SekundaMala, Label_SekundaWielka, Label_TercjaMala, Label_TercjaWielka;
    @FXML
    private Label Label_KwartaCzysta, Label_Tryton, Label_KwintaCzysta, Label_SekstaMala, Label_SekstaWielka;
    @FXML
    private Label Label_SeptymaMala, Label_SeptymaWielka, Label_Oktawa;
    @FXML
    private ScrollPane Lista, ListaGames;
    @FXML
    private DSLContext create;
    @FXML
    private DatePicker DateFrom, DateTo;
    @FXML
    private Button ZaznaczWszystko, OdznaczWszystko;

    public HistoryIntervalsController() {

    }


    private ArrayList<LevelBox<LevelintervalsRecord>> levelBoxArrayList =
            new ArrayList<LevelBox<LevelintervalsRecord>>();
    private ArrayList<LevelBox<IntervalsgameRecord>> gamesBoxArrayList =
            new ArrayList<LevelBox<IntervalsgameRecord>>();
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
        integerToLabel.put(0, Label_PrymaCzysta);
        integerToLabel.put(1, Label_SekundaMala);
        integerToLabel.put(2, Label_SekundaWielka);
        integerToLabel.put(3, Label_TercjaMala);
        integerToLabel.put(4, Label_TercjaWielka);
        integerToLabel.put(5, Label_KwartaCzysta);
        integerToLabel.put(6, Label_Tryton);
        integerToLabel.put(7, Label_KwintaCzysta);
        integerToLabel.put(8, Label_SekstaMala);
        integerToLabel.put(9, Label_SekstaWielka);
        integerToLabel.put(10, Label_SeptymaMala);
        integerToLabel.put(11, Label_SeptymaWielka);
        integerToLabel.put(12, Label_Oktawa);

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

        StatsSharedFunctions.setLabelKeysAction(Arrays.asList(Label_PrymaCzysta, Label_SekundaMala, Label_SekundaWielka, Label_TercjaMala, Label_TercjaWielka,
                Label_KwartaCzysta, Label_Tryton, Label_KwintaCzysta, Label_SekstaMala, Label_SekstaWielka,
                Label_SeptymaMala, Label_SeptymaWielka, Label_Oktawa), statsLabel);
    }

    private void refreshResultsAndBarChart() {
        Result<IntervalsgameRecord> noteGamesRecords =
                StatsSharedFunctions.StatsDbHelper.getGamesBetweenDatesAndWithLevelIDs(
                        DateFrom.getValue(), DateTo.getValue(), chosenLevelIDs,
                        INTERVALSGAME, INTERVALSGAME.DATEPLAYED, INTERVALSGAME.INTERVALLEVELID,
                        INTERVALSGAME.USERID
                );

        loadGamesIntoScrollPanel(noteGamesRecords);

        numberOfAnswers = StatsSharedFunctions.
                StatsDbHelper.getAnswersIntoDictFilterByGameIDs(chosenGameIDs,
                        ANSWERSINTERVALSGAME.INTERVALSGAMEID, ANSWERSINTERVALSGAME,
                        ANSWERSINTERVALSGAME.INTERVALOCCURRENCES,
                        ANSWERSINTERVALSGAME.INTERVALID, true);

        numberOfValidAnswers = StatsSharedFunctions.
                StatsDbHelper.getAnswersIntoDictFilterByGameIDs(chosenGameIDs,
                        ANSWERSINTERVALSGAME.INTERVALSGAMEID, ANSWERSINTERVALSGAME,
                        ANSWERSINTERVALSGAME.INTERVALGUESSEDCORRECTLY,
                        ANSWERSINTERVALSGAME.INTERVALID, true);

        StatsSharedFunctions.loadResultsIntoLabels(integerToLabel, numberOfAnswers,
                numberOfValidAnswers);

        if (DateFrom.getValue() == null || DateTo.getValue() == null || DateFrom.getValue().isAfter(DateTo.getValue()) ||
                DateFrom.getValue().equals(DateTo.getValue()) || chosenLevelIDs.isEmpty()) {
            StatsSharedFunctions.putEmptyTextInEachLabel(
                    new HashSet<Label>(Arrays.asList(integerToLabel.values().toArray(new Label[0]))));
        }
    }

    private void loadGamesIntoScrollPanel(Result<IntervalsgameRecord> noteGamesRecords) {
        //ArrayList<LevelBox<IntervalsgameRecord>> oldGamesBoxArrayList = gamesBoxArrayList;
        ArrayList<IntervalsgameRecord> previousNoteGamesRecords = new ArrayList<>();
        for (LevelBox<IntervalsgameRecord> levelBox : gamesBoxArrayList) {
            previousNoteGamesRecords.add(levelBox.getRecord());
        }
        for(LevelBox<IntervalsgameRecord> levelBox : gamesBoxArrayList){
            if(previousNoteGamesRecords.contains(levelBox.getRecord()) &&
                    !noteGamesRecords.contains(levelBox.getRecord()))
            {
                levelBox.getCheckBox().setSelected(false);
                chosenGameIDs.remove(levelBox.getRecord().getIntervalsgameid());
            }
        }


        gamesBoxArrayList =
                StatsSharedFunctions.History.loadGamesIntoScrollPane(ListaGames,
                        noteGamesRecords, INTERVALSGAME.DATEPLAYED, INTERVALSGAME.INTERVALSGAMEID,
                        INTERVALSGAME.USERID, INTERVALSGAME.INTERVALLEVELID, LEVELINTERVALS, LEVELINTERVALS.NAME,
                        LEVELINTERVALS.LEVELID);

        for (LevelBox<IntervalsgameRecord> levelBox : gamesBoxArrayList) {
            CheckBox checkBox = levelBox.getCheckBox();

            checkBox.setOnMouseClicked(event -> {
                if (checkBox.isSelected()) {
                    chosenGameIDs.add(levelBox.getRecord().getIntervalsgameid());
                    refreshResultsAndBarChart();

                } else {
                    chosenGameIDs.remove(levelBox.getRecord().getIntervalsgameid());
                    refreshResultsAndBarChart();
                }
            });
            if(chosenGameIDs.contains(levelBox.getRecord().getIntervalsgameid())){
                levelBox.getCheckBox().setSelected(true);
            }
        }
        System.out.println("chosenGamesIDs: " + chosenGameIDs);
    }

    private void loadLevelNotesIntoScrollPane(){
        levelBoxArrayList = StatsSharedFunctions.loadLevelsIntoScrollPane(Lista, LEVELINTERVALS, LEVELINTERVALS.NAME,
                LEVELINTERVALS.USERID);
        VBox vbox = (VBox)Lista.getContent();


        for(LevelBox<LevelintervalsRecord> levelBox : levelBoxArrayList){
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
                    LEVELINTERVALS.LEVELID, true);
            refreshResultsAndBarChart();
        });

        OdznaczWszystko.setOnMouseClicked(event ->
        {
            StatsSharedFunctions.selectAllCheckboxes(
                    levelBoxArrayList, chosenLevelIDs,
                    LEVELINTERVALS.LEVELID, false);
            refreshResultsAndBarChart();
        });
    }


    @FXML
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }
}
