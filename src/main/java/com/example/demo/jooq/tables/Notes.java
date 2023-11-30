/*
 * This file is generated by jOOQ.
 */
package com.example.demo.jooq.tables;


import com.example.demo.jooq.Db;
import com.example.demo.jooq.Keys;
import com.example.demo.jooq.tables.records.NotesRecord;

import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
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
public class Notes extends TableImpl<NotesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>db.Notes</code>
     */
    public static final Notes NOTES = new Notes();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<NotesRecord> getRecordType() {
        return NotesRecord.class;
    }

    /**
     * The column <code>db.Notes.noteID</code>.
     */
    public final TableField<NotesRecord, Integer> NOTEID = createField(DSL.name("noteID"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>db.Notes.noteName</code>.
     */
    public final TableField<NotesRecord, String> NOTENAME = createField(DSL.name("noteName"), SQLDataType.VARCHAR(30), this, "");

    private Notes(Name alias, Table<NotesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Notes(Name alias, Table<NotesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>db.Notes</code> table reference
     */
    public Notes(String alias) {
        this(DSL.name(alias), NOTES);
    }

    /**
     * Create an aliased <code>db.Notes</code> table reference
     */
    public Notes(Name alias) {
        this(alias, NOTES);
    }

    /**
     * Create a <code>db.Notes</code> table reference
     */
    public Notes() {
        this(DSL.name("Notes"), null);
    }

    public <O extends Record> Notes(Table<O> child, ForeignKey<O, NotesRecord> key) {
        super(child, key, NOTES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Db.DB;
    }

    @Override
    public UniqueKey<NotesRecord> getPrimaryKey() {
        return Keys.KEY_NOTES_PRIMARY;
    }

    @Override
    public Notes as(String alias) {
        return new Notes(DSL.name(alias), this);
    }

    @Override
    public Notes as(Name alias) {
        return new Notes(alias, this);
    }

    @Override
    public Notes as(Table<?> alias) {
        return new Notes(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Notes rename(String name) {
        return new Notes(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Notes rename(Name name) {
        return new Notes(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Notes rename(Table<?> name) {
        return new Notes(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function2<? super Integer, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super Integer, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}