package com.example.demo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class IntervalsGameController {
    @FXML
    private Label current_question_label;

    @FXML
    private Label incorrect_answers_label;

    @FXML
    private Label correct_answers_label;

    @FXML
    void intervalChosenOnClick(ActionEvent event) {
        System.out.println("eeee");
    }

    @FXML
    void exitOnClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "inter-view.fxml");
    }

    @FXML
    void exitAndSaveOnClick(ActionEvent event) {

    }
}
