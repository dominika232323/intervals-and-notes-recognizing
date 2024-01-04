package com.example.demo;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import static com.example.demo.jooq.tables.Levelnotes.LEVELNOTES;
import static com.example.demo.jooq.tables.Levelintervals.LEVELINTERVALS;
import static com.example.demo.jooq.tables.Notesgames.NOTESGAMES;
import static com.example.demo.jooq.tables.Answersnotesgame.ANSWERSNOTESGAME;
import static com.example.demo.jooq.tables.Intervalsgame.INTERVALSGAME;
import static com.example.demo.jooq.tables.Answersintervalsgame.ANSWERSINTERVALSGAME;


public class GameHistoryTablesOperations {
    static public Result<Record> getNotesLevelsByUserID(int id, DSLContext create) {
        return create.select()
                .from(LEVELNOTES)
                .where(LEVELNOTES.USERID.eq(id))
                .fetch();
    }

    static public Result<Record> getIntervalLevelsByUserID(int id, DSLContext create) {
        return create.select()
                .from(LEVELINTERVALS)
                .where(LEVELINTERVALS.USERID.eq(id))
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
}
