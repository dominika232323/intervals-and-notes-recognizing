package com.example.demo;

import java.time.LocalDate;

public class Game {
    private String name;
    private String level;
    private String  correctness;
    private LocalDate date;

    Game(String name, String level, int correctness, LocalDate date) {
        this.name = name;
        this.level = level;
        this.correctness = correctness + "%";
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public String getCorrectness() {
        return correctness;
    }

    public LocalDate getDate() {
        return date;
    }
}
