package de.codedbygruba;


import de.codedbygruba.listeners.MessageListener;
import de.codedbygruba.utils.SecretManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;
import java.util.List;

/**
 * @author CodedByGruba
 * @since 20.03.2025
 */
public class HypersBot {
    private static HypersBot INSTANCE;

    public static HypersBot getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HypersBot();
        }
        return INSTANCE;
    }

    public List<String> allowedRoles = List.of(
            "1229182145235255421", "1229182187182751797",
            "1309887484133638266", "1231949907892633632",
            "1234966150756962385", "1355676921391747113"
    );

    public static void main(String[] args) {
        try {
            JDA jda = JDABuilder.createDefault(SecretManager.getToken(),
                    EnumSet.of(
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.MESSAGE_CONTENT,
                            GatewayIntent.GUILD_MEMBERS
                    ))
                    .setActivity(Activity.playing("Hyper Hyper"))
                    .addEventListeners(new MessageListener())
                    .build();
            jda.awaitReady();
            jda.updateCommands().addCommands(MessageListener.getCommands()).queue();
        } catch (RuntimeException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}