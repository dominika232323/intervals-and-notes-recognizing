package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class NotesLevelFormController {

    @FXML
    private ComboBox<?> instrumentComboBox;

    @FXML
    private Button exitButton;

    @FXML
    private TextField endingWaveTextField;

    @FXML
    private TextField startingWaveTextField;

    @FXML
    private ComboBox<?> highestNoteComboBox;

    @FXML
    private Pane staffPane;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<?> lowestNoteComboBox;

    @FXML
    private TextField repetitionsInWave;

    @FXML
    void exitOnClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "create-level-notes-view.fxml");
    }

    @FXML
    void saveOnClick(ActionEvent event) {

    }

    public void initialize(){
        Circle circle = new Circle();
        circle.setRadius(10);
        circle.setCenterX(90);
        circle.setCenterY(130);
        staffPane.getChildren().add(circle);

        Circle circle2 = new Circle();
        circle2.setRadius(10);
        circle2.setCenterX(600);
        circle2.setCenterY(30);
        staffPane.getChildren().add(circle2);

    }

}