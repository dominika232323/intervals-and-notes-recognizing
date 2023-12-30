package com.example.demo;

import com.example.demo.jooq.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import static com.example.demo.jooq.tables.Users.USERS;
import static com.example.demo.jooq.tables.Notes.NOTES;
import static com.example.demo.jooq.tables.Levelnotes.LEVELNOTES;
import static com.example.demo.jooq.tables.Notesgames.NOTESGAMES;
import static com.example.demo.jooq.tables.Answersnotesgame.ANSWERSNOTESGAME;
import static com.example.demo.jooq.tables.Levelintervals.LEVELINTERVALS;
import static com.example.demo.jooq.tables.Intervalsgame.INTERVALSGAME;
import static com.example.demo.jooq.tables.Intervals.INTERVALS;
import static com.example.demo.jooq.tables.Answersintervalsgame.ANSWERSINTERVALSGAME;

public class UserTableOperations {
    static public Result<Record> getUserRecordByLogin(String login, DSLContext create) {
        return create.select()
                .from(USERS)
                .where(USERS.NAME.eq(login))
                .fetch();
    }

    static public Result<Record> getUsersRecords(DSLContext create) {
        return create.select()
                .from(USERS)
                .fetch();
    }

    static public void setUserInApplicationContext(String login, DSLContext create) {
        Result<Record> userInfo = getUserRecordByLogin(login, create);

        if (userInfo.size() == 1) {
            Record r = userInfo.get(0);

            int userID = r.get(USERS.USERID);
            String userName = r.get(USERS.NAME);
            String userHash = r.get(USERS.PASSWORDHASH);

            UsersRecord currentUser = new UsersRecord(userID, userName, userHash);
            ApplicationContext context = ApplicationContext.getInstance();
            context.setUser(currentUser);
        }
        else {
            return;
        }
    }

    static public void setUserInApplicationContext(int userID, String userName, String userHash) {
        UsersRecord currentUser = new UsersRecord(userID, userName, userHash);
        ApplicationContext context = ApplicationContext.getInstance();
        context.setUser(currentUser);
    }

    static public void insertNewUser(int userID, String userName, String userHash, DSLContext create) {
        create.insertInto(USERS)
                .columns(USERS.USERID, USERS.NAME, USERS.PASSWORDHASH)
                .values(userID, userName, userHash)
                .execute();
    }

    static public boolean checkIfLoginExists(String login, DSLContext create) {
        Result<Record> userInfo = UserTableOperations.getUserRecordByLogin(login, create);
        return checkIfLoginExists(userInfo);
    }

    static public boolean checkIfLoginExists(Result<Record> userInfo) {
        return !userInfo.isEmpty();
    }

    static public void deleteUser(int userID, DSLContext create) {
        create.transaction(configuration -> {
            DSL.using(configuration)
                    .deleteFrom(ANSWERSINTERVALSGAME)
                    .where(ANSWERSINTERVALSGAME.INTERVALSGAMEID.in(
                            DSL.select(INTERVALSGAME.INTERVALSGAMEID)
                                    .from(INTERVALSGAME)
                                    .where(INTERVALSGAME.USERID.eq(userID))
                    ))
                    .execute();

            DSL.using(configuration)
                    .deleteFrom(INTERVALSGAME)
                    .where(INTERVALSGAME.USERID.eq(userID))
                    .execute();

            DSL.using(configuration)
                    .deleteFrom(LEVELINTERVALS)
                    .where(LEVELINTERVALS.USERID.eq(userID))
                    .execute();

            DSL.using(configuration)
                    .deleteFrom(ANSWERSNOTESGAME)
                    .where(ANSWERSNOTESGAME.NOTESGAMEID.in(
                            DSL.select(NOTESGAMES.NOTESGAMEID)
                                    .from(NOTESGAMES)
                                    .where(NOTESGAMES.USERID.eq(userID))
                    ))
                    .execute();

            DSL.using(configuration)
                    .deleteFrom(NOTESGAMES)
                    .where(NOTESGAMES.USERID.eq(userID))
                    .execute();

            DSL.using(configuration)
                    .deleteFrom(LEVELNOTES)
                    .where(LEVELNOTES.USERID.eq(userID))
                    .execute();

            DSL.using(configuration)
                    .deleteFrom(USERS)
                    .where(USERS.USERID.eq(userID))
                    .execute();
        });
    }
}
