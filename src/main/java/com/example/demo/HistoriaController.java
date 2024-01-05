package com.example.demo;

import com.example.demo.jooq.tables.records.UsersRecord;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import java.io.IOException;
import java.net.URL;
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
    private CheckBox chooseLevelCheckBox;
    @FXML
    private CheckBox chooseAnswersCorrectnessCheckBox;
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
    private TableView<String> resultsTableView;
    @FXML
    private TableColumn gameNameTableColumn;
    @FXML
    private TableColumn levelNameTableColumn;
    @FXML
    private TableColumn correctnessTableColumn;
    @FXML
    private TableColumn dateTableColumn;

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

        boolean isLevelChosen = chooseLevelCheckBox.isSelected();

        String level = null;
        if (isLevelChosen) {
            level = chosenLevelChoiceBox.getValue();
        }

        boolean isAnswersCorrectnessChosen = chooseAnswersCorrectnessCheckBox.isSelected();
        int[] bounds = {0, 0};

        if (isAnswersCorrectnessChosen) {
            String lowerBoundCorrectness = lowerBoundCorrectnessChoiceBox.getValue();
            String upperBoundCorrectness = upperBoundCorrectnessChoiceBox.getValue();

            bounds[0] = getNumberFromCorrectness(lowerBoundCorrectness);
            bounds[1] = getNumberFromCorrectness(upperBoundCorrectness);

            if (bounds[1] < bounds[0]) {
                swap(bounds);
            }
        }

        LocalDate firstDate = getDate(firstDatePicker);
        LocalDate secondDate = getDate(secondDatePicker);

        System.out.println(bounds[0]);
        System.out.println(bounds[1]);
    }

    private Result<Record> getGames(boolean isNotesGameChosen, boolean isIntervalsGameChosen, DSLContext create) {
        Result<Record> games;

        if (isNotesGameChosen == isIntervalsGameChosen) {
            games = GameHistoryTablesOperations.getAllGamesByUserID(user.getUserid(), create);
        }
        else if (isNotesGameChosen) {
            games = GameHistoryTablesOperations.getNotesGamesByUserID(user.getUserid(), create);
        }
        else {
            games = GameHistoryTablesOperations.getIntervalGamesByUserID(user.getUserid(), create);
        }

        return games;
    }

    private void filterGamesByChosenLevel(String levelName, Result<Record> games) {
        games.removeIf(record -> !record.getValue("levelName").equals(levelName));
    }

    private void swap(int[] numbers) {
        int temp = numbers[0];
        numbers[0] = numbers[1];
        numbers[1] = temp;
    }

    private int getNumberFromCorrectness(String correctness) {
        String numberStr = correctness.substring(0, correctness.length() - 1);
        return Integer.parseInt(numberStr);
    }

    @FXML
    public void selectAll(ActionEvent event) {
        if (selectAllCheckBox.isSelected()) {
            notesGameCheckBox.setSelected(true);
            intervalGameCheckBox.setSelected(true);
            chooseLevelCheckBox.setSelected(true);
            chooseAnswersCorrectnessCheckBox.setSelected(true);
        }
        else {
            notesGameCheckBox.setSelected(false);
            intervalGameCheckBox.setSelected(false);
            chooseLevelCheckBox.setSelected(false);
            chooseAnswersCorrectnessCheckBox.setSelected(false);
        }
    }

    @FXML
    public void fillChoiceBoxWithLevels(ActionEvent event) throws SQLException {
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
    }

    private void fillChoiceBoxWithAnswersCorrectness(ChoiceBox<String> choiceBox) {
        ArrayList<String> answersCorrectness = new ArrayList<String>();

        int number = 0;

        while (number <= 100) {
            String correctness = number + "%";
            answersCorrectness.add(correctness);

            number = number + 10;
        }

        choiceBox.setItems(FXCollections.observableArrayList(answersCorrectness));
    }
}
