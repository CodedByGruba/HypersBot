package de.codedbygruba.utils;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SheetsManager {
    private static SheetsManager INSTANCE;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static SheetsManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SheetsManager();
        }
        return INSTANCE;
    }

    public int addPlayerToSheet(OptionMapping teleportedPlayer, OptionMapping farmGuardian, OptionMapping secondAccount) {
        String jsonInputString = String.format("{\"teleportedPlayer\":\"%s\", \"farmGuardian\":\"%s\", \"secondAccount\":\"%s\", \"commandType\":\"%s\", \"timeStamp\":\"%s\"}",
                teleportedPlayer.getAsString().toLowerCase(),
                farmGuardian.getAsString().toLowerCase(),
                secondAccount == null ? " " : secondAccount.getAsString().toLowerCase(),
                "ADD",
                LocalDateTime.now().format(formatter)
        );
        System.out.println("Backup response:");
        sendPostRequest(SecretManager.getGoogleSheetBackupURL(), jsonInputString);
        return sendPostRequest(SecretManager.getGoogleSheetURL() ,jsonInputString);
    }

    public int addSecondPlayerToSheet(OptionMapping player, OptionMapping secondAccount) {
        String jsonInputString = String.format("{\"player\":\"%s\", \"secondAccount\":\"%s\", \"commandType\":\"%s\"}",
                player.getAsString().toLowerCase(),
                secondAccount.getAsString().toLowerCase(),
                "ADD_SECOND"
        );
        return sendPostRequest(SecretManager.getGoogleSheetURL() ,jsonInputString);
    }

    private int sendPostRequest(String urlString ,String jsonInputString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);


            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            return responseCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
