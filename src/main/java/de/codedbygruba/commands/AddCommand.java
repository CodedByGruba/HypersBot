package de.codedbygruba.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AddCommand {
    public void execute(SlashCommandInteractionEvent event) {
        var name = event.getOption("name").getAsString();

        event.reply("✅ Spieler `" + name + "` wurde zur Farmliste hinzugefügt!").queue();
    }
}
