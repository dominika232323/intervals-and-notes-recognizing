package com.example.demo;
import com.example.demo.jooq.Tables;
import com.example.demo.jooq.tables.Notes;
import com.example.demo.jooq.tables.records.LevelintervalsRecord;
import com.example.demo.jooq.tables.records.LevelnotesRecord;
import com.example.demo.jooq.tables.records.NotesRecord;
import com.example.demo.jooq.tables.records.UsersRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CreateLevelNotesController {
    @FXML
    private Button editLevelButton;

    @FXML
    private Button createLevelButton;

    @FXML
    private ListView<LevelnotesRecord> levelsListView;

    @FXML
    private Button deleteLevelButton;

    @FXML
    private Button backToMenuButton;

    private ObservableList<LevelnotesRecord> notesLevels;

    @FXML
    void deleteLevelOnClick() {
        // If any level is selected
        if (!levelsListView.getSelectionModel().isEmpty()) {
            // We delete selected level from database
            LevelnotesRecord chosenLevel = levelsListView.getSelectionModel().getSelectedItem();
            chosenLevel.delete();
            // We delete selected level from list view
            notesLevels.remove(levelsListView.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    void editLevelOnClick(ActionEvent event) throws IOException {
        // If any level is selected
        if (!levelsListView.getSelectionModel().isEmpty()) {
            // We add selected level to context and change scene
            ApplicationContext context = ApplicationContext.getInstance();
            context.setLevelNotes(levelsListView.getSelectionModel().getSelectedItem());
            SharedFunctionsController menuButton = new SharedFunctionsController();
            menuButton.changeStage(event, "note-level-form-view.fxml");
        }
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

    public void initialize() throws SQLException {
        notesLevels = FXCollections.observableArrayList();

        // getting connection with database
        Connection connection = DatabaseConnection.getInstance().getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        // getting context
        ApplicationContext context = ApplicationContext.getInstance();

        UsersRecord user = context.getUser();

        // Making levelsListView show levels name instead of address in memory
        levelsListView.setCellFactory((tableColumn) -> {
            ListCell<LevelnotesRecord> listCell = new ListCell<LevelnotesRecord>() {
                @Override
                protected void updateItem(LevelnotesRecord item, boolean empty) {
                    super.updateItem(item, empty);

                    this.setText(null);
                    this.setGraphic(null);

                    if(!empty){
                        this.setText(item.getName());
                    }
                }
            };
            return listCell;
        });

        // Querying all levels that were made by current user who is in context
        notesLevels.addAll(create.selectFrom(Tables.LEVELNOTES)
                .where(Tables.LEVELNOTES.USERID.eq(context.getUser().getUserid())).fetch());

        // Connecting levels with list view
        levelsListView.setItems(notesLevels);

    }

}
