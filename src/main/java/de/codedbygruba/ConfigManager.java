package de.codedbygruba;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ConfigManager {
    private static JsonNode config;

    static {
        try {
            InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("userSecrets.json");
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
}
