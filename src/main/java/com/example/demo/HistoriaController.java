package com.example.demo;

import com.example.demo.jooq.tables.records.UsersRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.demo.jooq.tables.Levelnotes.LEVELNOTES;
import static com.example.demo.jooq.tables.Levelintervals.LEVELINTERVALS;


public class HistoriaController implements Initializable {
    @FXML
    private Label historyLabel;
    @FXML
    private Button backToMenuButton;
    @FXML
    private Button showResultsButton;
    @FXML
    private CheckBox notesGameCheckBox;
    @FXML
    private CheckBox intervalGameCheckBox;
    @FXML
    private CheckBox selectAllCheckBox;
    @FXML
    private DatePicker firstDatePicker;
    @FXML
    private DatePicker secondDatePicker;
    @FXML
    private ChoiceBox<String> chosenLevelChoiceBox;
    @FXML
    private ChoiceBox<String> lowerBoundCorrectnessChoiceBox;
    @FXML
    private ChoiceBox<String> upperBoundCorrectnessChoiceBox;
    @FXML
    private TableView<Game> resultsTableView;
    @FXML
    private TableColumn<Game, String> gameNameTableColumn;
    @FXML
    private TableColumn<Game, String> levelNameTableColumn;
    @FXML
    private TableColumn<Game, String> correctnessTableColumn;
    @FXML
    private TableColumn<Game, LocalDate> dateTableColumn;

    ApplicationContext context = ApplicationContext.getInstance();
    UsersRecord user = context.getUser();

    @FXML
    public void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }

    @FXML
    public void showResults(ActionEvent event) throws SQLException {
        DSLContext create = SharedFunctionsController.getDLCContex();

        boolean isNoteGameChosen = notesGameCheckBox.isSelected();
        boolean isIntervalGameChosen = intervalGameCheckBox.isSelected();

        Result<Record> games = getGames(isNoteGameChosen, isIntervalGameChosen, create);
        filterGamesByLevel(games);

        filterGamesByDatePlayed(games);

        fillTableView(games);
    }

    private void fillTableView(Result<Record> games) {
        gameNameTableColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("name"));
        levelNameTableColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("level"));
        correctnessTableColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("correctness"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<Game, LocalDate>("date"));

        ObservableList<Game> list = FXCollections.observableArrayList();

        for (Record r : games) {
            String name = r.get(DSL.field("game", String.class));
            String level = r.get(DSL.field("name", String.class));
            int correctness = 0;
            LocalDate date = r.get(DSL.field("datePlayed", LocalDate.class));

            Game g = new Game(name, level, correctness, date);
            list.add(g);
        }

        resultsTableView.setItems(list);
    }

    private Result<Record> getGames(boolean isNotesGameChosen, boolean isIntervalsGameChosen, DSLContext create) {
        Result<Record> games;

        if (isNotesGameChosen == isIntervalsGameChosen) {
            games = GameHistoryTablesOperations.getAllGamesJoinedWithLevelsByUserID(user.getUserid(), create);
        }
        else if (isNotesGameChosen) {
            games = GameHistoryTablesOperations.getNotesGamesJoinedWithLevelsByUserID(user.getUserid(), create);
        }
        else {
            games = GameHistoryTablesOperations.getIntervalsGamesJoinedWithLevelsByUserID(user.getUserid(), create);
        }

        return games;
    }

    private void filterGamesByLevel(Result<Record> games) {
        String level = chosenLevelChoiceBox.getValue();

        if (level != null) {
            removeLevelsOtherThanLevel(level, games);
        }
    }

    private void removeLevelsOtherThanLevel(String levelName, Result<Record> games) {
        games.removeIf(record -> !record.get(DSL.field("name", String.class)).equals(levelName));
    }

    private void filterGamesByAnswersCorrectness(Result<Record> games) {
        String lowerBoundCorrectness = lowerBoundCorrectnessChoiceBox.getValue();
        String upperBoundCorrectness = upperBoundCorrectnessChoiceBox.getValue();

        if (lowerBoundCorrectness != null && upperBoundCorrectness != null)
        {
            int lowerBound = getNumberFromCorrectness(lowerBoundCorrectness);
            int upperBound = getNumberFromCorrectness(upperBoundCorrectness);

            int[] bounds = {lowerBound, upperBound};

            if (bounds[1] < bounds[0]) {
                swap(bounds);
            }
        }
    }

//    private void getBounds(int[] bounds) {
//        String lowerBoundCorrectness = lowerBoundCorrectnessChoiceBox.getValue();
//        String upperBoundCorrectness = upperBoundCorrectnessChoiceBox.getValue();
//
//        boolean nullValues = false;
//
//        if (lowerBoundCorrectness == null) {
//            bounds[0] = Integer.parseInt(null);
//            nullValues = true;
//        }
//
//        if (upperBoundCorrectness == null) {
//
//        }
//
//        bounds[0] = getNumberFromCorrectness(lowerBoundCorrectness);
//        bounds[1] = getNumberFromCorrectness(upperBoundCorrectness);
//
//        if (bounds[1] < bounds[0]) {
//            swap(bounds);
//        }
//    }

    private int getNumberFromCorrectness(String correctness) {
        String numberStr = correctness.substring(0, correctness.length() - 1);
        return Integer.parseInt(numberStr);
    }

    private void swap(int[] numbers) {
        int temp = numbers[0];
        numbers[0] = numbers[1];
        numbers[1] = temp;
    }

    private void filterGamesByDatePlayed(Result<Record> games) {
        LocalDate firstDate = getDate(firstDatePicker);
        LocalDate secondDate = getDate(secondDatePicker);

        if (firstDate != null) {
            removeRecordsPlayedBefore(firstDate, games);
        }

        if (secondDate != null) {
            removeRecordsPlayedAfter(secondDate, games);
        }
    }

    private void removeRecordsPlayedBefore(LocalDate date, Result<Record> games) {
        games.removeIf(record -> record.get(DSL.field("datePlayed", LocalDate.class)).isBefore(date));
    }

    private void removeRecordsPlayedAfter(LocalDate date, Result<Record> games) {
        games.removeIf(record -> record.get(DSL.field("datePlayed", LocalDate.class)).isAfter(date));
    }

    @FXML
    public void selectAll(ActionEvent event) throws SQLException {
        if (selectAllCheckBox.isSelected()) {
            notesGameCheckBox.setSelected(true);
            intervalGameCheckBox.setSelected(true);
            fillLevelsChoiceBox();
        }
        else {
            notesGameCheckBox.setSelected(false);
            intervalGameCheckBox.setSelected(false);
            fillLevelsChoiceBox();
        }
    }

    @FXML
    public void onChosenGameCheckBox(ActionEvent event) throws SQLException {
        fillLevelsChoiceBox();
    }

    private void fillLevelsChoiceBox() throws SQLException {
        DSLContext create = SharedFunctionsController.getDLCContex();
        ArrayList<String> levelNames = new ArrayList<String>();

        if (notesGameCheckBox.isSelected() == intervalGameCheckBox.isSelected()) {
            levelNames = createArrayWithNotesLevels(create);
            levelNames.addAll(createArrayWithIntervalLevels(create));
        }
        else if (notesGameCheckBox.isSelected()) {
            levelNames = createArrayWithNotesLevels(create);
        }
        else {
            levelNames = createArrayWithIntervalLevels(create);
        }

        levelNames.add(0, null);
        chosenLevelChoiceBox.setItems(FXCollections.observableArrayList(levelNames));
    }

    private ArrayList<String> createArrayWithNotesLevels(DSLContext create) {
        Result<Record> levelsInfo = GameHistoryTablesOperations.getNotesLevelsByUserID(user.getUserid(), create);
        ArrayList<String> levelNames = new ArrayList<String>();

        for (Record r : levelsInfo) {
            String name = r.get(LEVELNOTES.NAME);
            levelNames.add(name);
        }

        return levelNames;
    }

    private ArrayList<String> createArrayWithIntervalLevels(DSLContext create) {
        Result<Record> levelsInfo = GameHistoryTablesOperations.getIntervalLevelsByUserID(user.getUserid(), create);
        ArrayList<String> levelNames = new ArrayList<String>();

        for (Record r : levelsInfo) {
            String name = r.get(LEVELINTERVALS.NAME);
            levelNames.add(name);
        }

        return levelNames;
    }

    @FXML
    public LocalDate getDate(DatePicker datePicker) {
        return datePicker.getValue();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillChoiceBoxWithAnswersCorrectness(lowerBoundCorrectnessChoiceBox);
        fillChoiceBoxWithAnswersCorrectness(upperBoundCorrectnessChoiceBox);

        try {
            fillLevelsChoiceBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillChoiceBoxWithAnswersCorrectness(ChoiceBox<String> choiceBox) {
        ArrayList<String> answersCorrectness = new ArrayList<String>();

        int number = 0;

        while (number <= 100) {
            String correctness = number + "%";
            answersCorrectness.add(correctness);

            number = number + 10;
        }

        answersCorrectness.add(0, null);
        choiceBox.setItems(FXCollections.observableArrayList(answersCorrectness));
    }
}
