package de.codedbygruba;


import de.codedbygruba.listeners.MessageListener;
import de.codedbygruba.utils.SecretManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;

/**
 * @author CodedByGruba
 * @since 20.03.2025
 */
public class HypersBot {
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