package com.example.demo;

import com.example.demo.jooq.tables.Notesgames;
import com.example.demo.jooq.tables.records.LevelnotesRecord;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;

import static com.example.demo.jooq.tables.Intervalsgame.INTERVALSGAME;
import static com.example.demo.jooq.tables.Levelnotes.LEVELNOTES;

import static com.example.demo.jooq.tables.Notesgames.NOTESGAMES;
import com.example.demo.jooq.tables.records.NotesgamesRecord;

import java.time.LocalDate;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.example.demo.StatsSharedFunctions;



class DbHelper{
    public static DSLContext create;

    public static Result<Record> getNotesGamesBetweenDates(LocalDate fromDate, LocalDate toDate){
        Result<Record> toReturn;
        toReturn = create.select().from(NOTESGAMES).where(
                NOTESGAMES.DATEPLAYED.between(fromDate, toDate)
        ).fetch();
        return toReturn;
        //return StatsSharedFunctions.StatsDbHelper.getGamesBetweenDates(fromDate, toDate, NOTESGAMES, NOTESGAMES.DATEPLAYED);
    }



    private DbHelper(){}
}

public class StatsNotesController {
    @FXML
    private Label statsLabel, C1, CSharp, D, DSharp, E, F, FSharp, G, GSharp, A, ASharp, B, C2;
    @FXML
    private ScrollPane Lista;
    @FXML
    private BarChart UsageChart;
    @FXML
    private DSLContext create;
    @FXML
    private DatePicker DateFrom, DateTo;

    private LocalDate dateFromValue, dateToValue;

    private ArrayList<LevelBox<LevelnotesRecord>> levelBoxArrayList;
    private HashSet<Integer> chosenLevelIDs;
    private HashMap<Integer, Integer> numberOfAnswers;
    private HashMap<Integer, Integer> numberOfValidAnswers;
    private HashMap<Integer, Label> integerToLabel;

    public void initialize() {
        chosenLevelIDs = new HashSet<Integer>();
        levelBoxArrayList = new ArrayList<LevelBox<LevelnotesRecord>>();
        numberOfAnswers = new HashMap<>();
        numberOfValidAnswers = new HashMap<Integer, Integer>();
        for(int i = 0; i < 12; i+= 1){
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

        try {
            create = DSL.using(DatabaseConnection.getInstance().getConnection(), SQLDialect.MYSQL);
            DbHelper.create = create;
            StatsSharedFunctions.create = create;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadLevelNotesIntoScrollPane();



        DateFrom.valueProperty().addListener((obj, oldData, newData) -> refreshResultsAndBarChart());
        DateTo.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldDate, LocalDate newDate) {
                refreshResultsAndBarChart();
            }
        }); //Do DateFrom użyliśmy funkcji lambda, która jest funkcjonalnie równa użyciu
        //klasy anonimowej, jak w przypadku DateTo. Poczytaj o interfejsach SAM
    }

    private void refreshResultsAndBarChart(){
        Result<NotesgamesRecord> notesGamesRecords_ID_Date =
                StatsSharedFunctions.StatsDbHelper.getGamesBetweenDatesAndWithLevelIDs(
                        DateFrom.getValue(), DateTo.getValue(), chosenLevelIDs,
                        NOTESGAMES, NOTESGAMES.DATEPLAYED, NOTESGAMES.LEVELNOTESID
                );

        HashMap<LocalDate, Integer> gamesPerDay =
                StatsSharedFunctions.getNumberOfGamesPlayedPerDay(
                        notesGamesRecords_ID_Date, NOTESGAMES.DATEPLAYED
                );

        StatsSharedFunctions.refreshBarChart(DateFrom.getValue(), DateTo.getValue(),
                        UsageChart, gamesPerDay);
    }
    //private Result<NotesgamesRecord> gamesRecordProperIdAndDate;


    private void loadLevelNotesIntoScrollPane(){
        levelBoxArrayList = StatsSharedFunctions.loadLevelsIntoScrollPane(Lista, LEVELNOTES, LEVELNOTES.NAME);
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


    @FXML
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }
}
