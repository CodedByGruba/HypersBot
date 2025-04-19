package de.codedbygruba.commands;


import de.codedbygruba.HypersBot;
import de.codedbygruba.utils.PermissionManager;
import de.codedbygruba.utils.SheetsManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AddSecondCommand {
    private final PermissionManager permissionManager = PermissionManager.getInstance();
    private final SheetsManager sheetsManager = SheetsManager.getInstance();

    public void execute(SlashCommandInteractionEvent event) {
        if (event.getMember().getRoles().stream()
                .noneMatch(role -> permissionManager.allowedRoles.contains(role.getId()))) {
            event.getHook().sendMessage("❌ Du hast nicht die Berechtigung, diesen Befehl zu benutzen!").setEphemeral(true).queue();
            return;
        }

        var player = event.getOption("player");
        var secondAccount = event.getOption("second");

        int responseCode = sheetsManager.addSecondPlayerToSheet(player, secondAccount);
        if (responseCode == 200) event.getHook().sendMessage(String.format("✅ Second Account `%s` wurde zu `%s` hinzugefügt!", secondAccount.getAsString(), player.getAsString())).queue();
        else if (responseCode == 400) event.getHook().sendMessage("❌ Der Spieler wurde noch nicht in die Liste eingetragen!").queue();
        else event.getHook().sendMessage(String.format(" Da ist was schief gelaufen! `%d´",  responseCode)).queue();
    }
}