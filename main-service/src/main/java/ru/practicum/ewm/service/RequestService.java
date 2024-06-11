package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto saveRequest(int userId, int eventId);

    ParticipationRequestDto cancelRequest(int userId, int requestId);

    List<ParticipationRequestDto> getRequestsToUser(int userId);

    List<ParticipationRequestDto> getRequestsToEvent(int userId, int eventId);

    EventRequestStatusUpdateResult confirmedOrRejectedRequests(int userId, int eventId,
                                                               EventRequestStatusUpdateRequest request);

}