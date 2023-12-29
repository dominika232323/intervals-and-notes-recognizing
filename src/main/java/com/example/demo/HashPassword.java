package com.example.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class HashPassword {
    static public String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
            messageDigest.update(password.getBytes());

            return Base64.getEncoder().encodeToString(messageDigest.digest());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    static public boolean checkPasswordCorrectness(String password, String hash) {
        String passwordHash = hashPassword(password);
        return passwordHash.equals(hash);
    }
}
