package de.codedbygruba.commands;

import de.codedbygruba.utils.SheetsManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import javax.swing.text.html.Option;
import java.util.List;

public class AddCommand {
    public void execute(SlashCommandInteractionEvent event) {
        List<String> allowedRoles = List.of(
                "1229182145235255421", "1229182187182751797",
                "1309887484133638266", "1231949907892633632",
                "1234966150756962385", "1355676921391747113"
        );

        event.deferReply().queue();

        boolean hasRole = event.getMember().getRoles().stream()
                .anyMatch(role -> allowedRoles.contains(role.getId()));

        if (!hasRole) {
            event.getHook().sendMessage("❌ Du hast nicht die Berechtigung, diesen Befehl zu benutzen!").setEphemeral(true).queue();
            return;
        }

        var teleportedPlayer = event.getOption("player");
        var farmGuardian = event.getOption("guardian");
        var secondAccount = event.getOption("second");

        int responseCode = SheetsManager.addPlayerToSheet(teleportedPlayer, farmGuardian, secondAccount);
        if (responseCode == 200) event.getHook().sendMessage(String.format("✅ Spieler `%s` wurde zur Farmliste hinzugefügt!", teleportedPlayer.getAsString())).queue();
        else event.getHook().sendMessage(String.format("❌ Da ist was schief gelaufen! `%d´",  responseCode)).queue();
    }
}
