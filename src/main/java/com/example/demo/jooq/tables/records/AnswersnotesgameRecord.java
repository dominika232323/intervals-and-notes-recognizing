/*
 * This file is generated by jOOQ.
 */
package com.example.demo.jooq.tables.records;


import com.example.demo.jooq.tables.Answersnotesgame;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AnswersnotesgameRecord extends UpdatableRecordImpl<AnswersnotesgameRecord> implements Record5<Integer, Integer, Integer, Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>db.AnswersNotesGame.answersNotesGameID</code>.
     */
    public void setAnswersnotesgameid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>db.AnswersNotesGame.answersNotesGameID</code>.
     */
    public Integer getAnswersnotesgameid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>db.AnswersNotesGame.notesGameID</code>.
     */
    public void setNotesgameid(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>db.AnswersNotesGame.notesGameID</code>.
     */
    public Integer getNotesgameid() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>db.AnswersNotesGame.noteID</code>.
     */
    public void setNoteid(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>db.AnswersNotesGame.noteID</code>.
     */
    public Integer getNoteid() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>db.AnswersNotesGame.noteOccurrences</code>.
     */
    public void setNoteoccurrences(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>db.AnswersNotesGame.noteOccurrences</code>.
     */
    public Integer getNoteoccurrences() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>db.AnswersNotesGame.noteGuessedCorrectly</code>.
     */
    public void setNoteguessedcorrectly(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>db.AnswersNotesGame.noteGuessedCorrectly</code>.
     */
    public Integer getNoteguessedcorrectly() {
        return (Integer) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Integer, Integer, Integer, Integer, Integer> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Answersnotesgame.ANSWERSNOTESGAME.ANSWERSNOTESGAMEID;
    }

    @Override
    public Field<Integer> field2() {
        return Answersnotesgame.ANSWERSNOTESGAME.NOTESGAMEID;
    }

    @Override
    public Field<Integer> field3() {
        return Answersnotesgame.ANSWERSNOTESGAME.NOTEID;
    }

    @Override
    public Field<Integer> field4() {
        return Answersnotesgame.ANSWERSNOTESGAME.NOTEOCCURRENCES;
    }

    @Override
    public Field<Integer> field5() {
        return Answersnotesgame.ANSWERSNOTESGAME.NOTEGUESSEDCORRECTLY;
    }

    @Override
    public Integer component1() {
        return getAnswersnotesgameid();
    }

    @Override
    public Integer component2() {
        return getNotesgameid();
    }

    @Override
    public Integer component3() {
        return getNoteid();
    }

    @Override
    public Integer component4() {
        return getNoteoccurrences();
    }

    @Override
    public Integer component5() {
        return getNoteguessedcorrectly();
    }

    @Override
    public Integer value1() {
        return getAnswersnotesgameid();
    }

    @Override
    public Integer value2() {
        return getNotesgameid();
    }

    @Override
    public Integer value3() {
        return getNoteid();
    }

    @Override
    public Integer value4() {
        return getNoteoccurrences();
    }

    @Override
    public Integer value5() {
        return getNoteguessedcorrectly();
    }

    @Override
    public AnswersnotesgameRecord value1(Integer value) {
        setAnswersnotesgameid(value);
        return this;
    }

    @Override
    public AnswersnotesgameRecord value2(Integer value) {
        setNotesgameid(value);
        return this;
    }

    @Override
    public AnswersnotesgameRecord value3(Integer value) {
        setNoteid(value);
        return this;
    }

    @Override
    public AnswersnotesgameRecord value4(Integer value) {
        setNoteoccurrences(value);
        return this;
    }

    @Override
    public AnswersnotesgameRecord value5(Integer value) {
        setNoteguessedcorrectly(value);
        return this;
    }

    @Override
    public AnswersnotesgameRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AnswersnotesgameRecord
     */
    public AnswersnotesgameRecord() {
        super(Answersnotesgame.ANSWERSNOTESGAME);
    }

    /**
     * Create a detached, initialised AnswersnotesgameRecord
     */
    public AnswersnotesgameRecord(Integer answersnotesgameid, Integer notesgameid, Integer noteid, Integer noteoccurrences, Integer noteguessedcorrectly) {
        super(Answersnotesgame.ANSWERSNOTESGAME);

        setAnswersnotesgameid(answersnotesgameid);
        setNotesgameid(notesgameid);
        setNoteid(noteid);
        setNoteoccurrences(noteoccurrences);
        setNoteguessedcorrectly(noteguessedcorrectly);
    }
}