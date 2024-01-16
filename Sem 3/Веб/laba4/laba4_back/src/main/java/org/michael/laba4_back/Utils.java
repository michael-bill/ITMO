package org.michael.laba4_back;

import com.google.gson.*;
import org.michael.laba4_back.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static Gson gson = new GsonBuilder()
    .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        return new JsonPrimitive(src.format(formatter));
    })
    .registerTypeAdapter(User.class, (JsonSerializer<User>) (src, typeOfSrc, context) -> {
        String login = src.getLogin();
        return new JsonPrimitive(login);
    })
    .create();

    public static String getMessage(String message) {
        return "{ \"message\": \"" + message + "\" }";
    }

    public static String getMessage(String key, String data) {
        return "{ \"" + key + "\": \"" + data + "\" }";
    }

    public static String sha256(String input) {
        try {
            return bytesToHex(MessageDigest.getInstance("SHA-256").digest(input.getBytes()));
        } catch (NoSuchAlgorithmException ignored) { }
        return "";
    }

    public static String getRandomHash() {
        try {
            byte[] randomBytes = new byte[32];
            new java.util.Random().nextBytes(randomBytes);
            return bytesToHex(MessageDigest.getInstance("SHA-256").digest(randomBytes));
        } catch (NoSuchAlgorithmException ignored) { }
        return "";
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
