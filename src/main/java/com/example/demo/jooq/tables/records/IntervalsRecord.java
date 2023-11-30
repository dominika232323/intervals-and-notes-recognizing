/*
 * This file is generated by jOOQ.
 */
package com.example.demo.jooq.tables.records;


import com.example.demo.jooq.tables.Intervals;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IntervalsRecord extends UpdatableRecordImpl<IntervalsRecord> implements Record2<Integer, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>db.Intervals.intervalID</code>.
     */
    public void setIntervalid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>db.Intervals.intervalID</code>.
     */
    public Integer getIntervalid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>db.Intervals.intervalName</code>.
     */
    public void setIntervalname(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>db.Intervals.intervalName</code>.
     */
    public String getIntervalname() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Integer, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Intervals.INTERVALS.INTERVALID;
    }

    @Override
    public Field<String> field2() {
        return Intervals.INTERVALS.INTERVALNAME;
    }

    @Override
    public Integer component1() {
        return getIntervalid();
    }

    @Override
    public String component2() {
        return getIntervalname();
    }

    @Override
    public Integer value1() {
        return getIntervalid();
    }

    @Override
    public String value2() {
        return getIntervalname();
    }

    @Override
    public IntervalsRecord value1(Integer value) {
        setIntervalid(value);
        return this;
    }

    @Override
    public IntervalsRecord value2(String value) {
        setIntervalname(value);
        return this;
    }

    @Override
    public IntervalsRecord values(Integer value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached IntervalsRecord
     */
    public IntervalsRecord() {
        super(Intervals.INTERVALS);
    }

    /**
     * Create a detached, initialised IntervalsRecord
     */
    public IntervalsRecord(Integer intervalid, String intervalname) {
        super(Intervals.INTERVALS);

        setIntervalid(intervalid);
        setIntervalname(intervalname);
    }
}
