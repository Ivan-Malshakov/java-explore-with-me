package ru.practicum.ewm.stats.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.stats.EndpointHit;
import ru.practicum.ewm.dto.stats.ViewStats;
import ru.practicum.ewm.stats.model.Stats;

import java.util.List;

@Mapper(componentModel = "spring", uses = StatsMapper.class)
public interface StatsMapper {
    @Mapping(target = "timestamp", source = "endpointHit.timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Stats toModelStats(EndpointHit endpointHit);

    @Mapping(target = "timestamp", source = "stats.timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss")
    List<ViewStats> toDtoStats(List<Stats> stats);
}