package de.codedbygruba.repositories.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.codedbygruba.models.FarmBuild;
import de.codedbygruba.models.FarmBuildStates;
import de.codedbygruba.models.FarmStatus;
import de.codedbygruba.repositories.FarmBuildRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class FarmBuildRepositoryImpl implements FarmBuildRepository {
    private final Path farmBuildsPath = Paths.get("farmBuilds.json");
    private final Path farmBuildStatesPath = Paths.get("farmBuildStates.json");

    private List<FarmBuild> farmBuilds = Collections.synchronizedList(new ArrayList<>());
    private Gson gson;
    private FarmBuildStates farmBuildStates;

    public FarmBuildRepositoryImpl() {
        gson = new GsonBuilder().setPrettyPrinting().create();

        loadFarmBuilds();
        loadFarmBuildStates();
    }

    @Override
    public int getHighestId() {
        return farmBuilds.stream()
                .mapToInt(FarmBuild::getId)
                .max()
                .orElse(0);
    }

    @Override
    public void addFarmBuild(FarmBuild farmBuild) {
        farmBuilds.add(farmBuild);
        saveFarmBuilds();
    }

    @Override
    public void startFarmBuild() {
        farmBuildStates.setFarmStatus(FarmStatus.BUILD);
        farmBuildStates.setStartFarmBuildTime(LocalDateTime.now());
    }

    @Override
    public void stopFarmBuild() {
        farmBuildStates.setFarmStatus(FarmStatus.READY);
        farmBuildStates.setStartFarmBuildTime(null);
    }

    @Override
    public FarmStatus getFarmStatus() {
        return farmBuildStates.getFarmStatus();
    }

    @Override
    public LocalDateTime getStartFarmBuildTime() {
        return farmBuildStates.getStartFarmBuildTime();
    }

    private void saveFarmBuilds() {
        CompletableFuture.runAsync(() -> {
            synchronized (farmBuilds) {
                try (Writer writer = new FileWriter(farmBuildsPath.toFile())) {
                    gson.toJson(farmBuilds, writer);
                    System.out.println("Saved farmBuilds.json");
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        });
    }

    private void saveFarmBuildStates() {
        CompletableFuture.runAsync(() -> {
            synchronized (farmBuildStates) {
                try (Writer writer = new FileWriter(farmBuildStatesPath.toFile())) {
                    gson.toJson(farmBuilds, writer);
                    System.out.println("Saved farmBuildStates.json");
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        });
    }

    private void loadFarmBuilds() {
        try {
            if (Files.notExists(farmBuildsPath)) {
                Files.write(farmBuildsPath, "[]".getBytes());
            }
            try (Reader reader = new FileReader(farmBuildsPath.toFile())) {
                Type listType = new TypeToken<ArrayList<FarmBuild>>() {}.getType();
                List<FarmBuild> savedFarmBuilds = gson.fromJson(reader, listType);
                if (savedFarmBuilds != null) {
                    farmBuilds.clear();
                    farmBuilds.addAll(savedFarmBuilds);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load farmBuilds.json");
            System.err.println(e.getMessage());
        }
    }

    private void loadFarmBuildStates() {
        try {
            if (Files.notExists(farmBuildsPath)) {
                Files.write(farmBuildsPath, " ".getBytes());
            }
            try (Reader reader = new FileReader(farmBuildsPath.toFile())) {
                FarmBuildStates savedFarmBuildStates = gson.fromJson(reader, FarmBuildStates.class);
                if (savedFarmBuildStates != null) {
                    farmBuildStates = savedFarmBuildStates;
                } else {
                    farmBuildStates = new FarmBuildStates();
                    farmBuildStates.setFarmStatus(FarmStatus.READY);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load farmBuildStates.json");
            System.err.println(e.getMessage());
        }
    }
}
