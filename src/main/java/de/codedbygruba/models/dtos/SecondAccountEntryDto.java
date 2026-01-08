package de.codedbygruba.models.dtos;

import de.codedbygruba.models.CommandType;

public class SecondAccountEntryDto {
    public SecondAccountEntryDto(String player, String secondAccount) {
        this.player = player;
        this.secondAccount = secondAccount;
    }

    String player;
    String secondAccount;
    CommandType commandType = CommandType.ADD_SECOND;
}
