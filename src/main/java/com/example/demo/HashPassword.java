package com.example.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashPassword {
    static public String hashPassword(String password, String salt) {
        String saltedPassword = password + salt;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(saltedPassword.getBytes());

            return bytesToString(messageDigest.digest());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

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

    static public boolean checkPasswordCorrectness(String password, String passwordSalt, String hash) {
        String passwordHash = hashPassword(password, passwordSalt);
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
