package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.Location;
import ru.practicum.ewm.model.State;

@Data
@NoArgsConstructor
public class EventFullDto {
    private Integer id;
    private String title;
    private String annotation;
    private String description;
    private CategoryDto category;
    private UserShortDto initiator;
    private Integer confirmedRequests;
    private String eventDate;
    private String createdOn;
    private String publishedOn;
    private Location location;
    private State state;
    private Integer participantLimit;
    private boolean requestModeration;
    private boolean paid;
    private Integer views;
}