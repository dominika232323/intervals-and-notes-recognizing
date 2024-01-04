package com.example.demo;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import static com.example.demo.jooq.tables.Users.USERS;
import static com.example.demo.jooq.tables.Levelnotes.LEVELNOTES;
import static com.example.demo.jooq.tables.Levelintervals.LEVELINTERVALS;

public class GameHistoryTablesOperations {
    static public Result<Record> getNotesLevelsByID(int id, DSLContext create) {
        return create.select()
                .from(LEVELNOTES)
                .where(LEVELNOTES.USERID.eq(id))
                .fetch();
    }

    static public Result<Record> getIntervalLevelsByID(int id, DSLContext create) {
        return create.select()
                .from(LEVELINTERVALS)
                .where(LEVELINTERVALS.USERID.eq(id))
                .fetch();
    }
}
