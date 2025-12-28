package de.codedbygruba.commands;

import com.google.inject.Inject;
import de.codedbygruba.models.Secrets;
import de.codedbygruba.services.FarmBuildService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class StartFarmBuildCommand {
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

        var result = farmBuildService.startFarmBuild();

        if (result != null)
            event.getHook().sendMessage("✅ Der Farmbau hat begonnen!");
        else
            event.getHook().sendMessage("❌ Der Farmbau hat schon begonnen!").queue();
    }
}
