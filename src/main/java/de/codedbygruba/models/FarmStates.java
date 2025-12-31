package de.codedbygruba.models;

import de.codedbygruba.models.dtos.FarmEntryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class FarmStates {
    private FarmStatus farmStatus;
    private LocalDateTime startFarmBuildTime;
    private List<FarmEntryDto> players;
}
