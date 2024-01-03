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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    public void showResults(ActionEvent event) {
        LocalDate date = firstDatePicker.getValue();
        System.out.println("Selected Date: " + date);
//        LocalDate firstDate = getDate(event, firstDatePicker);
//        LocalDate secondDate = getDate(event, secondDatePicker);
//
//        System.out.println(firstDate);
//        System.out.println(secondDate);
    }

//    @FXML
//    public LocalDate getDate(ActionEvent event, DatePicker datePicker) {
//        return datePicker.getValue();
//    }
}
