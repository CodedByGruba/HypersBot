package de.codedbygruba.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class SecretManager {
    private static JsonNode config;

    static {
        try {
            InputStream inputStream = SecretManager.class.getClassLoader().getResourceAsStream("userSecrets.json");
            if (inputStream == null) {
                throw new IOException("userSecrets.json not found!");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            config = objectMapper.readTree(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static String getToken() {
        return config.get("token").asText();
    }
    public static String getGoogleSheetURL() { return config.get("googleSheetURL").asText(); }
    public static String getGoogleSheetBackupURL() { return config.get("googleSheetBackupURL").asText(); }
}
