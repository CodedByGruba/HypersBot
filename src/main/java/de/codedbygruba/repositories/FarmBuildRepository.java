package de.codedbygruba.repositories;

import de.codedbygruba.models.FarmBuild;
import de.codedbygruba.models.FarmStatus;

import java.time.LocalDateTime;

public interface FarmBuildRepository {
    int getHighestId();
    void addFarmBuild(FarmBuild farmBuild);
    void startFarmBuild();
    void stopFarmBuild();
    FarmStatus getFarmStatus();
    LocalDateTime getStartFarmBuildTime();
}
