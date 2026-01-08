package de.codedbygruba.repositories.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.codedbygruba.models.*;
import de.codedbygruba.models.dtos.FarmEntryDto;
import de.codedbygruba.repositories.FarmRepository;
import de.codedbygruba.adapter.LocalDateTimeAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FarmRepositoryImpl implements FarmRepository {
    private final Path farmBuildsPath = Paths.get("farmBuilds.json");
    private final Path farmStatesPath = Paths.get("farmStates.json");

    private Map<Integer, FarmBuild> farmBuilds = new ConcurrentHashMap<>();
    private Gson gson;
    private FarmStates farmStates;

    public FarmRepositoryImpl() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();

        loadFarmBuilds();
        loadFarmStates();
    }

    @Override
    public int getHighestId() {
        return farmBuilds.keySet().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }

    @Override
    public void addPlayer(FarmEntryDto farmEntry) {
        farmStates.getPlayers().add(farmEntry);
        saveFarmStates();
    }

    @Override
    public FarmBuildStats startFarmBuild() {
        FarmBuild lastFarmBuild = farmBuilds.get(getHighestId());
        FarmBuildStats farmBuildStats = new FarmBuildStats(null, 0, 0);

        if (lastFarmBuild != null) {
            farmBuildStats = new FarmBuildStats(
                    Duration.between(lastFarmBuild.getStartTime(), lastFarmBuild.getEndTime()),
                    farmStates.getPlayers().size(),
                    lastFarmBuild.getPlayers().size());
        }

        farmStates.setFarmStatus(FarmStatus.BUILD);
        farmStates.setStartFarmBuildTime(LocalDateTime.now());
        farmStates.setPlayers(new ArrayList<>());
        saveFarmStates();

        return farmBuildStats;
    }

    @Override
    public void stopFarmBuild() {
        addFarmBuild(getHighestId() + 1, new FarmBuild(
                farmStates.getPlayers()
                        .stream()
                        .filter(farmEntryDto -> farmEntryDto.getHelpStatus() == HelpStatus.BUILDING)
                        .toList(),
                farmStates.getStartFarmBuildTime(),
                LocalDateTime.now())
        );

        farmStates.setFarmStatus(FarmStatus.READY);
        farmStates.setStartFarmBuildTime(null);
        saveFarmStates();
    }

    @Override
    public FarmStatus getFarmStatus() {
        return farmStates.getFarmStatus();
    }

    @Override
    public LocalDateTime getStartFarmBuildTime() {
        return farmStates.getStartFarmBuildTime();
    }

    private void addFarmBuild(int id, FarmBuild farmBuild) {
        farmBuilds.put(id, farmBuild);
        saveFarmBuilds();
    }

    private void saveFarmBuilds() {
        CompletableFuture.runAsync(() -> {
            synchronized (farmBuilds) {
                try (Writer writer = new FileWriter(farmBuildsPath.toFile())) {
                    gson.toJson(farmBuilds, writer);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        });
    }

    private void saveFarmStates() {
        CompletableFuture.runAsync(() -> {
            synchronized (farmStates) {
                try (Writer writer = new FileWriter(farmStatesPath.toFile())) {
                    gson.toJson(farmStates, writer);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        });
    }

    private void loadFarmBuilds() {
        try {
            if (Files.notExists(farmBuildsPath)) {
                farmBuilds = new ConcurrentHashMap<>();
                return;
            }
            try (Reader reader = new FileReader(farmBuildsPath.toFile())) {
                Type type = new TypeToken<ConcurrentHashMap<Integer, FarmBuild>>() {}.getType();
                ConcurrentHashMap<Integer, FarmBuild> savedFarmBuilds = gson.fromJson(reader, type);
                if (savedFarmBuilds != null) {
                    farmBuilds.clear();
                    farmBuilds = savedFarmBuilds;
                } else {
                    farmBuilds = new ConcurrentHashMap<>();
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load farmBuilds.json");
            System.err.println(e.getMessage());
        }
    }

    private void loadFarmStates() {
        try {
            if (Files.notExists(farmStatesPath)) {
                farmStates = new FarmStates(FarmStatus.READY, null, new ArrayList<>());
                return;
            }
            try (Reader reader = new FileReader(farmStatesPath.toFile())) {
                FarmStates savedFarmStates = gson.fromJson(reader, FarmStates.class);
                farmStates = Optional.ofNullable(savedFarmStates)
                        .orElseGet(() -> new FarmStates(FarmStatus.READY, null, new ArrayList<>()));

            }
        } catch (IOException e) {
            System.err.println("Failed to load farmBuildStates.json");
            System.err.println(e.getMessage());
        }
    }
}
