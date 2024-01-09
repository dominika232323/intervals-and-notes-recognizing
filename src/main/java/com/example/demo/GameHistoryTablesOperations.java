package com.example.demo;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.math.BigDecimal;

import static com.example.demo.jooq.tables.Users.USERS;
import static com.example.demo.jooq.tables.Notes.NOTES;
import static com.example.demo.jooq.tables.Levelnotes.LEVELNOTES;
import static com.example.demo.jooq.tables.Notesgames.NOTESGAMES;
import static com.example.demo.jooq.tables.Answersnotesgame.ANSWERSNOTESGAME;
import static com.example.demo.jooq.tables.Levelintervals.LEVELINTERVALS;
import static com.example.demo.jooq.tables.Intervalsgame.INTERVALSGAME;
import static com.example.demo.jooq.tables.Intervals.INTERVALS;
import static com.example.demo.jooq.tables.Answersintervalsgame.ANSWERSINTERVALSGAME;
import static org.jooq.impl.DSL.*;


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

    static public Result<Record> getNotesGamesJoinedWithLevelsByUserID(int id, DSLContext create) {
        var firstTableColumns = NOTESGAMES.fields();
        var secondTableColumns = LEVELNOTES.NAME;

        var joinCondition = NOTESGAMES.LEVELNOTESID.eq(LEVELNOTES.LEVELID);

        return create
                .select(val("Nuty").as("game"))
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

        var joinCondition = INTERVALSGAME.INTERVALLEVELID.eq(LEVELINTERVALS.LEVELID);

        return create
                .select(val("Interwały").as("game"))
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

    static public BigDecimal getNotesCorrectnessByGameID(int gameID, DSLContext create) {
        Result<?> result = create
                .select(sum(ANSWERSNOTESGAME.NOTEGUESSEDCORRECTLY).divide(sum(ANSWERSNOTESGAME.NOTEOCCURRENCES)).as("correctness"))
                .from(ANSWERSNOTESGAME)
                .where(ANSWERSNOTESGAME.NOTESGAMEID.eq(gameID))
                .fetch();

        BigDecimal correctness = new BigDecimal(0);

        for (var r : result) {
               correctness = r.get(DSL.field("correctness", BigDecimal.class));
               correctness = correctness.multiply(BigDecimal.valueOf(100));
        }

        return correctness;
    }
}
