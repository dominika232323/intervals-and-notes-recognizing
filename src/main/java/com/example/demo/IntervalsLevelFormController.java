package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.jooq.Context;

import java.io.IOException;

public class IntervalsLevelFormController {

    @FXML
    private RadioButton upRadioButton;

    @FXML
    private Label errorsLabel;

    @FXML
    private RadioButton togetherRadioButton;

    @FXML
    private Button goBackButton;

    @FXML
    private Button saveLevelButton;

    @FXML
    private RadioButton downRadioButton;

    @FXML
    private TextField repetitionsTextField;

    @FXML
    private ComboBox<?> instrumentsComboBox;

    @FXML
    private ToggleGroup intervalsType;

    @FXML
    private TextField levelNameTextField;

    public void initialize(){
        ApplicationContext context = ApplicationContext.getInstance();
        // We create new level
        if (context.getLevelInterval() == null){

        }
        // We edit level specified in context
        else {

        }
    }


    @FXML
    void goBackOnClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "create-level-intervals-view.fxml");
    }

    @FXML
    void saveLevelOnClick(ActionEvent event) {

    }
}
