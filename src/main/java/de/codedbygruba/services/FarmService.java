package de.codedbygruba.services;

import de.codedbygruba.models.FarmBuildStats;
import de.codedbygruba.models.FarmStates;
import de.codedbygruba.models.FarmStatus;
import de.codedbygruba.models.dtos.FarmEntryDto;

import java.time.Duration;
import java.time.LocalDateTime;

public interface FarmService {
    FarmBuildStats startFarmBuild();
    Duration stopFarmBuild();
    FarmStatus getFarmStatus();
    void addPlayer(FarmEntryDto farmEntry);
}
