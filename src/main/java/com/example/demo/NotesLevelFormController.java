package com.example.demo;

import com.example.demo.jooq.Tables;
import com.example.demo.jooq.tables.records.LevelintervalsRecord;
import com.example.demo.jooq.tables.records.LevelnotesRecord;
import com.example.demo.jooq.tables.records.NotesRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import static org.jooq.impl.DSL.*;
import org.jooq.*;
import org.jooq.impl.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotesLevelFormController {

    @FXML
    private ComboBox<String> instrumentComboBox;

    @FXML
    private Button exitButton;

    @FXML
    private TextField endingWaveTextField;

    @FXML
    private TextField startingWaveTextField;

    @FXML
    private ComboBox<NotesRecord> highestNoteComboBox;

    @FXML
    private Pane staffPane;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<NotesRecord> lowestNoteComboBox;

    @FXML
    private TextField repetitionsInWave;

    @FXML
    private ImageView highestNoteClefImageView;

    @FXML
    private ImageView lowestNoteClefImageView;

    @FXML
    private TextField nameTextField;

    private final Callback<ListView<NotesRecord>, ListCell<NotesRecord>> cellFactory = new Callback<ListView<NotesRecord>, ListCell<NotesRecord>>() {
        @Override
        public ListCell<NotesRecord> call(ListView<NotesRecord> l) {
            return new ListCell<NotesRecord>() {

                @Override
                protected void updateItem(NotesRecord item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        setText(item.getNotename());
                    }
                }
            } ;
        }
    };

    private ObservableList<NotesRecord> allNotes;

    private ObservableList<String> instruments;

    private Image violinClefImage = new Image(getClass().getResourceAsStream("violin_clef_resized.png"));
    private Image bassClefImage = new Image(getClass().getResourceAsStream("bass_clef_resized.png"));

    private static final Map<String, Integer> notesVerticalPosition = new HashMap<>() {{
        int position = 20;
        // violin clef
        put("C6", position);
        put("B5", position + 20);
        put("A5", position + 30);
        put("G5", position + 40);
        put("F5", position + 50);
        put("E5", position + 60);
        put("D5", position + 70);
        put("C5", position + 80);
        put("B4", position + 90);
        put("A4", position + 100);
        put("G4", position + 110);
        put("F4", position + 120);
        put("E4", position + 130);
        put("D4", position + 140);
        put("C4", position + 150);
        // bass clef
        put("B3", position + 30);
        put("A3", position + 40);
        put("G3", position + 50);
        put("F3", position + 60);
        put("E3", position + 70);
        put("D3", position + 80);
        put("C3", position + 90);
        put("B2", position + 100);
        put("A2", position + 110);
        put("G2", position + 120);
        put("F2", position + 130);
        put("E2", position + 140);
        put("D2", position + 150);
        put("C2", position + 160);
    }};

    private Circle lowestNote;
    private Circle highestNote;

    private final int lowestNoteHorizontalPosition = 120;
    private final int highestNoteHorizontalPosition = 600;


    @FXML
    void exitOnClick(ActionEvent event) throws IOException {
        ApplicationContext context = ApplicationContext.getInstance();
        context.setLevelNotes(null);
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "create-level-notes-view.fxml");
    }

    @FXML
    void saveOnClick(ActionEvent event) throws IOException, SQLException {
        if (checkFormValid()){
            ApplicationContext context = ApplicationContext.getInstance();
            LevelnotesRecord selectedLevel = context.getLevelNotes();
            Connection connection = DatabaseConnection.getInstance().getConnection();
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

            if (selectedLevel == null){
                LevelnotesRecord newLevel = create.newRecord(Tables.LEVELNOTES);
                newLevel.setName(nameTextField.getText());
                newLevel.setStartingwave(Integer.parseInt(startingWaveTextField.getText()));
                newLevel.setEndingwave(Integer.parseInt(endingWaveTextField.getText()));
                newLevel.setUserid(ApplicationContext.getInstance().getUser().getUserid());
                newLevel.setHighernotebound(highestNoteComboBox.getSelectionModel().getSelectedItem().getNoteid());
                newLevel.setLowernotebound(lowestNoteComboBox.getSelectionModel().getSelectedItem().getNoteid());
                newLevel.setRepetitionsnextwave(Integer.parseInt(repetitionsInWave.getText()));
                newLevel.store();
            }else{
                selectedLevel.setName(nameTextField.getText());
                selectedLevel.setStartingwave(Integer.parseInt(startingWaveTextField.getText()));
                selectedLevel.setEndingwave(Integer.parseInt(endingWaveTextField.getText()));
                selectedLevel.setUserid(ApplicationContext.getInstance().getUser().getUserid());
                selectedLevel.setHighernotebound(highestNoteComboBox.getSelectionModel().getSelectedItem().getNoteid());
                selectedLevel.setLowernotebound(lowestNoteComboBox.getSelectionModel().getSelectedItem().getNoteid());
                selectedLevel.setRepetitionsnextwave(Integer.parseInt(repetitionsInWave.getText()));
                selectedLevel.store();
            }
            // We set level interval in context to null as no interval level is selected anymore
            context.setLevelNotes(null);
            // Change scene to choosing interval levels in creator
            SharedFunctionsController button = new SharedFunctionsController();
            button.changeStage(event, "create-level-notes-view.fxml");
        }
        else {
            errorLabel.setText("Nieprawidłowe dane");
        }
    }

    private boolean checkFormValid(){
        try {
            int startWave = Integer.parseInt(startingWaveTextField.getText());
            int reps = Integer.parseInt(repetitionsInWave.getText());
            int endWave = Integer.parseInt(endingWaveTextField.getText());
            String name = nameTextField.getText();
            if (startWave >= endWave){
                return false;
            } else if (startWave < 1 || endWave < 1){
                return false;
            } else if (highestNoteComboBox.getSelectionModel().getSelectedItem().getNoteid() <=
                        lowestNoteComboBox.getSelectionModel().getSelectedItem().getNoteid()) {
                return false;
            } else if (name.equals("")) {
                return false;
            }

        } catch (Exception e){
            return false;
        }
        return true;
    }

    public void initialize() throws SQLException {
        lowestNote = new Circle();
        lowestNote.setRadius(10);
        staffPane.getChildren().add(lowestNote);

        highestNote = new Circle();
        highestNote.setRadius(10);
        staffPane.getChildren().add(highestNote);

        Connection connection = DatabaseConnection.getInstance().getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        allNotes = FXCollections.observableArrayList();
        List<NotesRecord> notesRecords = create.selectFrom(Tables.NOTES).where(length(Tables.NOTES.NOTENAME).le(2)).fetch();
        Collections.reverse(notesRecords);
        allNotes.addAll(notesRecords);

        setFacotryCell(lowestNoteComboBox);
        setFacotryCell(highestNoteComboBox);

        lowestNoteComboBox.setItems(allNotes);
        highestNoteComboBox.setItems(allNotes);

        if (ApplicationContext.getInstance().getLevelNotes() == null){
            lowestNoteComboBox.getSelectionModel().selectLast();
            highestNoteComboBox.getSelectionModel().selectFirst();
        }else{
            setFormFromContext();
        }

        instruments = FXCollections.observableArrayList();
        instruments.add("Pianino");
        instrumentComboBox.setItems(instruments);
        instrumentComboBox.getSelectionModel().selectFirst();

        onLowestNoteValChanged();
        onHighestNoteValChanged();
    }

    private void setFacotryCell(ComboBox<NotesRecord> comboBox){
        comboBox.setButtonCell(cellFactory.call(null));
        comboBox.setCellFactory(cellFactory);
    }

    private void setFormFromContext() throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.MYSQL);

        LevelnotesRecord selectedLevel = ApplicationContext.getInstance().getLevelNotes();
        NotesRecord selectedLowestNote = create.selectFrom(Tables.NOTES)
                .where(Tables.NOTES.NOTEID.eq(selectedLevel.getLowernotebound())).fetchOneInto(NotesRecord.class);
        NotesRecord selectedHighestNote = create.selectFrom(Tables.NOTES)
                .where(Tables.NOTES.NOTEID.eq(selectedLevel.getHighernotebound())).fetchOneInto(NotesRecord.class);

        int selectedStartWave = selectedLevel.getStartingwave();
        int selectedEndingWave = selectedLevel.getEndingwave();
        int selectedRepetitions = selectedLevel.getRepetitionsnextwave();
        String selectedName = selectedLevel.getName();

        startingWaveTextField.setText(Integer.toString(selectedStartWave));
        endingWaveTextField.setText(Integer.toString(selectedEndingWave));
        repetitionsInWave.setText(Integer.toString(selectedRepetitions));
        nameTextField.setText(selectedName);

        highestNoteComboBox.getSelectionModel().select(findNoteIndexById(selectedHighestNote.getNoteid()));
        lowestNoteComboBox.getSelectionModel().select(findNoteIndexById(selectedLowestNote.getNoteid()));

    }

    int findNoteIndexById(int noteId){
        for (int i =0; i < allNotes.size(); i++){
            if (allNotes.get(i).getNoteid() == noteId) {
                return i;
            }
        }
        return -1;
    }

    @FXML
    void onLowestNoteValChanged() {
        NotesRecord selectedNote = lowestNoteComboBox.getSelectionModel().getSelectedItem();
        String noteName = selectedNote.getNotename();
        lowestNote.setCenterX(lowestNoteHorizontalPosition);
        lowestNote.setCenterY(notesVerticalPosition.get(noteName));

        if (selectedNote.getNoteid() > 24){
            lowestNoteClefImageView.setImage(violinClefImage);
        }else {
            lowestNoteClefImageView.setImage(bassClefImage);
        }
    }

    @FXML
    void onHighestNoteValChanged() {
        NotesRecord selectedNote = highestNoteComboBox.getSelectionModel().getSelectedItem();
        String noteName = selectedNote.getNotename();
        highestNote.setCenterX(highestNoteHorizontalPosition);
        highestNote.setCenterY(notesVerticalPosition.get(noteName));

        if (selectedNote.getNoteid() > 24){
            highestNoteClefImageView.setImage(violinClefImage);
        }else {
            highestNoteClefImageView.setImage(bassClefImage);
        }
    }


}