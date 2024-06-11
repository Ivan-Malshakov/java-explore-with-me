package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.model.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventFullDto saveEvent(int userId, NewEventDto newEventDto);

    EventFullDto updateEventUser(int userId, int eventId, UpdateEventUserRequest userRequest);

    EventFullDto updateEventAdmin(int eventId, UpdateEventAdminRequest adminRequest);

    EventFullDto getEventToUser(int userId, int eventId);

    EventFullDto getEvent(int eventId);

    List<EventShortDto> getShortEvents(int userId, int from, int size);

    List<EventFullDto> getFullEvents(List<Integer> users, List<State> states, List<Integer> categories,
                                     LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    List<EventShortDto> getEventsByFilter(String text, List<Integer> categories, Boolean paid,
                                          LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                          Boolean onlyAvailable, String sort, int from, int size);
}