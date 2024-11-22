package com.recipe.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenUtilTest {
    @Test
    public void testEmailToken() {
        String email = "s.com";
        assertEquals(email, TokenUtil.decodeToken(TokenUtil.createToken(email)));
    }
}