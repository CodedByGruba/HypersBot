package de.codedbygruba.services.implementation;

import de.codedbygruba.models.FarmStatus;
import de.codedbygruba.services.FarmBuildService;

import java.time.Duration;
import java.time.LocalDateTime;

public class FarmBuildServiceImpl implements FarmBuildService {
    private FarmStatus farmStatus = FarmStatus.READY;

    private LocalDateTime startFarmBuildTime;

    @Override
    public LocalDateTime startFarmBuild() {
        if (farmStatus != FarmStatus.BUILD) {
            farmStatus = FarmStatus.BUILD;
            return LocalDateTime.now();
        }
        return null;
    }

    @Override
    public Duration stopFarmBuild() {
        if (farmStatus == FarmStatus.BUILD) {
            farmStatus = FarmStatus.READY;
            return Duration.between(startFarmBuildTime, LocalDateTime.now());
        }
        return null;
    }

    @Override
    public FarmStatus getFarmStatus() {
        return farmStatus;
    }
}
