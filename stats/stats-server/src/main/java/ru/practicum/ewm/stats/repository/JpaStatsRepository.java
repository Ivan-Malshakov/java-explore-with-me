package ru.practicum.ewm.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.stats.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaStatsRepository extends JpaRepository<Stats, Integer> {
    @Query("select new ru.practicum.ewm.stats.model.Stats(s.app, s.uri, count(s.uri)) from Stats s " +
            "where s.timestamp >= :start and s.timestamp <= :end group by s.uri, s.app order by count(s.uri) desc")
    List<Stats> findByNotUri(@Param("start") LocalDateTime start,
                             @Param("end") LocalDateTime end);

    @Query("select new ru.practicum.ewm.stats.model.Stats(s.app, s.uri, count(s.uri)) from Stats s " +
            "where s.timestamp >= :start and s.timestamp <= :end and s.uri in :uris " +
            "group by s.uri, s.app order by count(s.uri) desc")
    List<Stats> findByUri(@Param("start") LocalDateTime start,
                          @Param("end") LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.ewm.stats.model.Stats(s.app, s.uri, count(distinct s.ip)) from Stats s " +
            "where s.timestamp >= :start and s.timestamp <= :end group by s.uri, s.app order by count(distinct s.ip) desc")
    List<Stats> findByNotUriDistinct(@Param("start") LocalDateTime start,
                                     @Param("end") LocalDateTime end);

    @Query("select distinct new ru.practicum.ewm.stats.model.Stats(s.app, s.uri, count(distinct s.ip)) from Stats s " +
            "where s.timestamp >= :start and s.timestamp <= :end and s.uri in :uris " +
            "group by s.uri, s.app order by count(distinct s.ip) desc")
    List<Stats> findByUriDistinct(@Param("start") LocalDateTime start,
                                  @Param("end") LocalDateTime end, List<String> uris);
}