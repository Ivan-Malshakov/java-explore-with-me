package ru.practicum.ewm.stats.service;

import ru.practicum.ewm.dto.stats.EndpointHit;
import ru.practicum.ewm.dto.stats.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void save(EndpointHit endpointHit);

    List<ViewStats> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean isUnique);
}