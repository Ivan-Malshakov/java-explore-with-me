package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class NewCommentDto {
    @NotBlank
    @Length(min = 1, max = 255)
    private String text;
}