package de.codedbygruba.utils;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import javax.swing.text.html.Option;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SheetsManager {
    public static int addPlayerToSheet(OptionMapping teleportedPlayer, OptionMapping farmGuardian, OptionMapping secondAccount) {
        try {
            URL url = new URL(SecretManager.getGoogleSheetURL());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = String.format("{\"teleportedPlayer\":\"%s\", \"farmGuardian\":\"%s\", \"secondAccount\":\"%s\"}",
                    teleportedPlayer.getAsString().toLowerCase(),
                    farmGuardian.getAsString().toLowerCase(),
                    secondAccount == null ? " " : secondAccount.getAsString().toLowerCase()
            );

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
