package ru.practicum.ewm.stats;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm.dto.stats.EndpointHit;
import ru.practicum.ewm.dto.stats.ViewStats;
import ru.practicum.ewm.stats.model.Stats;
import ru.practicum.ewm.stats.repository.JpaStatsRepository;
import ru.practicum.ewm.stats.service.StatsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatsServiceImplIntegrationTest {
    @Autowired
    private StatsService statsService;
    @Autowired
    private JpaStatsRepository repository;

    @Test
    @DirtiesContext
    void saveShouldBeOkWithOneStats() {
        EndpointHit hit = new EndpointHit("ewm-main-service", "/events/2", "111.111.11.11",
                "2022-09-06 11:00:23");

        statsService.save(hit);
        List<Stats> statsAll = repository.findAll();

        assertEquals(1, statsAll.size());
        assertEquals("ewm-main-service", statsAll.get(0).getApp());
        assertEquals(1, statsAll.get(0).getId());
        assertEquals("/events/2", statsAll.get(0).getUri());
        assertEquals("111.111.11.11", statsAll.get(0).getIp());
        assertEquals(LocalDateTime.parse("2022-09-06 11:00:23",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), statsAll.get(0).getTimestamp());
    }

    @Test
    @DirtiesContext
    void getShouldBeOkWithNullUrisAndUniqueFalse() {
        EndpointHit hit1 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2022-09-06 11:00:23");
        EndpointHit hit2 = new EndpointHit("ewm-main-service", "/events/2", "222.111.11.11",
                "2022-09-10 11:00:00");
        EndpointHit hit3 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2023-09-10 11:00:00");
        EndpointHit hit4 = new EndpointHit("ewm-main-service", "/events/2", "111.111.11.11",
                "2024-02-10 11:00:00");
        EndpointHit hit5 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2023-02-10 11:00:00");
        EndpointHit hit6 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2018-01-01 11:00:00");

        statsService.save(hit1);
        statsService.save(hit2);
        statsService.save(hit3);
        statsService.save(hit4);
        statsService.save(hit5);
        statsService.save(hit6);
        List<ViewStats> statsActual = statsService.get(LocalDateTime.now().minusYears(4), LocalDateTime.now(),
                null, false);

        assertEquals(3, statsActual.size());
        assertEquals("ewm-main-service", statsActual.get(0).getApp());
        assertEquals("/events/1", statsActual.get(0).getUri());
        assertEquals(2, statsActual.get(0).getHits());
        assertEquals("ewm-main-service", statsActual.get(1).getApp());
        assertEquals("/events/2", statsActual.get(1).getUri());
        assertEquals(2, statsActual.get(1).getHits());
        assertEquals("ewm-main-service", statsActual.get(2).getApp());
        assertEquals("/events", statsActual.get(2).getUri());
        assertEquals(1, statsActual.get(2).getHits());
    }

    @Test
    @DirtiesContext
    void getWithNullUrisAndUniqueFalseShouldResponseNull() {
        EndpointHit hit1 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2022-09-06 11:00:23");
        EndpointHit hit2 = new EndpointHit("ewm-main-service", "/events/2", "222.111.11.11",
                "2022-09-10 11:00:00");
        EndpointHit hit3 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2023-09-10 11:00:00");
        EndpointHit hit4 = new EndpointHit("ewm-main-service", "/events/2", "111.111.11.11",
                "2024-02-10 11:00:00");
        EndpointHit hit5 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2023-02-10 11:00:00");
        EndpointHit hit6 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2018-01-01 11:00:00");

        statsService.save(hit1);
        statsService.save(hit2);
        statsService.save(hit3);
        statsService.save(hit4);
        statsService.save(hit5);
        statsService.save(hit6);
        List<ViewStats> statsActual = statsService.get(LocalDateTime.now().minusYears(10),
                LocalDateTime.now().minusYears(7), null, false);

        assertEquals(0, statsActual.size());
    }

    @Test
    @DirtiesContext
    void getShouldBeOkWithUrisTwoAndUniqueFalse() {
        EndpointHit hit1 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2022-09-06 11:00:23");
        EndpointHit hit2 = new EndpointHit("ewm-main-service", "/events/2", "222.111.11.11",
                "2022-09-10 11:00:00");
        EndpointHit hit3 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2023-09-10 11:00:00");
        EndpointHit hit4 = new EndpointHit("ewm-main-service", "/events/2", "111.111.11.11",
                "2024-02-10 11:00:00");
        EndpointHit hit5 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2023-02-10 11:00:00");
        EndpointHit hit6 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2018-01-01 11:00:00");

        statsService.save(hit1);
        statsService.save(hit2);
        statsService.save(hit3);
        statsService.save(hit4);
        statsService.save(hit5);
        statsService.save(hit6);
        List<ViewStats> statsActual = statsService.get(LocalDateTime.now().minusYears(4), LocalDateTime.now(),
                List.of("/events/1", "/events"), false);

        assertEquals(2, statsActual.size());
        assertEquals("ewm-main-service", statsActual.get(0).getApp());
        assertEquals("/events/1", statsActual.get(0).getUri());
        assertEquals(2, statsActual.get(0).getHits());
        assertEquals("ewm-main-service", statsActual.get(1).getApp());
        assertEquals("/events", statsActual.get(1).getUri());
        assertEquals(1, statsActual.get(1).getHits());
    }

    @Test
    @DirtiesContext
    void getWithUrisTwoAndUniqueFalseShouldBeResponseNull() {
        EndpointHit hit1 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2022-09-06 11:00:23");
        EndpointHit hit2 = new EndpointHit("ewm-main-service", "/events/2", "222.111.11.11",
                "2022-09-10 11:00:00");
        EndpointHit hit3 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2023-09-10 11:00:00");
        EndpointHit hit4 = new EndpointHit("ewm-main-service", "/events/2", "111.111.11.11",
                "2024-02-10 11:00:00");
        EndpointHit hit5 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2023-02-10 11:00:00");
        EndpointHit hit6 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2018-01-01 11:00:00");

        statsService.save(hit1);
        statsService.save(hit2);
        statsService.save(hit3);
        statsService.save(hit4);
        statsService.save(hit5);
        statsService.save(hit6);
        List<ViewStats> statsActual = statsService.get(LocalDateTime.now().minusYears(4), LocalDateTime.now(),
                List.of("/events/4"), false);

        assertEquals(0, statsActual.size());
    }

    @Test
    @DirtiesContext
    void getShouldBeOkWithUrisNullAndUniqueTrue() {
        EndpointHit hit1 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2022-09-06 11:00:23");
        EndpointHit hit2 = new EndpointHit("ewm-main-service", "/events/2", "222.111.11.11",
                "2022-09-10 11:00:00");
        EndpointHit hit3 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2023-09-10 11:00:00");
        EndpointHit hit4 = new EndpointHit("ewm-main-service", "/events/2", "111.111.11.11",
                "2024-02-10 11:00:00");
        EndpointHit hit5 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2023-02-10 11:00:00");
        EndpointHit hit6 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2018-01-01 11:00:00");

        statsService.save(hit1);
        statsService.save(hit2);
        statsService.save(hit3);
        statsService.save(hit4);
        statsService.save(hit5);
        statsService.save(hit6);
        List<ViewStats> statsActual = statsService.get(LocalDateTime.now().minusYears(4), LocalDateTime.now(),
                null, true);

        assertEquals(3, statsActual.size());
        assertEquals("ewm-main-service", statsActual.get(0).getApp());
        assertEquals("/events/2", statsActual.get(0).getUri());
        assertEquals(2, statsActual.get(0).getHits());
        assertEquals("ewm-main-service", statsActual.get(1).getApp());
        assertEquals("/events", statsActual.get(1).getUri());
        assertEquals(1, statsActual.get(1).getHits());
        assertEquals("ewm-main-service", statsActual.get(2).getApp());
        assertEquals("/events/1", statsActual.get(2).getUri());
        assertEquals(1, statsActual.get(2).getHits());
    }

    @Test
    @DirtiesContext
    void getWithUrisNullAndUniqueTrueShouldResponseNull() {
        EndpointHit hit1 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2022-09-06 11:00:23");
        EndpointHit hit2 = new EndpointHit("ewm-main-service", "/events/2", "222.111.11.11",
                "2022-09-10 11:00:00");
        EndpointHit hit3 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2023-09-10 11:00:00");
        EndpointHit hit4 = new EndpointHit("ewm-main-service", "/events/2", "111.111.11.11",
                "2024-02-10 11:00:00");
        EndpointHit hit5 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2023-02-10 11:00:00");
        EndpointHit hit6 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2018-01-01 11:00:00");

        statsService.save(hit1);
        statsService.save(hit2);
        statsService.save(hit3);
        statsService.save(hit4);
        statsService.save(hit5);
        statsService.save(hit6);
        List<ViewStats> statsActual = statsService.get(LocalDateTime.now().minusYears(10),
                LocalDateTime.now().minusYears(8), null, true);

        assertEquals(0, statsActual.size());
    }

    @Test
    @DirtiesContext
    void getShouldBeOkWithUrisTwoAndUniqueTrue() {
        EndpointHit hit1 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2022-09-06 11:00:23");
        EndpointHit hit2 = new EndpointHit("ewm-main-service", "/events/2", "222.111.11.11",
                "2022-09-10 11:00:00");
        EndpointHit hit3 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2023-09-10 11:00:00");
        EndpointHit hit4 = new EndpointHit("ewm-main-service", "/events/2", "111.111.11.11",
                "2024-02-10 11:00:00");
        EndpointHit hit5 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2023-02-10 11:00:00");
        EndpointHit hit6 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2018-01-01 11:00:00");

        statsService.save(hit1);
        statsService.save(hit2);
        statsService.save(hit3);
        statsService.save(hit4);
        statsService.save(hit5);
        statsService.save(hit6);
        List<ViewStats> statsActual = statsService.get(LocalDateTime.now().minusYears(4), LocalDateTime.now(),
                List.of("/events/2", "/events/1"), true);

        assertEquals(2, statsActual.size());
        assertEquals("ewm-main-service", statsActual.get(0).getApp());
        assertEquals("/events/2", statsActual.get(0).getUri());
        assertEquals(2, statsActual.get(0).getHits());
        assertEquals("ewm-main-service", statsActual.get(1).getApp());
        assertEquals("/events/1", statsActual.get(1).getUri());
        assertEquals(1, statsActual.get(1).getHits());
    }

    @Test
    @DirtiesContext
    void getWithUrisTwoAndUniqueTrueShouldBeResponseNull() {
        EndpointHit hit1 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2022-09-06 11:00:23");
        EndpointHit hit2 = new EndpointHit("ewm-main-service", "/events/2", "222.111.11.11",
                "2022-09-10 11:00:00");
        EndpointHit hit3 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2023-09-10 11:00:00");
        EndpointHit hit4 = new EndpointHit("ewm-main-service", "/events/2", "111.111.11.11",
                "2024-02-10 11:00:00");
        EndpointHit hit5 = new EndpointHit("ewm-main-service", "/events/1", "111.111.11.11",
                "2023-02-10 11:00:00");
        EndpointHit hit6 = new EndpointHit("ewm-main-service", "/events", "111.111.11.11",
                "2018-01-01 11:00:00");

        statsService.save(hit1);
        statsService.save(hit2);
        statsService.save(hit3);
        statsService.save(hit4);
        statsService.save(hit5);
        statsService.save(hit6);
        List<ViewStats> statsActual = statsService.get(LocalDateTime.now().minusYears(10),
                LocalDateTime.now().minusYears(8), List.of("/events/2", "/events/1"), true);

        assertEquals(0, statsActual.size());
    }
}