package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.model.Location;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BaseUpdateEventRequest {
    @Length(min = 3, max = 120)
    protected String title;

    @Length(min = 20, max = 2000)
    protected String annotation;

    @Length(min = 20, max = 7000)
    protected String description;

    protected Integer category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime eventDate;

    protected Boolean paid;

    @Min(0)
    protected Integer participantLimit;

    protected Boolean requestModeration;

    protected Location location;
}