package com.example.demo;

import com.example.demo.jooq.tables.records.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.VBox;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.time.LocalDate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


import static com.example.demo.jooq.tables.Levelintervals.LEVELINTERVALS;
import com.example.demo.jooq.tables.records.LevelintervalsRecord;

import static com.example.demo.jooq.tables.Intervalsgame.INTERVALSGAME;
import com.example.demo.jooq.tables.records.IntervalsgameRecord;

import static com.example.demo.jooq.tables.Answersintervalsgame.ANSWERSINTERVALSGAME;
import com.example.demo.jooq.tables.records.AnswersintervalsgameRecord;


public class StatsIntervalsController {
    @FXML
    private Label statsLabel;
    @FXML
    private ScrollPane Lista;
    @FXML
    private BarChart<String, Number> UsageChart;
    @FXML
    private DatePicker DateFrom, DateTo;
    @FXML
    private Button ZaznaczWszystko, OdznaczWszystko;
    @FXML
    private Label Label_PrymaCzysta, Label_SekundaMala, Label_SekundaWielka, Label_TercjaMala, Label_TercjaWielka, Label_KwartaCzysta, Label_Tryton;
    @FXML
    private Label Label_KwintaCzysta, Label_SekstaMala, Label_SekstaWielka, Label_SeptymaMala, Label_SeptymaWielka, Label_Oktawa;

    private LocalDate dateFromValue, dateToValue;
    private ArrayList<LevelBox<LevelintervalsRecord>> levelBoxArrayList = new ArrayList<>();
    private HashSet<Integer> chosenLevelIDs = new HashSet<>();
    private HashMap<Integer, Integer> numberOfAnswers;
    private HashMap<Integer, Integer> numberOfValidAnswers;
    private HashMap<Integer, Label> integerToLabel;

    private DSLContext create;

    private void setIntegerToLabelMap(){
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
    }


    public void initialize() {
        numberOfAnswers = new HashMap<>();
        numberOfValidAnswers = new HashMap<Integer, Integer>();
        for(int i = 0; i < 12; i+= 1){
            numberOfAnswers.put(i, 0);
            numberOfValidAnswers.put(i, 0);
        }

        setIntegerToLabelMap();

        try {
            create = DSL.using(DatabaseConnection.getInstance().getConnection(), SQLDialect.MYSQL);
            StatsSharedFunctions.create = create;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadLevelIntervalsIntoScrollPane();
        StatsSharedFunctions.putEmptyTextInEachLabel(
                new HashSet<Label>(Arrays.asList(integerToLabel.values().toArray( new Label[0] ))));
        setZaznaczOdznaczAction();

        DateFrom.valueProperty().addListener((obj, oldData, newData) -> refreshResultsAndBarChart());
        DateTo.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldDate, LocalDate newDate) {
                refreshResultsAndBarChart();
            }
        });
//Label_PrymaC
        StatsSharedFunctions.setLabelKeysAction(Arrays.asList(Label_PrymaCzysta,
                Label_SekundaMala, Label_SekundaWielka, Label_TercjaMala,
                Label_TercjaWielka, Label_KwartaCzysta, Label_Tryton,
                Label_KwintaCzysta, Label_SekstaMala, Label_SekstaWielka,
                Label_SeptymaMala, Label_SeptymaWielka, Label_Oktawa), statsLabel);
    }

    private void refreshResultsAndBarChart(){
        Result<IntervalsgameRecord> intervalGamesRecords =
                StatsSharedFunctions.StatsDbHelper.getGamesBetweenDatesAndWithLevelIDs(
                        DateFrom.getValue(), DateTo.getValue(), chosenLevelIDs,
                        INTERVALSGAME, INTERVALSGAME.DATEPLAYED, INTERVALSGAME.INTERVALLEVELID,
                        INTERVALSGAME.USERID
                );
        HashMap<LocalDate, Integer> gamesPerDay =
                StatsSharedFunctions.getNumberOfGamesPlayedPerDay(
                        intervalGamesRecords, INTERVALSGAME.DATEPLAYED
                );

        StatsSharedFunctions.refreshBarChart(DateFrom.getValue(), DateTo.getValue(),
                UsageChart, gamesPerDay);

        HashSet<Integer> intervalGamesID = StatsSharedFunctions.StatsDbHelper.getIntegerColumnIntoSet(
                intervalGamesRecords,INTERVALSGAME.INTERVALSGAMEID);

        numberOfAnswers = StatsSharedFunctions.
                StatsDbHelper.getAnswersIntoDictFilterByGameIDs(intervalGamesID,
                        ANSWERSINTERVALSGAME.INTERVALSGAMEID, ANSWERSINTERVALSGAME,
                        ANSWERSINTERVALSGAME.INTERVALOCCURRENCES,
                        ANSWERSINTERVALSGAME.INTERVALID, false);

        numberOfValidAnswers = StatsSharedFunctions.
                StatsDbHelper.getAnswersIntoDictFilterByGameIDs(intervalGamesID,
                        ANSWERSINTERVALSGAME.INTERVALSGAMEID, ANSWERSINTERVALSGAME,
                        ANSWERSINTERVALSGAME.INTERVALGUESSEDCORRECTLY,
                        ANSWERSINTERVALSGAME.INTERVALID, false);

        StatsSharedFunctions.loadResultsIntoLabels(integerToLabel, numberOfAnswers,
                numberOfValidAnswers);

        if(DateFrom.getValue() == null || DateTo.getValue() == null || DateFrom.getValue().isAfter(DateTo.getValue()) ||
                DateFrom.getValue().equals(DateTo.getValue()) || chosenLevelIDs.isEmpty()){
            StatsSharedFunctions.putEmptyTextInEachLabel(
                    new HashSet<Label>(Arrays.asList(integerToLabel.values().toArray( new Label[0] ))));
        }
    }

    private void loadLevelIntervalsIntoScrollPane() {
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
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException{
        (new SharedFunctionsController()).onBackToMenuButtonClick(event);
    }
}
