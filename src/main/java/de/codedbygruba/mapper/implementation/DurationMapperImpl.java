package de.codedbygruba.mapper.implementation;

import de.codedbygruba.mapper.DurationMapper;

import java.time.Duration;

public class DurationMapperImpl implements DurationMapper {
    @Override
    public String mapToString(Duration duration) {
        return String.format(
                "%02dh %02dm %02ds",
                duration.toHours(),
                duration.toMinutesPart(),
                duration.toSecondsPart()
        );
    }
}
