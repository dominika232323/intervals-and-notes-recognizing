package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import com.example.demo.jooq.tables.records.AnswersintervalsgameRecord;
import com.example.demo.jooq.tables.records.IntervalsgameRecord;
import com.example.demo.jooq.tables.records.LevelintervalsRecord;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.chart.BarChart;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.time.LocalDate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

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
    }

    private void refreshResultsAndBarChart() {
        // Similar logic as in StatsNotesController, but for interval data
    }

    private void loadLevelIntervalsIntoScrollPane() {
        // Load levels specific to intervals into the scroll pane
    }

    private void setZaznaczOdznaczAction() {
        // Implement actions for ZaznaczWszystko and OdznaczWszystko buttons
    }
    @FXML
    private void onBackToMenuButtonClick(){

    }
}
