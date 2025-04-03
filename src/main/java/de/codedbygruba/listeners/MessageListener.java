package de.codedbygruba.listeners;

import de.codedbygruba.commands.AddCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "add":
                new AddCommand().execute(event);
                break;
        }
    }
    public static List<CommandData> getCommands() {
        return List.of(
                Commands.slash("add", "Füge einen Spieler zur Farmliste hinzu")
                        .addOption(OptionType.STRING, "player", "Teleportierter Spieler", true)
                        .addOption(OptionType.STRING, "guardian", "Farm Wächter", true)
                        .addOption(OptionType.STRING, "second", "Second Account", false)
        );
    }
}
