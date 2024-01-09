package com.example.demo;

import com.example.demo.jooq.Tables;
import com.example.demo.jooq.tables.records.LevelnotesRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class NutyController {
    @FXML
    private Button backToMenuButton;
    @FXML
    private Button startGameButton;
    @FXML
    private Label nutyLabel;
    @FXML
    private ListView<LevelnotesRecord> notesLevelsListView;

    private ObservableList<LevelnotesRecord> userNotesLevels;
    private ObservableList<LevelnotesRecord> defaultNotesLevels;

    public void initialize() throws SQLException {
        userNotesLevels = FXCollections.observableArrayList();
        defaultNotesLevels = FXCollections.observableArrayList();

        // getting connection with database
        Connection connection = DatabaseConnection.getInstance().getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        // getting context
        ApplicationContext context = ApplicationContext.getInstance();

        // querying intervals levels
        userNotesLevels.addAll(create.selectFrom(Tables.LEVELNOTES).where
                (Tables.LEVELNOTES.USERID.eq(context.getUser().getUserid())).fetch());

        defaultNotesLevels.addAll(create.selectFrom(Tables.LEVELNOTES).where
                (Tables.LEVELNOTES.USERID.isNull()).fetch());

        // setting items and cell factory to intervalLevelListView
        setListViewCellFactory(notesLevelsListView);
        notesLevelsListView.setItems(defaultNotesLevels);
    }

    private void setListViewCellFactory(ListView<LevelnotesRecord> levelsListView){
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
    }

    @FXML
    private void onDefaultLevelsButtonClick() {
        notesLevelsListView.setItems(defaultNotesLevels);
        System.out.println(Integer.toString(defaultNotesLevels.size()));
    }

    @FXML
    private void onUserLevelsButtonClick() {
        notesLevelsListView.setItems(userNotesLevels);
        System.out.println(Integer.toString(userNotesLevels.size()));
    }

    @FXML
    private void onBackToMenuButtonClick() {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Menu");
            stage.setScene(new Scene(root, 700, 500));

            Stage currentStage = (Stage) backToMenuButton.getScene().getWindow();
            currentStage.close();

            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void onStartGameButtonClick(ActionEvent event) throws IOException {
        if (notesLevelsListView.getSelectionModel().isEmpty()){
            return;
        }
        ApplicationContext.getInstance().setLevelNotes(notesLevelsListView.getSelectionModel().getSelectedItem());
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "note-game-view.fxml");
    }
}
