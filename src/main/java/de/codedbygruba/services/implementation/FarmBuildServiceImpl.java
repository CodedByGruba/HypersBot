package de.codedbygruba.services.implementation;

import com.google.inject.Inject;
import de.codedbygruba.models.FarmStatus;
import de.codedbygruba.repositories.FarmBuildRepository;
import de.codedbygruba.services.FarmBuildService;

import java.time.Duration;
import java.time.LocalDateTime;

public class FarmBuildServiceImpl implements FarmBuildService {
    @Inject
    private FarmBuildRepository farmBuildRepository;

    @Override
    public LocalDateTime startFarmBuild() {
        if (farmBuildRepository.getFarmStatus() != FarmStatus.BUILD) {
            farmBuildRepository.startFarmBuild();
            return LocalDateTime.now();
        }
        return null;
    }

    @Override
    public Duration stopFarmBuild() {
        if (farmBuildRepository.getFarmStatus() == FarmStatus.BUILD) {
            Duration duration = Duration.between(farmBuildRepository.getStartFarmBuildTime(), LocalDateTime.now());
            farmBuildRepository.stopFarmBuild();
            return duration;
        }
        return null;
    }

    @Override
    public FarmStatus getFarmStatus() {
        return farmBuildRepository.getFarmStatus();
    }
}
