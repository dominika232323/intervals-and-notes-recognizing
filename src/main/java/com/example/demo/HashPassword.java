package com.example.demo;

public class HashPassword {
    static public String hashPassword(String password) {
        return "";
    }

    static public boolean checkPasswordCorrectness(String password, String hash) {
        String passwordHash = hashPassword(password);
        return passwordHash.equals(hash);
    }
}
