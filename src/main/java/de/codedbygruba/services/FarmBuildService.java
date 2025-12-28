package de.codedbygruba.services;

import de.codedbygruba.models.FarmStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public interface FarmBuildService {
    LocalDateTime startFarmBuild();
    Duration stopFarmBuild();
    FarmStatus getFarmStatus();
}
