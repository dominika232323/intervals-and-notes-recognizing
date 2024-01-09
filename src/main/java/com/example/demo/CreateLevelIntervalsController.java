package com.example.demo;
import com.example.demo.jooq.Tables;
import com.example.demo.jooq.tables.records.IntervalsgameRecord;
import com.example.demo.jooq.tables.records.LevelintervalsRecord;
import com.example.demo.jooq.tables.records.UsersRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.User;
import org.jooq.impl.DSL;
import static org.jooq.impl.DSL.*;
import org.jooq.*;
import org.jooq.impl.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CreateLevelIntervalsController {
    @FXML
    private Button editLevelButton;

    @FXML
    private Button createLevelButton;

    @FXML
    private ListView<LevelintervalsRecord> levelsListView;

    @FXML
    private Button deleteLevelButton;

    @FXML
    private Button backToMenuButton;

    private ObservableList<LevelintervalsRecord> intervalLevels;

    public void initialize() throws SQLException {
        intervalLevels = FXCollections.observableArrayList();

        // getting connection with database
        Connection connection = DatabaseConnection.getInstance().getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        // getting context
        ApplicationContext context = ApplicationContext.getInstance();

        UsersRecord user = context.getUser();

        // Making levelsListView show levels name instead of address in memory
        levelsListView.setCellFactory((tableColumn) -> {
            ListCell<LevelintervalsRecord> listCell = new ListCell<LevelintervalsRecord>() {
                @Override
                protected void updateItem(LevelintervalsRecord item, boolean empty) {
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
        intervalLevels.addAll(create.selectFrom(Tables.LEVELINTERVALS).where(
                                Tables.LEVELINTERVALS.USERID.eq(user.getUserid())).fetch());

        // Connecting levels with list view
        levelsListView.setItems(intervalLevels);
    }

    @FXML
    void deleteLevelOnClick(ActionEvent event) throws SQLException {
        // If any level is selected
        if (!levelsListView.getSelectionModel().isEmpty()) {
            // Getting connection with database
            Connection connection = DatabaseConnection.getInstance().getConnection();
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

            // We get selected level from database
            LevelintervalsRecord chosenLevel = levelsListView.getSelectionModel().getSelectedItem();

            // Getting all games that used selected level
            List<IntervalsgameRecord> usedGames = create.selectFrom(Tables.INTERVALSGAME)
                    .where(Tables.INTERVALSGAME.INTERVALLEVELID.eq(chosenLevel.getLevelid())).fetch();

            // Deleting all answers from all games that used selected level
            create.deleteFrom(Tables.ANSWERSINTERVALSGAME).where(Tables.ANSWERSINTERVALSGAME.INTERVALSGAMEID
                    .in(select(Tables.INTERVALSGAME.INTERVALSGAMEID)
                            .from(Tables.INTERVALSGAME)
                            .where(Tables.INTERVALSGAME.INTERVALLEVELID.eq(chosenLevel.getLevelid())))).execute();

            // Deleting all games that used selected level
            for (IntervalsgameRecord usedGame: usedGames){
                usedGame.delete();
            }

            // We delete selected level from database
            chosenLevel.delete();
            // We delete selected level from list view
            intervalLevels.remove(levelsListView.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    void editLevelOnClick(ActionEvent event) throws IOException {
        // If any level is selected
        if (!levelsListView.getSelectionModel().isEmpty()) {
            // We add selected level to context and change scene
            ApplicationContext context = ApplicationContext.getInstance();
            context.setLevelInterval(levelsListView.getSelectionModel().getSelectedItem());
            SharedFunctionsController menuButton = new SharedFunctionsController();
            menuButton.changeStage(event, "intervals-level-form-view.fxml");
        }
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
