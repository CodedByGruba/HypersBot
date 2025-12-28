package de.codedbygruba.models.dtos;

import de.codedbygruba.models.CommandType;
import de.codedbygruba.models.HelpStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FarmEntryDto {
    public FarmEntryDto(String teleportetPlayer, String farmGuardian, String secondAccount, HelpStatus helpStatus, LocalDateTime timeStamp) {
        this.teleportetPlayer = teleportetPlayer;
        this.farmGuardian = farmGuardian;
        this.secondAccount = secondAccount;
        this.helpStatus = helpStatus;
        this.timeStamp = timeStamp;
    }

    String teleportetPlayer;
    String farmGuardian;
    String secondAccount;
    CommandType commandType = CommandType.ADD;
    HelpStatus helpStatus;
    LocalDateTime timeStamp;
}
