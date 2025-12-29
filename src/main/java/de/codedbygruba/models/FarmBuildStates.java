package de.codedbygruba.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class FarmBuildStates {
    private FarmStatus farmStatus;
    private LocalDateTime startFarmBuildTime;
}
