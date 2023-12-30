package com.example.demo;

import com.example.demo.jooq.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import static com.example.demo.jooq.tables.Users.USERS;

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
}
