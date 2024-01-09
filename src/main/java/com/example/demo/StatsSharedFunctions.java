package com.example.demo;

import com.example.demo.jooq.tables.records.LevelnotesRecord;
import com.example.demo.jooq.tables.records.NotesgamesRecord;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jooq.*;
import org.jooq.Record;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

import static com.example.demo.jooq.tables.Intervalsgame.INTERVALSGAME;
import static com.example.demo.jooq.tables.Levelintervals.LEVELINTERVALS;
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

    static class StatsDbHelper {
        //Ta funkcja przyjmuje jako argument wyniki zapytania SQL dla tabeli NotesGames
        //lub IntervalGames i zwraca wszystkie notesGameID
        public static <R extends Record> HashSet<Integer> getIntegerColumnIntoSet(
                Result<R> table,
                TableField<R, Integer> tableField) {
            HashSet<Integer> toReturn = new HashSet<Integer>();
            for (R gameRecord : table) {
                toReturn.add(gameRecord.get(tableField));
            }
            return toReturn;
        }

        public static <A extends Record> HashMap<Integer, Integer> getAnswersIntoDictFilterByGameIDs(
                HashSet<Integer> gameIDs,
                TableField<A, Integer> answersGamesGameIDField,
                Table<A> answersTable, TableField<A, Integer> answersOccurencesField,
                TableField<A, Integer> answersKeyIDField, boolean trueIfNotes) {
            HashMap<Integer, Integer> toReturn = new HashMap<Integer, Integer>();
            Result<A> answerResult = (Result<A>) create.select().from(answersTable).
                    where(answersGamesGameIDField.in(gameIDs)).fetch();

            for (A record : answerResult) {
                Integer keyID = record.get(answersKeyIDField);
                if (trueIfNotes) {
                    keyID = (keyID - 1) % 12;
                }
                Integer fieldValue = record.get(answersOccurencesField);
                toReturn.merge(keyID, fieldValue, Integer::sum);
            }
            int maxKeyID = trueIfNotes ? 11 : 12;
            for (int i = 0; i <= maxKeyID; i++) {
                toReturn.merge(i, 0, Integer::sum);
            }
            return toReturn;
        }


        public static <R extends Record> Result<R> getGamesBetweenDatesAndWithLevelIDs(
                LocalDate fromDate,
                LocalDate toDate,
                HashSet<Integer> levelIDs,
                Table<R> table,
                TableField<R, LocalDate> dateField,
                TableField<R, Integer> levelIDField,
                TableField<R, Integer> userIDField) {
            Integer userID = ApplicationContext.getInstance().getUser().getUserid();
            return (Result<R>) create
                    .select()
                    .from(table)
                    .where(levelIDField.in(levelIDs)).and(dateField.between(fromDate, toDate)).
                    and(userIDField.eq(ApplicationContext.getInstance().getUser().getUserid())).
                    orderBy(dateField)
                    .fetch();
        }
    }

    public static class History{
        public static <R extends Record, A extends Record> ArrayList<LevelBox<R>> loadGamesIntoScrollPane(ScrollPane Lista, Result<R> results,
                                                                                         TableField<R, LocalDate> dateField,
                                                                                         TableField<R, Integer> gamesIDField,
                                                                                         TableField<R, Integer> tableUserID,
                                                                                         TableField<R, Integer> gameLevelIDField,
                                                                                         Table<A> levelTable,
                                                                                         TableField<A, String> levelNameField,
                                                                                            TableField<A, Integer> levelIDField) {
            ArrayList<LevelBox<R>> toReturn = new ArrayList<LevelBox<R>>();
            VBox vbox = new VBox(10); // VBox with spacing 10
            vbox.setPadding(new Insets(10, 10, 10, 10)); // Optional padding

            Integer currUserID = ApplicationContext.getInstance().getUser().getUserid();
            for (R record : results) {
                int idToSet = record.get(gameLevelIDField);
                String id1 = (String)(create.select().from(levelTable).
                where(levelIDField.eq(idToSet)).fetchOne().get(levelNameField));

                String levelName = record.get(dateField) + ", " + record.get(gamesIDField) + ", Level Name: " + id1;


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

    public static void setLabelKeysAction(List<Label> labelKeys, Label statsLabel){
        for(Label labelKey : labelKeys){
            labelKey.setOnMousePressed(event -> {
                labelKey.setStyle(" -fx-background-color: CCCCCC;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1;");
                statsLabel.setText(labelKey.getText());

            });
            labelKey.setOnMouseReleased(event -> {
                labelKey.setStyle(" -fx-background-color: DED0B6;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1;");
                statsLabel.setText(labelKey.getText());
            });
        }
    }


    public static <R extends Record> HashMap<LocalDate, Integer>
    getNumberOfGamesPlayedPerDay(Result<R> playedGames, TableField<R, LocalDate> dateField) {
        HashMap<LocalDate, Integer> toReturn = new HashMap<LocalDate, Integer>();
        for (Record record : playedGames) {
            LocalDate datePlayed = ((R) record).get(dateField);
            toReturn.put(datePlayed, toReturn.getOrDefault(datePlayed, 0) + 1);
        }
        return toReturn;
    }

    public static <R extends Record> void refreshBarChart(LocalDate dateFromValue,
                                                          LocalDate dateToValue,
                                                          BarChart UsageChart,
                                                          HashMap<LocalDate, Integer> numberOfGamesPerDay) {
        if (dateFromValue == null || dateToValue == null) {
            return;
        }
        UsageChart.getData().clear();
        LocalDate nextDate = dateFromValue;
        XYChart.Series<String, Integer> dateSeries = new XYChart.Series<>();
        while (nextDate.isBefore(dateToValue)) {
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
                                                                                     TableField<R, String> tableField,
                                                                                     TableField<R, Integer> tableUserID) {
        ArrayList<LevelBox<R>> toReturn = new ArrayList<LevelBox<R>>();
        VBox vbox = new VBox(10); // VBox with spacing 10
        vbox.setPadding(new Insets(10, 10, 10, 10)); // Optional padding

        Integer currUserID = ApplicationContext.getInstance().getUser().getUserid();
        Result<R> result = (Result<R>) create.selectFrom(table).
                        where(tableUserID.eq(currUserID).or(tableUserID.isNull())).
                        fetch();

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



    public static void loadResultsIntoLabels(HashMap<Integer, Label> integerToLabel,
                                      HashMap<Integer, Integer> numberOfAnswers,
                                      HashMap<Integer, Integer> numberOfValidAnswers)
    {
        for(HashMap.Entry<Integer, Label> labelEntry : integerToLabel.entrySet()){
            Label label = labelEntry.getValue();
            int labelInt = labelEntry.getKey();
            int allAnswersInt = numberOfAnswers.getOrDefault(labelInt, 0);
            int validAnswersInt = numberOfValidAnswers.getOrDefault(labelInt, 0);
            label.setText("\n  " + validAnswersInt + "/" + allAnswersInt);
        }
    }

    public static void putEmptyTextInEachLabel(HashSet<Label> labels){
        labels.forEach((label) -> {
            label.setText("");
        });
    }

    public static <R extends Record> void selectAllCheckboxes(ArrayList<LevelBox<R>> levelBoxArrayList,
                                           HashSet<Integer> chosenLevelIDs,
                                           TableField<R, Integer> FieldID,
                                           boolean selectOrNot){
        chosenLevelIDs.clear();
        for(LevelBox<R> levelBox : levelBoxArrayList){
            levelBox.getCheckBox().setSelected(selectOrNot);
            if(selectOrNot) {
                chosenLevelIDs.add((Integer) levelBox.getRecord().get(FieldID));
            }
        }
        System.out.println("chosenLevelIDs: " + chosenLevelIDs);
    }
}
