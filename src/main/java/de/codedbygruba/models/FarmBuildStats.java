package de.codedbygruba.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@AllArgsConstructor
@Getter
public class FarmBuildStats {
    private Duration duration;
    private int players;
    private int builders;
}
