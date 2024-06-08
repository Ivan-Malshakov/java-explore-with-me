package ru.practicum.ewm.stats;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.ewm.stats.model.Stats;
import ru.practicum.ewm.stats.repository.JpaStatsRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JpaStatsRepositoryTest {
    @Autowired
    private JpaStatsRepository repository;

    @Test
    void findByNotUriShouldBeOk() {
        LocalDateTime timestamp1 = LocalDateTime.now().minusDays(365);
        LocalDateTime timestamp2 = LocalDateTime.now().minusDays(4000);
        LocalDateTime timestamp3 = LocalDateTime.now().minusDays(1);
        LocalDateTime timestamp4 = LocalDateTime.now().plusDays(1);
        LocalDateTime timestamp5 = LocalDateTime.now().minusDays(2);
        Stats stats1 = new Stats(null, "ewm-main-service", "/events", "192.168.123.132.",
                timestamp1, null);
        Stats stats2 = new Stats(null, "ewm-main-service", "/notevents", "192.168.123.132.",
                timestamp2, null);
        Stats stats3 = new Stats(null, "ewm-main-service", "/events365", "192.168.123.132.",
                timestamp3, null);
        Stats stats4 = new Stats(null, "ewm-main-service", "/events993", "192.168.123.132.",
                timestamp4, null);
        Stats stats5 = new Stats(null, "ewm-main-service", "/events365", "192.168.123.132.",
                timestamp5, null);
        repository.save(stats1);
        repository.save(stats2);
        repository.save(stats3);
        repository.save(stats4);
        repository.save(stats5);
        LocalDateTime start = timestamp1.minusDays(1);
        LocalDateTime end = timestamp3;

        List<Stats> statsActual = repository.findByNotUri(start, end);

        assertEquals(2, statsActual.size());
        assertEquals("/events365", statsActual.get(0).getUri());
        assertEquals("ewm-main-service", statsActual.get(0).getApp());
        assertEquals(2, statsActual.get(0).getHits());
        assertEquals("/events", statsActual.get(1).getUri());
        assertEquals("ewm-main-service", statsActual.get(1).getApp());
        assertEquals(1, statsActual.get(1).getHits());
    }

    @Test
    void findByUriShouldBeOk() {
        LocalDateTime timestamp1 = LocalDateTime.now().minusDays(365);
        LocalDateTime timestamp2 = LocalDateTime.now().minusDays(4000);
        LocalDateTime timestamp3 = LocalDateTime.now().minusDays(1);
        LocalDateTime timestamp4 = LocalDateTime.now().plusDays(1);
        LocalDateTime timestamp5 = LocalDateTime.now().minusDays(2);
        List<String> uris = List.of("/events365", "/events993");
        Stats stats1 = new Stats(null, "ewm-main-service", "/events", "192.168.123.132.",
                timestamp1, null);
        Stats stats2 = new Stats(null, "ewm-main-service", "/notevents", "192.168.123.132.",
                timestamp2, null);
        Stats stats3 = new Stats(null, "ewm-main-service", "/events365", "192.168.123.132.",
                timestamp3, null);
        Stats stats4 = new Stats(null, "ewm-main-service", "/events993", "192.168.123.132.",
                timestamp4, null);
        Stats stats5 = new Stats(null, "ewm-main-service", "/events365", "192.168.123.132.",
                timestamp5, null);
        repository.save(stats1);
        repository.save(stats2);
        repository.save(stats3);
        repository.save(stats4);
        repository.save(stats5);
        LocalDateTime start = timestamp2;
        LocalDateTime end = timestamp4;

        List<Stats> statsActual = repository.findByUri(start, end, uris);

        assertEquals(2, statsActual.size());
        assertEquals("/events365", statsActual.get(0).getUri());
        assertEquals("ewm-main-service", statsActual.get(0).getApp());
        assertEquals(2, statsActual.get(0).getHits());
        assertEquals("/events993", statsActual.get(1).getUri());
        assertEquals("ewm-main-service", statsActual.get(1).getApp());
        assertEquals(1, statsActual.get(1).getHits());
    }

    @Test
    void findByNotUriDistinctShouldBeOkWithOneIp() {
        LocalDateTime timestamp1 = LocalDateTime.now().minusDays(365);
        LocalDateTime timestamp2 = LocalDateTime.now().minusDays(4000);
        LocalDateTime timestamp3 = LocalDateTime.now().minusDays(1);
        LocalDateTime timestamp4 = LocalDateTime.now().plusDays(1);
        LocalDateTime timestamp5 = LocalDateTime.now().minusDays(2);
        Stats stats1 = new Stats(null, "ewm-main-service", "/events", "192.168.123.132.",
                timestamp1, null);
        Stats stats2 = new Stats(null, "ewm-main-service", "/notevents", "192.168.123.132.",
                timestamp2, null);
        Stats stats3 = new Stats(null, "ewm-main-service", "/events365", "192.168.123.132.",
                timestamp3, null);
        Stats stats4 = new Stats(null, "ewm-main-service", "/events993", "192.168.123.132.",
                timestamp4, null);
        Stats stats5 = new Stats(null, "ewm-main-service", "/events365", "192.168.123.132.",
                timestamp5, null);
        repository.save(stats1);
        repository.save(stats2);
        repository.save(stats3);
        repository.save(stats4);
        repository.save(stats5);
        LocalDateTime start = timestamp1.minusDays(1);
        LocalDateTime end = timestamp3;

        List<Stats> statsActual = repository.findByNotUriDistinct(start, end);

        assertEquals(2, statsActual.size());
        assertEquals("/events", statsActual.get(0).getUri());
        assertEquals("ewm-main-service", statsActual.get(0).getApp());
        assertEquals(1, statsActual.get(0).getHits());
        assertEquals("/events365", statsActual.get(1).getUri());
        assertEquals("ewm-main-service", statsActual.get(1).getApp());
        assertEquals(1, statsActual.get(1).getHits());
    }

    @Test
    void findByNotUriDistinctShouldBeOkWithTwoIp() {
        LocalDateTime timestamp1 = LocalDateTime.now().minusDays(365);
        LocalDateTime timestamp2 = LocalDateTime.now().minusDays(4000);
        LocalDateTime timestamp3 = LocalDateTime.now().minusDays(1);
        LocalDateTime timestamp4 = LocalDateTime.now().plusDays(10);
        LocalDateTime timestamp5 = LocalDateTime.now().minusDays(2);
        Stats stats1 = new Stats(null, "ewm-main-service", "/events", "192.168.123.132.",
                timestamp1, null);
        Stats stats2 = new Stats(null, "ewm-main-service", "/notevents", "192.168.123.132.",
                timestamp2, null);
        Stats stats3 = new Stats(null, "ewm-main-service", "/events365", "192.168.123.133.",
                timestamp3, null);
        Stats stats4 = new Stats(null, "ewm-main-service", "/events365", "192.168.123.132.",
                timestamp4, null);
        Stats stats5 = new Stats(null, "ewm-main-service", "/events365", "192.168.123.132.",
                timestamp5, null);
        repository.save(stats1);
        repository.save(stats2);
        repository.save(stats3);
        repository.save(stats4);
        repository.save(stats5);
        LocalDateTime start = timestamp1.minusDays(1);
        LocalDateTime end = timestamp3;

        List<Stats> statsActual = repository.findByNotUriDistinct(start, end);

        assertEquals(2, statsActual.size());
        assertEquals("/events365", statsActual.get(0).getUri());
        assertEquals("ewm-main-service", statsActual.get(0).getApp());
        assertEquals(2, statsActual.get(0).getHits());
        assertEquals("/events", statsActual.get(1).getUri());
        assertEquals("ewm-main-service", statsActual.get(1).getApp());
        assertEquals(1, statsActual.get(1).getHits());
    }

    @Test
    void findByUriDistinctShouldBeOk() {
        LocalDateTime timestamp1 = LocalDateTime.now().minusDays(365);
        LocalDateTime timestamp2 = LocalDateTime.now().minusDays(4000);
        LocalDateTime timestamp3 = LocalDateTime.now().minusDays(1);
        LocalDateTime timestamp4 = LocalDateTime.now().plusDays(1);
        LocalDateTime timestamp5 = LocalDateTime.now().minusDays(2);
        List<String> uris = List.of("/events365", "/events993");
        Stats stats1 = new Stats(null, "ewm-main-service", "/events", "192.168.123.132.",
                timestamp1, null);
        Stats stats2 = new Stats(null, "ewm-main-service", "/events993", "192.168.123.132.",
                timestamp2, null);
        Stats stats3 = new Stats(null, "ewm-main-service", "/events365", "192.168.123.132.",
                timestamp3, null);
        Stats stats4 = new Stats(null, "ewm-main-service", "/events993", "192.168.123.132.",
                timestamp4, null);
        Stats stats5 = new Stats(null, "ewm-main-service", "/events365", "192.168.123.133.",
                timestamp5, null);
        repository.save(stats1);
        repository.save(stats2);
        repository.save(stats3);
        repository.save(stats4);
        repository.save(stats5);
        LocalDateTime start = timestamp2;
        LocalDateTime end = timestamp4;

        List<Stats> statsActual = repository.findByUriDistinct(start, end, uris);

        assertEquals(2, statsActual.size());
        assertEquals("/events365", statsActual.get(0).getUri());
        assertEquals("ewm-main-service", statsActual.get(0).getApp());
        assertEquals(2, statsActual.get(0).getHits());
        assertEquals("/events993", statsActual.get(1).getUri());
        assertEquals("ewm-main-service", statsActual.get(1).getApp());
        assertEquals(1, statsActual.get(1).getHits());
    }
}