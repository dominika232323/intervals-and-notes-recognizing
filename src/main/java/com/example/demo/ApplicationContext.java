package com.example.demo;

import com.example.demo.jooq.tables.records.LevelintervalsRecord;
import com.example.demo.jooq.tables.records.LevelnotesRecord;
import com.example.demo.jooq.tables.records.UsersRecord;

public class ApplicationContext {
    private static ApplicationContext instance;

    private UsersRecord user;
    private LevelintervalsRecord levelInterval;
    private LevelnotesRecord levelNotes;


    public UsersRecord getUser() {
        return user;
    }

    public void setUser(UsersRecord user) {
        this.user = user;
    }

    public LevelintervalsRecord getLevelInterval() {
        return levelInterval;
    }

    public void setLevelInterval(LevelintervalsRecord levelInterval) {
        this.levelInterval = levelInterval;
    }

    public LevelnotesRecord getLevelNotes() {
        return levelNotes;
    }

    public void setLevelNotes(LevelnotesRecord levelNotes) {
        this.levelNotes = levelNotes;
    }

    private ApplicationContext(){

    }

    public static ApplicationContext getInstance(){
        if (instance == null){
            instance = new ApplicationContext();
        }
        return instance;
    }
}
