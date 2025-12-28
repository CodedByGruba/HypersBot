package de.codedbygruba.services.implementation;

import com.google.inject.Inject;
import de.codedbygruba.models.FarmStatus;
import de.codedbygruba.models.HelpStatus;
import de.codedbygruba.models.Secrets;
import de.codedbygruba.models.dtos.FarmEntryDto;
import de.codedbygruba.models.dtos.SecondAccountEntryDto;
import de.codedbygruba.services.ApiService;
import de.codedbygruba.services.FarmBuildService;
import de.codedbygruba.services.SheetsService;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

public class SheetsServiceImpl implements SheetsService {
    @Inject
    Secrets secrets;
    @Inject
    private ApiService apiService;
    @Inject
    private FarmBuildService farmBuildService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");


    @Override
    public int addPlayerToSheet(OptionMapping teleportedPlayer, OptionMapping farmGuardian, OptionMapping secondAccount) {
        var farmEntryDto = new FarmEntryDto(
                teleportedPlayer.getAsString().toLowerCase(),
                farmGuardian.getAsString(),
                secondAccount == null ? " " : secondAccount.getAsString().toLowerCase(),
                farmBuildService.getFarmStatus() == FarmStatus.BUILD ? HelpStatus.BUILDING : HelpStatus.NONE,
                LocalDateTime.now()
                );

        apiService.sendPostRequest(secrets.getGoogleSheetBackupUrl(), Void.class, farmEntryDto);
        var response = apiService.sendPostRequest(secrets.getGoogleSheetUrl(), Void.class, farmEntryDto);

        try {
            return response.get().getResponseCode();
        } catch (ExecutionException | InterruptedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return 500;
        }
    }

    @Override
    public int addSecondPlayerToSheet(OptionMapping player, OptionMapping secondAccount) {
        var secondAccountEntryDto = new SecondAccountEntryDto(
                player.getAsString().toLowerCase(),
                secondAccount.getAsString().toLowerCase()
        );

        var response = apiService.sendPostRequest(secrets.getGoogleSheetUrl(), Void.class, secondAccountEntryDto);

        try {
            return response.get().getResponseCode();
        } catch (ExecutionException | InterruptedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return 500;
        }
    }
}
