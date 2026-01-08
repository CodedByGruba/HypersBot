package de.codedbygruba.commands;


import com.google.inject.Inject;
import de.codedbygruba.models.Secrets;
import de.codedbygruba.services.SheetsService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AddSecondCommand {
    @Inject
    private Secrets secrets;
    @Inject
    private SheetsService sheetsService;

    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        if (event.getMember().getRoles().stream()
                .noneMatch(role -> secrets.getAllowedRoles().contains(role.getId()))) {
            event.getHook().sendMessage("❌ Du hast nicht die Berechtigung, diesen Befehl zu benutzen!").setEphemeral(true).queue();
            return;
        }

        var player = event.getOption("player");
        var secondAccount = event.getOption("second");

        int response = sheetsService.addSecondPlayerToSheet(player, secondAccount);

        if (response / 200 == 1)
            event.getHook().sendMessage(String.format("✅ Second Account `%s` wurde zu `%s` hinzugefügt!", secondAccount.getAsString(), player.getAsString())).queue();
        else if (response == 400)
            event.getHook().sendMessage("❌ Der Spieler wurde noch nicht in die Liste eingetragen!").queue();
        else
            event.getHook().sendMessage(String.format(" Da ist was schief gelaufen! `%d´",  response)).queue();
    }
}