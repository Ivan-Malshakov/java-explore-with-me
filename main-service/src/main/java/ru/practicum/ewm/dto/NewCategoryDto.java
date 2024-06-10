package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class NewCategoryDto {
    @NotBlank
    @Length(min = 1, max = 50)
    private String name;
}