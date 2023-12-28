package com.example.demo;

import java.security.SecureRandom;

public class HashPassword {
    static public String hashPassword(String password) {
        return "";
    }

    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    static public boolean checkPasswordCorrectness(String password, String hash) {
        String passwordHash = hashPassword(password);
        return passwordHash.equals(hash);
    }
}
