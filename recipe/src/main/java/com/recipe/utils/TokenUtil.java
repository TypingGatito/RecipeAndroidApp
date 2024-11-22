package com.recipe.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TokenUtil {

    private static final String SECRET_KEY = "mySecretKey";

    public static String decodeToken(String token) {
        try {
            // Split the token into header, payload, and signature
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid token format");
            }

            // Decode the payload (second part of the token)
            String base64Payload = parts[1];
            String payloadJson = new String(Base64.getUrlDecoder().decode(base64Payload));

            // Extract the email from the payload
            int emailStart = payloadJson.indexOf(": \"") + 3;
            int emailEnd = payloadJson.indexOf("\"", emailStart);
            return payloadJson.substring(emailStart, emailEnd);

        } catch (Exception e) {
            throw new RuntimeException("Error decoding token", e);
        }
    }

    public static String createToken(String email) {
        try {
            String header = "{\"alg\": \"HS256\", \"typ\": \"JWT\"}";
            String base64Header = encodeBase64UrlSafe(header.getBytes(StandardCharsets.UTF_8));

            // Only include the email in the payload
            String payload = "{\"email\": \"" + email + "\"}";
            String base64Payload = encodeBase64UrlSafe(payload.getBytes(StandardCharsets.UTF_8));

            String signatureInput = base64Header + "." + base64Payload;
            String signature = createSignature(signatureInput);

            return base64Header + "." + base64Payload + "." + signature;

        } catch (Exception e) {
            throw new RuntimeException("Error creating token", e);
        }
    }



    private static String encodeBase64UrlSafe(byte[] data) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
    }

    private static String createSignature(String data) throws Exception {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return encodeBase64UrlSafe(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error creating signature", e);
        }
    }

}

