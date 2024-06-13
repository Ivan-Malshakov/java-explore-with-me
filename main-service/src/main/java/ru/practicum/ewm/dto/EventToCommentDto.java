package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventToCommentDto {
    private Integer id;
    private String title;
}