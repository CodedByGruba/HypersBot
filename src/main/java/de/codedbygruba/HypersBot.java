package de.codedbygruba;


import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;


/**
 * @author CodedByGruba
 * @since 20.03.2025
 */
public class HypersBot {
    public static void main(String[] args) {
        try {
            JDABuilder.createDefault(ConfigManager.getToken())
                    .setActivity(Activity.playing("Hyper Hyper"))
                    .build();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}