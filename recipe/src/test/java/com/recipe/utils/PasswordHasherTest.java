package com.recipe.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHasherTest {
    @Test
    public void testHashPassword() {
        String email = "s.com";
        assertEquals(PasswordHasher.hashPassword(email), PasswordHasher.hashPassword(email));
    }
}