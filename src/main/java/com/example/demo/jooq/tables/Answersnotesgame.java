/*
 * This file is generated by jOOQ.
 */
package com.example.demo.jooq.tables;


import com.example.demo.jooq.Db;
import com.example.demo.jooq.Indexes;
import com.example.demo.jooq.Keys;
import com.example.demo.jooq.tables.records.AnswersnotesgameRecord;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function5;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Answersnotesgame extends TableImpl<AnswersnotesgameRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>db.AnswersNotesGame</code>
     */
    public static final Answersnotesgame ANSWERSNOTESGAME = new Answersnotesgame();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AnswersnotesgameRecord> getRecordType() {
        return AnswersnotesgameRecord.class;
    }

    /**
     * The column <code>db.AnswersNotesGame.answersNotesGameID</code>.
     */
    public final TableField<AnswersnotesgameRecord, Integer> ANSWERSNOTESGAMEID = createField(DSL.name("answersNotesGameID"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>db.AnswersNotesGame.notesGameID</code>.
     */
    public final TableField<AnswersnotesgameRecord, Integer> NOTESGAMEID = createField(DSL.name("notesGameID"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>db.AnswersNotesGame.noteID</code>.
     */
    public final TableField<AnswersnotesgameRecord, Integer> NOTEID = createField(DSL.name("noteID"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>db.AnswersNotesGame.noteOccurrences</code>.
     */
    public final TableField<AnswersnotesgameRecord, Integer> NOTEOCCURRENCES = createField(DSL.name("noteOccurrences"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>db.AnswersNotesGame.noteGuessedCorrectly</code>.
     */
    public final TableField<AnswersnotesgameRecord, Integer> NOTEGUESSEDCORRECTLY = createField(DSL.name("noteGuessedCorrectly"), SQLDataType.INTEGER, this, "");

    private Answersnotesgame(Name alias, Table<AnswersnotesgameRecord> aliased) {
        this(alias, aliased, null);
    }

    private Answersnotesgame(Name alias, Table<AnswersnotesgameRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>db.AnswersNotesGame</code> table reference
     */
    public Answersnotesgame(String alias) {
        this(DSL.name(alias), ANSWERSNOTESGAME);
    }

    /**
     * Create an aliased <code>db.AnswersNotesGame</code> table reference
     */
    public Answersnotesgame(Name alias) {
        this(alias, ANSWERSNOTESGAME);
    }

    /**
     * Create a <code>db.AnswersNotesGame</code> table reference
     */
    public Answersnotesgame() {
        this(DSL.name("AnswersNotesGame"), null);
    }

    public <O extends Record> Answersnotesgame(Table<O> child, ForeignKey<O, AnswersnotesgameRecord> key) {
        super(child, key, ANSWERSNOTESGAME);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Db.DB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.ANSWERSNOTESGAME_NOTEID, Indexes.ANSWERSNOTESGAME_NOTESGAMEID);
    }

    @Override
    public UniqueKey<AnswersnotesgameRecord> getPrimaryKey() {
        return Keys.KEY_ANSWERSNOTESGAME_PRIMARY;
    }

    @Override
    public List<ForeignKey<AnswersnotesgameRecord, ?>> getReferences() {
        return Arrays.asList(Keys.ANSWERSNOTESGAME_IBFK_1, Keys.ANSWERSNOTESGAME_IBFK_2);
    }

    private transient Notesgames _notesgames;
    private transient Notes _notes;

    /**
     * Get the implicit join path to the <code>db.NotesGames</code> table.
     */
    public Notesgames notesgames() {
        if (_notesgames == null)
            _notesgames = new Notesgames(this, Keys.ANSWERSNOTESGAME_IBFK_1);

        return _notesgames;
    }

    /**
     * Get the implicit join path to the <code>db.Notes</code> table.
     */
    public Notes notes() {
        if (_notes == null)
            _notes = new Notes(this, Keys.ANSWERSNOTESGAME_IBFK_2);

        return _notes;
    }

    @Override
    public Answersnotesgame as(String alias) {
        return new Answersnotesgame(DSL.name(alias), this);
    }

    @Override
    public Answersnotesgame as(Name alias) {
        return new Answersnotesgame(alias, this);
    }

    @Override
    public Answersnotesgame as(Table<?> alias) {
        return new Answersnotesgame(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Answersnotesgame rename(String name) {
        return new Answersnotesgame(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Answersnotesgame rename(Name name) {
        return new Answersnotesgame(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Answersnotesgame rename(Table<?> name) {
        return new Answersnotesgame(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function5<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function5<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
