package de.codedbygruba.models;

import de.codedbygruba.models.dtos.FarmEntryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class FarmBuild {
    private List<FarmEntryDto> players;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
