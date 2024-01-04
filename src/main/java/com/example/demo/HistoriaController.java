package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

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
    public void fillChoiceBoxWithLevels(ActionEvent event) {
        if (notesGameCheckBox.isSelected() == intervalGameCheckBox.isSelected()) {

        }
        else if (notesGameCheckBox.isSelected()) {

        }
        else {

        }
    }

    @FXML
    public LocalDate getDate(DatePicker datePicker) {
        return datePicker.getValue();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
