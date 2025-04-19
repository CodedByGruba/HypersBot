package de.codedbygruba.commands;

import de.codedbygruba.HypersBot;
import de.codedbygruba.utils.PermissionManager;
import de.codedbygruba.utils.SheetsManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AddCommand {
    private final PermissionManager permissionManager = PermissionManager.getInstance();
    private final SheetsManager sheetsManager = SheetsManager.getInstance();

    public void execute(SlashCommandInteractionEvent event) {
        if (event.getMember().getRoles().stream()
                .noneMatch(role -> permissionManager.allowedRoles.contains(role.getId()))) {
            event.getHook().sendMessage("❌ Du hast nicht die Berechtigung, diesen Befehl zu benutzen!").setEphemeral(true).queue();
            return;
        }

        var teleportedPlayer = event.getOption("player");
        var farmGuardian = event.getOption("guardian");
        var secondAccount = event.getOption("second");

        int responseCode = sheetsManager.addPlayerToSheet(teleportedPlayer, farmGuardian, secondAccount);
        if (responseCode == 200) event.getHook().sendMessage(String.format("✅ Spieler `%s` wurde zur Farmliste hinzugefügt!", teleportedPlayer.getAsString())).queue();
        else event.getHook().sendMessage(String.format("❌ Da ist was schief gelaufen! `%d´",  responseCode)).queue();
    }
}
