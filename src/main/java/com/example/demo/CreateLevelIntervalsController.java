package com.example.demo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;

public class CreateLevelIntervalsController {
    @FXML
    private Button editLevelButton;

    @FXML
    private Button createLevelButton;

    @FXML
    private ListView<?> levelsListView;

    @FXML
    private Button deleteLevelButton;

    @FXML
    private Button backToMenuButton;

    @FXML
    void deleteLevelOnClick(ActionEvent event) {

    }

    @FXML
    void editLevelOnClick(ActionEvent event) {

    }

    @FXML
    void createNewLevelOnClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "intervals-level-form-view.fxml");
    }

    @FXML
    void backToMenuOnClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);

    }
}
