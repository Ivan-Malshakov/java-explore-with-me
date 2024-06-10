package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.StateActionAdmin;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class UpdateEventAdminRequest extends BaseUpdateEventRequest {
    private StateActionAdmin stateAction;
}