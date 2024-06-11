package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import ru.practicum.ewm.model.StateActionUser;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class UpdateEventUserRequest extends BaseUpdateEventRequest {
    private StateActionUser stateAction;
}