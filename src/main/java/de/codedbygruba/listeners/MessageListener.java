package de.codedbygruba.listeners;

import com.google.inject.Inject;
import de.codedbygruba.commands.AddCommand;
import de.codedbygruba.commands.AddSecondCommand;
import de.codedbygruba.commands.StartFarmBuildCommand;
import de.codedbygruba.commands.StopFarmBuildCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageListener extends ListenerAdapter {
    @Inject
    private AddCommand addCommand;
    @Inject
    private AddSecondCommand addSecondCommand;
    @Inject
    private StartFarmBuildCommand startFarmBuildCommand;
    @Inject
    private StopFarmBuildCommand stopFarmBuildCommand;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "add" -> addCommand.execute(event);
            case "addsecond" -> addSecondCommand.execute(event);
            case "startfarmbuild" -> startFarmBuildCommand.execute(event);
            case "stopfarmbuild" -> stopFarmBuildCommand.execute(event);
        }
    }

    public static List<CommandData> getCommands() {
        return List.of(
                Commands.slash("add", "Füge einen Spieler zur Farmliste hinzu")
                        .addOption(OptionType.STRING, "player", "Teleportierter Spieler", true)
                        .addOption(OptionType.STRING, "guardian", "Farm Wächter", true)
                        .addOption(OptionType.STRING, "second", "Second Account", false)
                        .addOption(OptionType.BOOLEAN, "helped", "False wenn nicht geholfen", false),
                Commands.slash("addsecond", "Füge einen Second zu einem Bereits eingetragenen Spieler hinzu")
                        .addOption(OptionType.STRING, "player", "Eingetragener Main Account", true)
                        .addOption(OptionType.STRING, "second", "Second Account", true),
                Commands.slash("startfarmbuild", "Starte den Farm Bau")
                        .addOption(OptionType.STRING, "farm-manager", "FarmManager", false)
                        .addOption(OptionType.STRING, "farm-manager2", "FarmManager2", false),
                Commands.slash("stopfarmbuild", "Stopt den Farm Bau")
        );
    }
}
