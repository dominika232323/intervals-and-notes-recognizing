package com.example.demo;

import com.example.demo.jooq.tables.records.LevelnotesRecord;
import com.example.demo.jooq.tables.records.NotesgamesRecord;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jooq.*;
import org.jooq.Record;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.example.demo.jooq.tables.Intervalsgame.INTERVALSGAME;
import static com.example.demo.jooq.tables.Levelnotes.LEVELNOTES;
import static com.example.demo.jooq.tables.Notesgames.NOTESGAMES;

class LevelBox <R extends Record>{
    private CheckBox checkBox;
    private R record;
    private String text;
    public LevelBox(R newRecord, String checkBoxText){
        this.record = newRecord;
        text = checkBoxText;
        checkBox = new CheckBox(checkBoxText);
    }
    public String getText(){
        return text;
    }
    public R getRecord(){
        return record;
    }
    public CheckBox getCheckBox(){
        return checkBox;
    }
}
public class StatsSharedFunctions {
    public static DSLContext create;
    static class StatsDbHelper{
        //public static DSLContext create;

        public static <R extends Record> Result<R> getGamesBetweenDates(
                LocalDate fromDate,
                LocalDate toDate,
                Table<R> table,
                TableField<R, LocalDate> datePlayedField)
        {
            return (Result<R>) create
                    .select()
                    .from(table)
                    .where(datePlayedField.between(fromDate, toDate))
                    .fetch();

        }

        public static <R extends Record> Result<R> getGamesWithGivenLevelIDs(
                HashSet<Integer> levelIDs,
                Table<R> table,
                TableField<R, Integer> levelIDField)
        {
            return (Result<R>) create
                    .select()
                    .from(table)
                    .where(levelIDField.in(levelIDs))
                    .fetch();

        }

        public static <R extends Record> Result<R> getGamesBetweenDatesAndWithLevelIDs(
                LocalDate fromDate,
                LocalDate toDate,
                HashSet<Integer> levelIDs,
                Table<R> table,
                TableField<R, LocalDate> dateField,
                TableField<R, Integer> levelIDField)

        {
            return (Result<R>) create
                    .select()
                    .from(table)
                    .where(levelIDField.in(levelIDs)).and(dateField.between(fromDate, toDate))
                    .fetch();

        }
    }

    public static <R extends Record> HashMap<LocalDate, Integer>
    getNumberOfGamesPlayedPerDay(Result<R> playedGames, TableField<R, LocalDate> dateField)
    {
        HashMap<LocalDate, Integer> toReturn = new HashMap<LocalDate, Integer>();
        for(Record record : playedGames){
            LocalDate datePlayed = ((R)record).get(dateField);
            toReturn.put(datePlayed, toReturn.getOrDefault(datePlayed, 0) + 1);
        }
        return toReturn;
    }

    public static <R extends Record> void refreshBarChart(LocalDate dateFromValue,
                                       LocalDate dateToValue,
                                       BarChart UsageChart,
                                       HashMap<LocalDate, Integer> numberOfGamesPerDay)
    {
        if(dateFromValue == null || dateToValue == null){
            return;
        }
        UsageChart.getData().clear();
        LocalDate nextDate = dateFromValue;
        XYChart.Series<String, Integer> dateSeries= new XYChart.Series<>();
        while(nextDate.isBefore(dateToValue)){
            int count = numberOfGamesPerDay.getOrDefault(nextDate, 0);
            dateSeries.getData().add(new XYChart.Data<>(nextDate.toString(), count));
            nextDate = nextDate.plusDays(1);
        }

        UsageChart.getData().clear();
        UsageChart.getData().add(dateSeries);
        UsageChart.getXAxis().setTickLabelRotation(-90);
        UsageChart.getXAxis().setTickLabelFont(Font.font("Verdana", FontWeight.BOLD, 7));
    }

    public static <R extends Record> ArrayList<LevelBox<R>> loadLevelsIntoScrollPane(ScrollPane Lista,
                                                                           Table<R> table,
                                                                           TableField<R, String> tableField)
    {
        ArrayList<LevelBox<R>> toReturn = new ArrayList<LevelBox<R>>();
        VBox vbox = new VBox(10); // VBox with spacing 10
        vbox.setPadding(new Insets(10, 10, 10, 10)); // Optional padding


        Result<R> result = (Result<R>) create.selectFrom(table).fetch();

        for (R record : result) {
            String levelName = record.get(tableField);
            LevelBox<R> levelBox = new LevelBox<R>(record, levelName);
            levelBox.getCheckBox().setPadding(new Insets(5, 10, 5, 10));
            levelBox.getCheckBox().setStyle("-fx-border-color: black");
            vbox.getChildren().add(levelBox.getCheckBox());
            toReturn.add(levelBox);
        }

        Lista.setContent(vbox);
        return toReturn;
    }
}
