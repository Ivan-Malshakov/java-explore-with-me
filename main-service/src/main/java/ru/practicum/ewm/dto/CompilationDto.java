package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CompilationDto {
    private Integer id;

    private List<EventShortDto> events;

    private Boolean pinned;

    private String title;
}