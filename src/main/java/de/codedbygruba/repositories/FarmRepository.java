package de.codedbygruba.repositories;

import de.codedbygruba.models.FarmBuildStats;
import de.codedbygruba.models.FarmStatus;
import de.codedbygruba.models.dtos.FarmEntryDto;

import java.time.LocalDateTime;

public interface FarmRepository {
    int getHighestId();
    void addPlayer(FarmEntryDto farmEntry);
    FarmBuildStats startFarmBuild();
    void stopFarmBuild();
    FarmStatus getFarmStatus();
    LocalDateTime getStartFarmBuildTime();
}
