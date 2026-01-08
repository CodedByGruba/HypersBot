package de.codedbygruba;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.codedbygruba.di.DiModule;
import de.codedbygruba.listeners.MessageListener;
import de.codedbygruba.models.Secrets;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;

/**
 * @author CodedByGruba
 * @since 20.03.2025
 */
public class HypersBot {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        try {
            InputStream is = HypersBot.class
                    .getClassLoader()
                    .getResourceAsStream("secrets.json");
            assert is != null;
            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            Secrets secret = gson.fromJson(reader, Secrets.class);
            Injector injector = Guice.createInjector(new DiModule(secret));

            JDA jda = JDABuilder.createDefault(injector.getInstance(Secrets.class).getToken(),
                    EnumSet.of(
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.MESSAGE_CONTENT,
                            GatewayIntent.GUILD_MEMBERS
                    ))
                    .setActivity(Activity.playing("Hyper Hyper"))
                    .addEventListeners(injector.getInstance(MessageListener.class))
                    .build();
            jda.awaitReady();
            jda.updateCommands().addCommands(MessageListener.getCommands()).queue();
        } catch (RuntimeException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}