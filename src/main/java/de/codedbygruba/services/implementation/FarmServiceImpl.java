package de.codedbygruba.services.implementation;

import com.google.inject.Inject;
import de.codedbygruba.models.FarmBuild;
import de.codedbygruba.models.FarmBuildStats;
import de.codedbygruba.models.FarmStates;
import de.codedbygruba.models.FarmStatus;
import de.codedbygruba.models.dtos.FarmEntryDto;
import de.codedbygruba.repositories.FarmRepository;
import de.codedbygruba.services.FarmService;

import java.time.Duration;
import java.time.LocalDateTime;

public class FarmServiceImpl implements FarmService {
    @Inject
    private FarmRepository farmRepository;

    @Override
    public FarmBuildStats startFarmBuild() {
        if (farmRepository.getFarmStatus() != FarmStatus.BUILD) {
            return farmRepository.startFarmBuild();
        }
        return null;
    }

    @Override
    public Duration stopFarmBuild() {
        if (farmRepository.getFarmStatus() == FarmStatus.BUILD) {
            Duration duration = Duration.between(farmRepository.getStartFarmBuildTime(), LocalDateTime.now());
            farmRepository.stopFarmBuild();
            return duration;
        }
        return null;
    }

    @Override
    public FarmStatus getFarmStatus() {
        return farmRepository.getFarmStatus();
    }

    @Override
    public void addPlayer(FarmEntryDto farmEntry) {
        farmRepository.addPlayer(farmEntry);
    }
}
