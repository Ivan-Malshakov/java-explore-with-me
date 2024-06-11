package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdateCompilationRequest {
    private List<Integer> events;

    private Boolean pinned;

    @Length(min = 1, max = 50)
    private String title;
}