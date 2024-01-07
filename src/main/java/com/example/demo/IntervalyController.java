package com.example.demo;

import com.example.demo.jooq.Tables;
import com.example.demo.jooq.tables.records.LevelintervalsRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class IntervalyController {

    @FXML
    private Label interLabel;

    @FXML
    private ListView<LevelintervalsRecord> intervalLevelsListView;

    @FXML
    private Button backToMenuButton;

    private ObservableList<LevelintervalsRecord> userIntervalLevels;
    private ObservableList<LevelintervalsRecord> defaultIntervalLevels;


    public void initialize() throws SQLException{
        userIntervalLevels = FXCollections.observableArrayList();
        defaultIntervalLevels = FXCollections.observableArrayList();

        // getting connection with database
        Connection connection = DatabaseConnection.getInstance().getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        // getting context
        ApplicationContext context = ApplicationContext.getInstance();

        // querying intervals levels
        userIntervalLevels.addAll(create.selectFrom(Tables.LEVELINTERVALS).where
                (Tables.LEVELINTERVALS.USERID.eq(context.getUser().getUserid())).fetch());

        defaultIntervalLevels.addAll(create.selectFrom(Tables.LEVELINTERVALS).where
                (Tables.LEVELINTERVALS.USERID.isNull()).fetch());

        // setting items and cell factory to intervalLevelListView
        setListViewCellFactory(intervalLevelsListView);
        intervalLevelsListView.setItems(defaultIntervalLevels);

    }

    private void setListViewCellFactory(ListView<LevelintervalsRecord> levelsListView){
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
    }

    @FXML
    void onDefaultLevelsClicked(ActionEvent event) {
        intervalLevelsListView.setItems(defaultIntervalLevels);
        System.out.println(Integer.toString(defaultIntervalLevels.size()));
    }

    @FXML
    void onUserLevelsClicked(ActionEvent event) {
        intervalLevelsListView.setItems(userIntervalLevels);
        System.out.println(Integer.toString(userIntervalLevels.size()));
    }

    @FXML
    void onPlayButtonClick(ActionEvent event) throws IOException {
        if (intervalLevelsListView.getSelectionModel().isEmpty()){
            return;
        }
        ApplicationContext.getInstance().setLevelInterval(intervalLevelsListView.getSelectionModel().getSelectedItem());
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "intervals-game-view.fxml");
    }

    @FXML
    void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "hello-view.fxml");
    }
}
