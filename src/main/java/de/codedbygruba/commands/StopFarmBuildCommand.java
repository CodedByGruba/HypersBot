package de.codedbygruba.commands;

import com.google.inject.Inject;
import de.codedbygruba.models.Secrets;
import de.codedbygruba.services.FarmBuildService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class StopFarmBuildCommand {
    @Inject
    private Secrets secrets;
    @Inject
    private FarmBuildService farmBuildService;

    public void execute(SlashCommandInteractionEvent event) {
        if (event.getMember().getRoles().stream()
                .noneMatch(role -> secrets.getAllowedRoles().contains(role.getId()))) {
            event.getHook().sendMessage("❌ Du hast nicht die Berechtigung, diesen Befehl zu benutzen!").setEphemeral(true).queue();
            return;
        }

        var result = farmBuildService.stopFarmBuild();

        if (result != null)
            event.getHook().sendMessage(String.format("✅ Der Farmbau ist abgeschlossen. Verbrauchte Zeit: %s", result)).queue();
        else
            event.getHook().sendMessage("❌ Der Farmbau hat noch nicht begonnen!").queue();
    }
}
