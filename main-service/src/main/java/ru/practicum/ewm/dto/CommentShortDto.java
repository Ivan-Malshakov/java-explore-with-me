package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentShortDto {
    private Integer id;
    private String text;
    private String authorName;
    private String created;
}