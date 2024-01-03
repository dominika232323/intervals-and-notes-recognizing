package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class HistoriaController {
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
    private CheckBox chooseEverythingCheckBox;
    @FXML
    private CheckBox chooseNothingCheckBox;
    @FXML
    private DatePicker firstDatePicker;
    @FXML
    private DatePicker secondDatePicker;
    @FXML
    private ChoiceBox chosenLevelChoiceBox;
    @FXML
    private ChoiceBox chosenAnswersCorrectnessChoiceBox;
    @FXML
    private ScrollPane resultsScrollPane;

    @FXML
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }
}
