/*
 * This file is generated by jOOQ.
 */
package com.example.demo.jooq.tables;


import com.example.demo.jooq.Db;
import com.example.demo.jooq.Keys;
import com.example.demo.jooq.tables.records.IntervalsRecord;

import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Identity;
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
public class Intervals extends TableImpl<IntervalsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>db.Intervals</code>
     */
    public static final Intervals INTERVALS = new Intervals();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IntervalsRecord> getRecordType() {
        return IntervalsRecord.class;
    }

    /**
     * The column <code>db.Intervals.intervalID</code>.
     */
    public final TableField<IntervalsRecord, Integer> INTERVALID = createField(DSL.name("intervalID"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>db.Intervals.intervalName</code>.
     */
    public final TableField<IntervalsRecord, String> INTERVALNAME = createField(DSL.name("intervalName"), SQLDataType.VARCHAR(30), this, "");

    private Intervals(Name alias, Table<IntervalsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Intervals(Name alias, Table<IntervalsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>db.Intervals</code> table reference
     */
    public Intervals(String alias) {
        this(DSL.name(alias), INTERVALS);
    }

    /**
     * Create an aliased <code>db.Intervals</code> table reference
     */
    public Intervals(Name alias) {
        this(alias, INTERVALS);
    }

    /**
     * Create a <code>db.Intervals</code> table reference
     */
    public Intervals() {
        this(DSL.name("Intervals"), null);
    }

    public <O extends Record> Intervals(Table<O> child, ForeignKey<O, IntervalsRecord> key) {
        super(child, key, INTERVALS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Db.DB;
    }

    @Override
    public Identity<IntervalsRecord, Integer> getIdentity() {
        return (Identity<IntervalsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<IntervalsRecord> getPrimaryKey() {
        return Keys.KEY_INTERVALS_PRIMARY;
    }

    @Override
    public Intervals as(String alias) {
        return new Intervals(DSL.name(alias), this);
    }

    @Override
    public Intervals as(Name alias) {
        return new Intervals(alias, this);
    }

    @Override
    public Intervals as(Table<?> alias) {
        return new Intervals(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Intervals rename(String name) {
        return new Intervals(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Intervals rename(Name name) {
        return new Intervals(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Intervals rename(Table<?> name) {
        return new Intervals(name.getQualifiedName(), null);
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
