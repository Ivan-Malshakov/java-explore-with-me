package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventShortDto {
    private Integer id;
    private String title;
    private String annotation;
    private CategoryDto category;
    private UserShortDto initiator;
    private Integer confirmedRequests;
    private String eventDate;
    private boolean paid;
    private Integer views;
}