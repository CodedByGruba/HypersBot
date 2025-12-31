package de.codedbygruba.commands;

import com.google.inject.Inject;
import de.codedbygruba.models.Secrets;
import de.codedbygruba.services.FarmService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class StartFarmBuildCommand {
    @Inject
    private Secrets secrets;
    @Inject
    private FarmService farmService;

    public void execute(SlashCommandInteractionEvent event) {
        if (event.getMember().getRoles().stream()
                .noneMatch(role -> secrets.getAllowedRoles().contains(role.getId()))) {
            event.getHook().sendMessage("❌ Du hast nicht die Berechtigung, diesen Befehl zu benutzen!").setEphemeral(true).queue();
            return;
        }

        var result = farmService.startFarmBuild();

        if (result.getDuration() == null) {
            event.getHook().sendMessage("❌ Letzte Woche gab es keinen Farm Bau ._.").queue();
        } else {
            event.getHook().sendMessage(String.format("✅ Hier die Stats des letzten Farm Baus: \nVerbauchte Zeit: %s \nSpieler auf der Farm: %d \nSpieler die mitgeholfen haben: %d ",
                    result.getDuration(), result.getPlayers(), result.getBuilders())).queue();
        }

        if (result != null)
            event.getHook().sendMessage("✅ Der Farmbau hat begonnen!");
        else
            event.getHook().sendMessage("❌ Der Farmbau hat schon begonnen!").queue();
    }
}
