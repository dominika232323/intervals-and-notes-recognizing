package com.example.demo;
import com.example.demo.jooq.Tables;
import com.example.demo.jooq.tables.Notes;
import com.example.demo.jooq.tables.records.NotesRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CreateLevelNotesController {
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
    void deleteLevelOnClick() {

    }

    @FXML
    void editLevelOnClick() {

    }

    @FXML
    void createNewLevelOnClick(ActionEvent event) throws SQLException, IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "note-level-form-view.fxml");
    }

    @FXML
    void backToMenuOnClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }

}
