package com.example.demo;
import com.example.demo.jooq.Tables;
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
import org.jooq.impl.DSL;

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

        // getting connection with databse
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
