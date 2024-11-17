package com.recipe.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordHasher {

    private static final String HASH_ALGORITHM = "SHA-256";

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка: Алгоритм хэширования не найден", e);
        }
    }

    public static boolean verifyPassword(String password, String storedHash) {
        String hashOfInput = hashPassword(password);
        return hashOfInput.equals(storedHash);
    }

}
