package de.codedbygruba.commands;

import com.google.inject.Inject;
import de.codedbygruba.mapper.DurationMapper;
import de.codedbygruba.models.Secrets;
import de.codedbygruba.services.FarmService;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.time.Duration;

public class StartFarmBuildCommand {
    @Inject
    private Secrets secrets;
    @Inject
    private FarmService farmService;
    @Inject
    private DurationMapper durationMapper;

    public void execute(SlashCommandInteractionEvent event) {
        if (event.getMember().getRoles().stream()
                .noneMatch(role -> secrets.getAllowedRoles().contains(role.getId()))) {
            event.getHook().sendMessage("❌ Du hast nicht die Berechtigung, diesen Befehl zu benutzen!").setEphemeral(true).queue();
            return;
        }

        var result = farmService.startFarmBuild();

        var sender = event.getMember().getNickname() != null ? event.getMember().getNickname() : event.getMember().getEffectiveName();
        var farmManager = event.getOption("farm-manager");
        var farmManager2 = event.getOption("farm-manager2");

        if (result != null) {
            event.reply("✅ Der Farmbau hat begonnen!").queue();
            TextChannel textChannel = event.getJDA().getTextChannelById(1231965456722694266L);

            if (textChannel != null) {
                String tpaTarget =
                        farmManager2 != null ? String.format("%s oder %s", farmManager.getAsString(), farmManager2.getAsString())
                                : farmManager != null ? farmManager.getAsString()
                                : sender;

                textChannel.sendMessage(String.format("Heyhooo %s, der Farmbau beginnt! Tpa an %s",
                        event.getGuild().getRoleById(1231947760027176990L).getAsMention(),
                        tpaTarget)).queue();
            }
        } else {
            event.reply("❌ Der Farmbau hat schon begonnen!").queue();
            return;
        }

        if (result.getDuration() == null) {
            event.getChannel()
                    .sendMessage("❌ Letzte Woche gab es keinen Farm Bau ._.")
                    .queue();
        } else {
            String formattedDuration = durationMapper.mapToString(result.getDuration());

            event.getChannel()
                    .sendMessage(String.format(
                            "✅ Hier die Stats des letzten Farm Baus:\n" +
                                    "Verbrauchte Zeit: %s\n" +
                                    "Spieler auf der Farm: %d\n" +
                                    "Spieler die mitgeholfen haben: %d",
                            formattedDuration,
                            result.getPlayers(),
                            result.getBuilders()
                    ))
                    .queue();
        }

    }
}
