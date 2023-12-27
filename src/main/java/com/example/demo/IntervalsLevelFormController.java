package com.example.demo;

import com.example.demo.jooq.Tables;
import com.example.demo.jooq.tables.records.LevelintervalsRecord;
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
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
        LevelintervalsRecord levelInterval = context.getLevelInterval();

        // We create new level
        if (levelInterval == null){
            // We don't have to do anything
        }
        // We edit level specified in context
        else {
            // We have to fill in form so it represented edited level
            levelNameTextField.setText(levelInterval.getName());
            repetitionsTextField.setText(levelInterval.getNumberofrepetitions().toString());
            if (levelInterval.getUp() != 0){
                upRadioButton.setSelected(true);
            } else if (levelInterval.getDown() != 0) {
                downRadioButton.setSelected(true);
            }else {
                togetherRadioButton.setSelected(true);
            }

        }
    }

    boolean checkIfValid(){
        // TODO make it work properly
        return true;
    }

    @FXML
    void goBackOnClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "create-level-intervals-view.fxml");
    }

    @FXML
    void saveLevelOnClick(ActionEvent event) throws SQLException, IOException {
        if (!checkIfValid()){
            errorsLabel.setText("Nieprawidłowe dane!");
            return;
        }
        ApplicationContext context = ApplicationContext.getInstance();
        LevelintervalsRecord levelInterval = context.getLevelInterval();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        String newName;
        Integer newRepetitions;
        Byte newUp;
        Byte newDown;
        Byte newTogether;

        newName = levelNameTextField.getText();
        newRepetitions = Integer.parseInt(repetitionsTextField.getText());
        if (upRadioButton.isSelected()){
            newUp = 1;
            newDown = 0;
            newTogether = 0;
        } else if (downRadioButton.isSelected()) {
            newUp = 0;
            newDown = 1;
            newTogether = 0;
        }else {
            newUp = 0;
            newDown = 0;
            newTogether = 1;
        }

        // We save new level to database
        if (levelInterval == null){
            LevelintervalsRecord newIntervalLevel = create.newRecord(Tables.LEVELINTERVALS);
            newIntervalLevel.setName(newName);
            newIntervalLevel.setNumberofrepetitions(newRepetitions);
            newIntervalLevel.setUp(newUp);
            newIntervalLevel.setDown(newDown);
            newIntervalLevel.setTogether(newTogether);
            //  TODO set author of level
            newIntervalLevel.store();
        } else {
            levelInterval.setName(newName);
            levelInterval.setNumberofrepetitions(newRepetitions);
            levelInterval.setUp(newUp);
            levelInterval.setDown(newDown);
            levelInterval.setTogether(newTogether);
            // TODO set author of level
            levelInterval.store();
        }
        context.setLevelInterval(null);
        SharedFunctionsController button = new SharedFunctionsController();
        button.changeStage(event, "create-level-intervals-view.fxml");
    }
}
