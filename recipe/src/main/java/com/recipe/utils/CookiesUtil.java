package com.recipe.utils;

import com.recipe.models.enums.UserRole;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Set;

public class CookiesUtil {
    public static void deleteAuthTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("authToken", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getAuthTokenEmail(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String authToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("authToken".equals(cookie.getName())) {
                    authToken = cookie.getValue();
                    break;
                }
            }
        }

        if (authToken == null) {
            return null;
        }
        return TokenUtil.decodeToken(authToken);
    }

    public static String getRole(Set<UserRole> roles) {
        for (UserRole role : roles) {
            if (role == UserRole.ADMIN) {
                return "admin";
            }
        }

        return "user";
    }
}
