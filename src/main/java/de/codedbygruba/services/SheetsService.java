package de.codedbygruba.services;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public interface SheetsService {
    int addPlayerToSheet(OptionMapping teleportedPlayer, OptionMapping farmGuardian, OptionMapping secondAccount, OptionMapping helped);
    int addSecondPlayerToSheet(OptionMapping player, OptionMapping secondAccount);
}
