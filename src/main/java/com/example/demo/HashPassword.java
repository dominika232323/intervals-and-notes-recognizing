package com.example.demo;

import java.security.SecureRandom;

public class HashPassword {
    static public String hashPassword(String password) {
        return "";
    }

    public static String generateSalt() {
        return bytesToString(generateSaltBytes());
    }

    private static byte[] generateSaltBytes() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    static public boolean checkPasswordCorrectness(String password, String hash) {
        String passwordHash = hashPassword(password);
        return passwordHash.equals(hash);
    }

    static private String bytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
