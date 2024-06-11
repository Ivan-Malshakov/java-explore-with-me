package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.Status;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds;

    @NotNull
    private Status status;
}