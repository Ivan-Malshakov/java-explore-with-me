package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.model.Location;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class NewEventDto {
    @NotBlank
    @Length(min = 3, max = 120)
    private String title;

    @NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;

    @NotBlank
    @Length(min = 20, max = 7000)
    private String description;

    @NotNull
    private Integer category;

    @NotNull
    private String eventDate;

    private boolean paid;

    @Min(0)
    private Integer participantLimit;

    private Boolean requestModeration;

    @NotNull
    private Location location;
}