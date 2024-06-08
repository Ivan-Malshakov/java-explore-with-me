package ru.practicum.ewm.stats.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "stats")
public class Stats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stats_id")
    private Integer id;

    private String app;

    private String uri;

    private String ip;

    private LocalDateTime timestamp;

    @Transient
    private Integer hits;

    public Stats(String app, String uri, Long hits) {
        this.id = null;
        this.app = app;
        this.uri = uri;
        this.ip = null;
        this.timestamp = null;
        this.hits = hits.intValue();
    }
}
