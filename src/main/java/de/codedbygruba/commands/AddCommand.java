package de.codedbygruba.commands;

import com.google.inject.Inject;
import de.codedbygruba.models.Secrets;
import de.codedbygruba.services.SheetsService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AddCommand {
    @Inject
    private Secrets secrets;
    @Inject
    private SheetsService sheetsService;

    public void execute(SlashCommandInteractionEvent event) {
        if (event.getMember().getRoles().stream()
                .noneMatch(role -> secrets.getAllowedRoles().contains(role.getId()))) {
            event.getHook().sendMessage("❌ Du hast nicht die Berechtigung, diesen Befehl zu benutzen!").setEphemeral(true).queue();
            return;
        }

        var teleportedPlayer = event.getOption("player");
        var farmGuardian = event.getOption("guardian");
        var secondAccount = event.getOption("second");

        int response = sheetsService.addPlayerToSheet(teleportedPlayer, farmGuardian, secondAccount);

        if (response / 200 == 1) {
            event.getHook().sendMessage(secondAccount == null ?
                    String.format("✅ Spieler `%s` wurde zur Farmliste hinzugefügt!", teleportedPlayer.getAsString()) :
                    String.format("✅ Spieler `%s` + `%s` (Second) wurde zur Farmliste hinzugefügt!", teleportedPlayer.getAsString(),  secondAccount.getAsString())
            ).queue();
        } else
            event.getHook().sendMessage(String.format("❌ Da ist was schief gelaufen! `%d´",  response)).queue();
    }
}
