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
    private ChoiceBox<String> chosenAnswersCorrectnessChoiceBox;
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
    public void showResults(ActionEvent event) {
        LocalDate firstDate = getDate(firstDatePicker);
        LocalDate secondDate = getDate(secondDatePicker);

        boolean isNoteGameChosen = notesGameCheckBox.isSelected();
        boolean isIntervalGameChosen = intervalGameCheckBox.isSelected();
        boolean isLevelChosen = chooseLevelCheckBox.isSelected();
        boolean isAnswersCorrectnessChosen = chooseAnswersCorrectnessCheckBox.isSelected();

        if (!isNoteGameChosen && !isIntervalGameChosen) {
            isNoteGameChosen = true;
            isIntervalGameChosen = true;
        }

        if (isLevelChosen) {
            String level = chosenLevelChoiceBox.getValue();
        }

        int lowerBound;
        int upperBound;

        if (isAnswersCorrectnessChosen) {
            String correctness = chosenAnswersCorrectnessChoiceBox.getValue();
            lowerBound = getLowerBoundFromCorrectness(correctness);
            upperBound = getUpperBoundFromCorrectness(correctness);
        }
    }

    private int getLowerBoundFromCorrectness(String correctness) {
        char firstChar = correctness.charAt(0);
        int lowerBound = Character.getNumericValue(firstChar);
        return lowerBound * 10;
    }

    private int getUpperBoundFromCorrectness(String correctness) {
        char secondLastChar = correctness.charAt(correctness.length() - 2);
        int upperBound = Character.getNumericValue(secondLastChar);
        upperBound = upperBound * 10;

        if (upperBound == 0) {
            upperBound = 100;
        }

        return upperBound;
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
        Result<Record> levelsInfo = GameHistoryTablesOperations.getNotesLevelsByID(user.getUserid(), create);
        ArrayList<String> levelNames = new ArrayList<String>();

        for (Record r : levelsInfo) {
            String name = r.get(LEVELNOTES.NAME);
            levelNames.add(name);
        }

        return levelNames;
    }

    private ArrayList<String> createArrayWithIntervalLevels(DSLContext create) {
        Result<Record> levelsInfo = GameHistoryTablesOperations.getIntervalLevelsByID(user.getUserid(), create);
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
        fillChoiceBoxWithAnswersCorrectness();
    }

    private void fillChoiceBoxWithAnswersCorrectness() {
        ArrayList<String> answersCorrectness = new ArrayList<String>();

        int lower_bound = 0;
        int upper_bound = 10;

        while (upper_bound < 110) {
            String correctness = lower_bound + "% - " + upper_bound + "%";
            answersCorrectness.add(correctness);

            lower_bound = lower_bound + 10;
            upper_bound = upper_bound + 10;
        }

        chosenAnswersCorrectnessChoiceBox.setItems(FXCollections.observableArrayList(answersCorrectness));
    }
}
