package com.example.demo;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import static com.example.demo.jooq.tables.Levelnotes.LEVELNOTES;
import static com.example.demo.jooq.tables.Levelintervals.LEVELINTERVALS;
import static com.example.demo.jooq.tables.Notesgames.NOTESGAMES;
import static com.example.demo.jooq.tables.Intervalsgame.INTERVALSGAME;


public class GameHistoryTablesOperations {
    static public Result<Record> getNotesLevelsByUserID(int id, DSLContext create) {
        return create.select()
                .from(LEVELNOTES)
                .where(LEVELNOTES.USERID.eq(id).or(LEVELNOTES.USERID.isNull()))
                .fetch();
    }

    static public Result<Record> getIntervalLevelsByUserID(int id, DSLContext create) {
        return create.select()
                .from(LEVELINTERVALS)
                .where(LEVELINTERVALS.USERID.eq(id).or(LEVELINTERVALS.USERID.isNull()))
                .fetch();
    }

    static public Result<Record> getNotesGamesByUserID(int id, DSLContext create) {
        return create.select()
                .from(NOTESGAMES)
                .where(NOTESGAMES.USERID.eq(id))
                .fetch();
    }

    static public Result<Record> getIntervalGamesByUserID(int id, DSLContext create) {
        return create.select()
                .from(INTERVALSGAME)
                .where(INTERVALSGAME.USERID.eq(id))
                .fetch();
    }

    static public Result<Record> getAllGamesByUserID(int id, DSLContext create) {
        Result<Record> allGames = getNotesGamesByUserID(id, create);
        allGames.addAll(getIntervalGamesByUserID(id, create));

        return allGames;
    }

    static public Result<Record> getNotesGamesJoinedWithLevelsByUserID(int id, DSLContext create) {
        var firstTableColumns = NOTESGAMES.fields();
        var secondTableColumns = LEVELNOTES.NAME;
//        secondTableColumns.add(LEVELNOTES.LEVELID);

        var joinCondition = NOTESGAMES.LEVELNOTESID.eq(LEVELNOTES.LEVELID);

        return create
                .select(firstTableColumns)
                .select(secondTableColumns)
                .from(NOTESGAMES)
                .join(LEVELNOTES).on(joinCondition)
                .where(LEVELNOTES.USERID.eq(id))
                .fetch();
    }

    static public Result<Record> getIntervalsGamesJoinedWithLevelsByUserID(int id, DSLContext create) {
        var firstTableColumns = INTERVALSGAME.fields();
        var secondTableColumns = LEVELINTERVALS.NAME;
//        secondTableColumns.add(LEVELINTERVALS.LEVELID);

        var joinCondition = INTERVALSGAME.INTERVALLEVELID.eq(LEVELINTERVALS.LEVELID);

        return create
                .select(firstTableColumns)
                .select(secondTableColumns)
                .from(INTERVALSGAME)
                .join(LEVELINTERVALS).on(joinCondition)
                .where(LEVELINTERVALS.USERID.eq(id))
                .fetch();
    }

    static public Result<Record> getAllGamesJoinedWithLevelsByUserID(int id, DSLContext create) {
        Result<Record> allGames = getNotesGamesJoinedWithLevelsByUserID(id, create);
        allGames.addAll(getIntervalsGamesJoinedWithLevelsByUserID(id, create));

        return allGames;
    }
}
