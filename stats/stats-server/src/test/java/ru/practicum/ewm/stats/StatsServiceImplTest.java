package ru.practicum.ewm.stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm.dto.stats.EndpointHit;
import ru.practicum.ewm.dto.stats.ViewStats;
import ru.practicum.ewm.stats.mapper.StatsMapperImpl;
import ru.practicum.ewm.stats.model.Stats;
import ru.practicum.ewm.stats.repository.JpaStatsRepository;
import ru.practicum.ewm.stats.service.StatsService;
import ru.practicum.ewm.stats.service.StatsServiceImpl;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsServiceImplTest {
    private StatsService service;
    @Mock
    private JpaStatsRepository repository;

    @BeforeEach
    void setUp() {
        service = new StatsServiceImpl(new StatsMapperImpl(), repository);
    }

    @Test
    void getShouldBeOkWithUniqueFalseAndUrisNull() {
        Stats stats1 = new Stats(null, "ewm-main-service", "/events/2", "111.111.11.11",
                LocalDateTime.now(), 1);
        Stats stats2 = new Stats(null, "ewm-main-service", "/events/1", "111.111.11.11",
                LocalDateTime.now(), 2);

        when(repository.findByNotUri(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(stats2, stats1));
        List<ViewStats> statsActual = service.get(LocalDateTime.now(), LocalDateTime.now(), null, false);
        ViewStats viewStatsExpected1 = new ViewStats("ewm-main-service", "/events/1", 2);
        ViewStats viewStatsExpected2 = new ViewStats("ewm-main-service", "/events/2", 1);

        assertEquals(viewStatsExpected1.getApp(), statsActual.get(0).getApp());
        assertEquals(viewStatsExpected1.getUri(), statsActual.get(0).getUri());
        assertEquals(viewStatsExpected1.getHits(), statsActual.get(0).getHits());
        assertEquals(viewStatsExpected2.getApp(), statsActual.get(1).getApp());
        assertEquals(viewStatsExpected2.getUri(), statsActual.get(1).getUri());
        assertEquals(viewStatsExpected2.getHits(), statsActual.get(1).getHits());
    }

    @Test
    void getShouldBeOkWithUniqueFalseAndUrisOne() {
        Stats stats1 = new Stats(null, "ewm-main-service", "/events/2", "111.111.11.11",
                LocalDateTime.now(), 1);

        when(repository.findByUri(any(LocalDateTime.class), any(LocalDateTime.class), any(List.class)))
                .thenReturn(List.of(stats1));
        List<ViewStats> statsActual = service.get(LocalDateTime.now(), LocalDateTime.now(), List.of("/events/2"), false);
        ViewStats viewStatsExpected1 = new ViewStats("ewm-main-service", "/events/2", 1);

        assertEquals(viewStatsExpected1.getApp(), statsActual.get(0).getApp());
        assertEquals(viewStatsExpected1.getUri(), statsActual.get(0).getUri());
        assertEquals(viewStatsExpected1.getHits(), statsActual.get(0).getHits());
    }

    @Test
    void getShouldBeOkWithUniqueTrueAndUrisNull() {
        Stats stats1 = new Stats(null, "ewm-main-service", "/events/2", "111.111.11.11",
                LocalDateTime.now(), 1);
        Stats stats2 = new Stats(null, "ewm-main-service", "/events/1", "111.111.11.11",
                LocalDateTime.now(), 2);

        when(repository.findByNotUriDistinct(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(stats2, stats1));
        List<ViewStats> statsActual = service.get(LocalDateTime.now(), LocalDateTime.now(), null, true);
        ViewStats viewStatsExpected1 = new ViewStats("ewm-main-service", "/events/1", 2);
        ViewStats viewStatsExpected2 = new ViewStats("ewm-main-service", "/events/2", 1);

        assertEquals(viewStatsExpected1.getApp(), statsActual.get(0).getApp());
        assertEquals(viewStatsExpected1.getUri(), statsActual.get(0).getUri());
        assertEquals(viewStatsExpected1.getHits(), statsActual.get(0).getHits());
        assertEquals(viewStatsExpected2.getApp(), statsActual.get(1).getApp());
        assertEquals(viewStatsExpected2.getUri(), statsActual.get(1).getUri());
        assertEquals(viewStatsExpected2.getHits(), statsActual.get(1).getHits());
    }

    @Test
    void getShouldBeOkWithUniqueTrueAndUrisOne() {
        Stats stats1 = new Stats(null, "ewm-main-service", "/events/2", "111.111.11.11",
                LocalDateTime.now(), 1);

        when(repository.findByUriDistinct(any(LocalDateTime.class), any(LocalDateTime.class), any(List.class)))
                .thenReturn(List.of(stats1));
        List<ViewStats> statsActual = service.get(LocalDateTime.now(), LocalDateTime.now(), List.of("/events/2"),
                true);
        ViewStats viewStatsExpected1 = new ViewStats("ewm-main-service", "/events/2", 1);

        assertEquals(viewStatsExpected1.getApp(), statsActual.get(0).getApp());
        assertEquals(viewStatsExpected1.getUri(), statsActual.get(0).getUri());
        assertEquals(viewStatsExpected1.getHits(), statsActual.get(0).getHits());
    }

    @Test
    void saveShouldBeOk() {
        EndpointHit hit = new EndpointHit("ewm-main-service", "/events/2", "111.111.11.11",
                "2022-09-06 11:00:23");
        service.save(hit);
        verify(repository, atLeast(1)).save(any(Stats.class));
    }

    @Test
    void saveShouldThrowValidateException() {
        EndpointHit hit = new EndpointHit("ewm-main-service", "/events/2", "111.111.11.11",
                "2024-09-06 11:00:23");

        ValidationException e = assertThrows(ValidationException.class, () -> service.save(hit));
        assertEquals(String.format("Invalid date: Timestamp after to {}", LocalDateTime.now()), e.getMessage());
    }
}