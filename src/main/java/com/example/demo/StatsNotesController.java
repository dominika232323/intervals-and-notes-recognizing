package com.example.demo;

import com.example.demo.jooq.Db;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import static com.example.demo.jooq.tables.Levelnotes.LEVELNOTES;
import com.example.demo.jooq.tables.records.LevelnotesRecord;

import static com.example.demo.jooq.tables.Notesgames.NOTESGAMES;
import com.example.demo.jooq.tables.records.NotesgamesRecord;

import static com.example.demo.jooq.tables.Answersnotesgame.ANSWERSNOTESGAME;
import com.example.demo.jooq.tables.records.AnswersnotesgameRecord;
import org.jooq.impl.QOM;

import java.time.LocalDate;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

class DbHelper{
    public static DSLContext create;
    public static Result<Record> getNotesGamesBetweenDates(LocalDate fromDate, LocalDate toDate){
        Result<Record> toReturn;
        toReturn = create.select().from(NOTESGAMES).where(
                NOTESGAMES.DATEPLAYED.between(fromDate, toDate)
        ).fetch();
        return toReturn;
    }

    public static HashMap<LocalDate, Integer> getNumberOfGamesPlayedPerDay(LocalDate fromDate, LocalDate toDate){ //throws SQLException{
        HashMap<LocalDate, Integer> toReturn = new HashMap<LocalDate, Integer>();
        Result<Record> recordsNoteGames = getNotesGamesBetweenDates(fromDate, toDate);
        for(Record record : recordsNoteGames){
            LocalDate datePlayed = ((NotesgamesRecord)record).getDateplayed();
            //LocalDate datePlayed1 = record.get(NOTESGAMES.DATEPLAYED); //alternative
            toReturn.put(datePlayed, toReturn.getOrDefault(datePlayed, 0) + 1);
        }
        return toReturn;
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

    private HashMap<Integer, Integer> numberOfAnswers;
    private HashMap<Integer, Integer> numberOfValidAnswers;
    private HashMap<Integer, Label> integerToLabel;

    public void initialize() {
        numberOfAnswers = new HashMap<Integer, Integer>();
        numberOfAnswers.put(0, 0);
        numberOfAnswers.put(1, 0);
        numberOfAnswers.put(2, 0);
        numberOfAnswers.put(3, 0);
        numberOfAnswers.put(4, 0);
        numberOfAnswers.put(5, 0);
        numberOfAnswers.put(6, 0);
        numberOfAnswers.put(7, 0);
        numberOfAnswers.put(8, 0);
        numberOfAnswers.put(9, 0);
        numberOfAnswers.put(10, 0);
        numberOfAnswers.put(11, 0);

        numberOfValidAnswers = new HashMap<Integer, Integer>();
        numberOfValidAnswers.put(0, 0);
        numberOfValidAnswers.put(1, 0);
        numberOfValidAnswers.put(2, 0);
        numberOfValidAnswers.put(3, 0);
        numberOfValidAnswers.put(4, 0);
        numberOfValidAnswers.put(5, 0);
        numberOfValidAnswers.put(6, 0);
        numberOfValidAnswers.put(7, 0);
        numberOfValidAnswers.put(8, 0);
        numberOfValidAnswers.put(9, 0);
        numberOfValidAnswers.put(10, 0);
        numberOfValidAnswers.put(11, 0);

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadLevelNotesIntoScrollPane();


        //DateFrom.setOnMouseClicked(event -> checkDatePickers());
        //DateTo.setOnMouseClicked(event -> checkDatePickers());

        DateFrom.valueProperty().addListener((obj, oldData, newData) -> refreshBarChart());
        DateTo.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldDate, LocalDate newDate) {
                refreshBarChart();
            }
        }); //Do DateFrom użyliśmy funkcji lambda, która jest funkcjonalnie równa użyciu
        //klasy anonimowej, jak w przypadku DateTo.


        C1.setOnMouseClicked(event -> {

            XYChart.Series dataSeries1 = new XYChart.Series<>();
            UsageChart.getXAxis().setTickLabelRotation(-90);

            dataSeries1.getData().add(new XYChart.Data<>("11-12-2002", 567));
            dataSeries1.getData().add(new XYChart.Data<>("2", 65));
            dataSeries1.getData().add(new XYChart.Data<>("3", 23));

            UsageChart.getData().clear();


            UsageChart.getData().add(dataSeries1);

        });

    }

    private void refreshBarChart(){
        System.out.println("A");
        dateFromValue = DateFrom.getValue();
        dateToValue = DateTo.getValue();
        if(dateFromValue == null || dateToValue == null){
            return;
        }

        HashMap<LocalDate, Integer> numberOfGamesPerDay = DbHelper.getNumberOfGamesPlayedPerDay(
                                                            dateFromValue, dateToValue);

        LocalDate nextDate = dateFromValue;
        XYChart.Series<String, Integer> dateSeries= new XYChart.Series<>();
        while(nextDate.isBefore(dateToValue)){
            int count = numberOfGamesPerDay.getOrDefault(nextDate, 0);
            dateSeries.getData().add(new XYChart.Data<>(nextDate.toString(), count));
            System.out.println(nextDate.toString() + ": " + numberOfGamesPerDay.getOrDefault(nextDate, 0));
            nextDate = nextDate.plusDays(1);
        }

        UsageChart.getData().clear();
        UsageChart.getData().add(dateSeries);
        UsageChart.getXAxis().setTickLabelRotation(-90);
        UsageChart.getXAxis().setTickLabelFont(Font.font("Verdana", FontWeight.BOLD, 7));
        //UsageChart.requestLayout();
        //UsageChart.requestFocus();
        UsageChart.getParent().requestLayout();
    }



    private void loadLevelNotesIntoScrollPane() {
        VBox vbox = new VBox(10); // VBox with spacing 10
        vbox.setPadding(new Insets(10, 10, 10, 10)); // Optional padding

        // Fetch data from LevelNotes table
        Result<?> result = create.selectFrom(LEVELNOTES).fetch();

        // For each row in the result, create a checkbox
        for (Record record : result) {
            String levelName = record.get(LEVELNOTES.NAME);
            CheckBox checkBox = new CheckBox(levelName);
            checkBox.setId("CheckBox: " + record.get(LEVELNOTES.LEVELID));
            checkBox.setOnMouseClicked(event -> {
                if (checkBox.isSelected()) {
                    System.out.println("Selected: " + checkBox.getId().substring(10));
                } else {
                    System.out.println("Not selected");
                }
            });
            checkBox.setPadding(new Insets(5, 10, 5, 10));
            checkBox.setStyle("-fx-border-color: black");
            vbox.getChildren().add(checkBox);
        }

        Lista.setContent(vbox);
    }



    @FXML
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }
}
