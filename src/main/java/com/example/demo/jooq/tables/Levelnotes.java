/*
 * This file is generated by jOOQ.
 */
package com.example.demo.jooq.tables;


import com.example.demo.jooq.Db;
import com.example.demo.jooq.Indexes;
import com.example.demo.jooq.Keys;
import com.example.demo.jooq.tables.records.LevelnotesRecord;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function8;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row8;
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
public class Levelnotes extends TableImpl<LevelnotesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>db.LevelNotes</code>
     */
    public static final Levelnotes LEVELNOTES = new Levelnotes();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LevelnotesRecord> getRecordType() {
        return LevelnotesRecord.class;
    }

    /**
     * The column <code>db.LevelNotes.levelID</code>.
     */
    public final TableField<LevelnotesRecord, Integer> LEVELID = createField(DSL.name("levelID"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>db.LevelNotes.userID</code>.
     */
    public final TableField<LevelnotesRecord, Integer> USERID = createField(DSL.name("userID"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>db.LevelNotes.name</code>.
     */
    public final TableField<LevelnotesRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(30), this, "");

    /**
     * The column <code>db.LevelNotes.lowerNoteBound</code>.
     */
    public final TableField<LevelnotesRecord, Integer> LOWERNOTEBOUND = createField(DSL.name("lowerNoteBound"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>db.LevelNotes.higherNoteBound</code>.
     */
    public final TableField<LevelnotesRecord, Integer> HIGHERNOTEBOUND = createField(DSL.name("higherNoteBound"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>db.LevelNotes.startingWave</code>.
     */
    public final TableField<LevelnotesRecord, Integer> STARTINGWAVE = createField(DSL.name("startingWave"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>db.LevelNotes.endingWave</code>.
     */
    public final TableField<LevelnotesRecord, Integer> ENDINGWAVE = createField(DSL.name("endingWave"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>db.LevelNotes.repetitionsNextWave</code>.
     */
    public final TableField<LevelnotesRecord, Integer> REPETITIONSNEXTWAVE = createField(DSL.name("repetitionsNextWave"), SQLDataType.INTEGER, this, "");

    private Levelnotes(Name alias, Table<LevelnotesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Levelnotes(Name alias, Table<LevelnotesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>db.LevelNotes</code> table reference
     */
    public Levelnotes(String alias) {
        this(DSL.name(alias), LEVELNOTES);
    }

    /**
     * Create an aliased <code>db.LevelNotes</code> table reference
     */
    public Levelnotes(Name alias) {
        this(alias, LEVELNOTES);
    }

    /**
     * Create a <code>db.LevelNotes</code> table reference
     */
    public Levelnotes() {
        this(DSL.name("LevelNotes"), null);
    }

    public <O extends Record> Levelnotes(Table<O> child, ForeignKey<O, LevelnotesRecord> key) {
        super(child, key, LEVELNOTES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Db.DB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.LEVELNOTES_HIGHERNOTEBOUND, Indexes.LEVELNOTES_LOWERNOTEBOUND, Indexes.LEVELNOTES_USERID);
    }

    @Override
    public UniqueKey<LevelnotesRecord> getPrimaryKey() {
        return Keys.KEY_LEVELNOTES_PRIMARY;
    }

    @Override
    public List<UniqueKey<LevelnotesRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.KEY_LEVELNOTES_NAME);
    }

    @Override
    public List<ForeignKey<LevelnotesRecord, ?>> getReferences() {
        return Arrays.asList(Keys.LEVELNOTES_IBFK_1, Keys.LEVELNOTES_IBFK_2, Keys.LEVELNOTES_IBFK_3);
    }

    private transient Users _users;
    private transient Notes _levelnotesIbfk_2;
    private transient Notes _levelnotesIbfk_3;

    /**
     * Get the implicit join path to the <code>db.Users</code> table.
     */
    public Users users() {
        if (_users == null)
            _users = new Users(this, Keys.LEVELNOTES_IBFK_1);

        return _users;
    }

    /**
     * Get the implicit join path to the <code>db.Notes</code> table, via the
     * <code>LevelNotes_ibfk_2</code> key.
     */
    public Notes levelnotesIbfk_2() {
        if (_levelnotesIbfk_2 == null)
            _levelnotesIbfk_2 = new Notes(this, Keys.LEVELNOTES_IBFK_2);

        return _levelnotesIbfk_2;
    }

    /**
     * Get the implicit join path to the <code>db.Notes</code> table, via the
     * <code>LevelNotes_ibfk_3</code> key.
     */
    public Notes levelnotesIbfk_3() {
        if (_levelnotesIbfk_3 == null)
            _levelnotesIbfk_3 = new Notes(this, Keys.LEVELNOTES_IBFK_3);

        return _levelnotesIbfk_3;
    }

    @Override
    public Levelnotes as(String alias) {
        return new Levelnotes(DSL.name(alias), this);
    }

    @Override
    public Levelnotes as(Name alias) {
        return new Levelnotes(alias, this);
    }

    @Override
    public Levelnotes as(Table<?> alias) {
        return new Levelnotes(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Levelnotes rename(String name) {
        return new Levelnotes(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Levelnotes rename(Name name) {
        return new Levelnotes(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Levelnotes rename(Table<?> name) {
        return new Levelnotes(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<Integer, Integer, String, Integer, Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function8<? super Integer, ? super Integer, ? super String, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function8<? super Integer, ? super Integer, ? super String, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
