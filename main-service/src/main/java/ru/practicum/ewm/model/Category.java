package ru.practicum.ewm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;

    private String name;

    @ElementCollection
    @CollectionTable(name = "events", joinColumns = @JoinColumn(name = "category_id"))
    @Column(name = "event_id")
    private Set<Integer> events;

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.events = new HashSet<>();
    }
}