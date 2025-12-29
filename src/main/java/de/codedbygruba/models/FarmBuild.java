package de.codedbygruba.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Getter
public class FarmBuild {
    private int id;
    private int player;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Date date;
}
