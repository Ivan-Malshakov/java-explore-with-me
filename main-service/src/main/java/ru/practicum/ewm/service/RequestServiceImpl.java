package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.ConflictRequestException;
import ru.practicum.ewm.exception.DataNotFoundException;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.model.*;
import ru.practicum.ewm.repository.JpaEventsRepository;
import ru.practicum.ewm.repository.JpaRequestRepository;
import ru.practicum.ewm.repository.JpaUsersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final JpaRequestRepository requestRepository;
    private final JpaEventsRepository eventsRepository;
    private final JpaUsersRepository usersRepository;
    private final RequestMapper mapper;

    @Override
    @Transactional
    public ParticipationRequestDto saveRequest(int userId, int eventId) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User with id =" + userId + " not found"));
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event with id =" + eventId + " not found"));
        if (event.getState() != State.PUBLISHED) {
            throw new ConflictRequestException("Event with id = " + eventId + " has not been PUBLISHED");
        }
        if (event.getInitiator().getId() == userId) {
            throw new ConflictRequestException("User with id = " + userId + " is the initiator of the event");
        }
        if (!requestRepository.findByRequesterIdAndEventId(userId, eventId).isEmpty()) {
            throw new ConflictRequestException("User with id = " + userId + " has a request to participate to event");
        }
        if (event.getParticipantLimit() != 0 && event.getRequests().stream()
                .filter(r -> r.getStatus() == Status.CONFIRMED)
                .collect(Collectors.toSet()).size() >= event.getParticipantLimit()) {
            throw new ConflictRequestException("Event with id = " + eventId
                    + " has reached the maximum number of participants");
        }
        Request request = new Request(event, user);
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(Status.CONFIRMED);
        }
        return mapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(int userId, int requestId) {
        Request request = requestRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new DataNotFoundException("Request with id = " + requestId + "for user = " + userId
                        + " not found"));
        request.setStatus(Status.CANCELED);
        return mapper.toRequestDto(request);
    }

    @Override
    public List<ParticipationRequestDto> getRequestsToUser(int userId) {
        usersRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User with id = " + userId + " not found"));
        return mapper.toRequestsDto(requestRepository.findByRequesterId(userId));
    }

    @Override
    public List<ParticipationRequestDto> getRequestsToEvent(int userId, int eventId) {
        eventsRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new DataNotFoundException("Event with id = " + eventId + "for initiator = " + userId
                        + " not found"));
        return mapper.toRequestsDto(requestRepository.findByEventId(eventId));
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult confirmedOrRejectedRequests(int userId, int eventId,
                                                                      EventRequestStatusUpdateRequest request) {
        Event event = eventsRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new DataNotFoundException("Event with id = " + eventId + "for initiator id = "
                        + userId + " not found"));
        if (event.getState() != State.PUBLISHED) {
            throw new ConflictRequestException("Event with id = " + eventId + " has not been PUBLISHED");
        }
        if (request.getStatus() != Status.CONFIRMED && request.getStatus() != Status.REJECTED) {
            throw new BadRequestException("Cannot change status to " + request.getStatus());
        }
        if (event.getRequests().stream()
                .filter(r -> r.getStatus() == Status.CONFIRMED)
                .collect(Collectors.toSet()).size() + request.getRequestIds().size() > event.getParticipantLimit() &&
                request.getStatus() == Status.CONFIRMED && event.getRequestModeration()) {
            throw new ConflictRequestException("The participant limit has been reached");
        }
        requestRepository.saveAll(requestRepository.findAllById(request.getRequestIds()).stream()
                .peek(r -> {
                    if (r.getStatus() != Status.PENDING) {
                        throw new ConflictRequestException("Request must have status PENDING");
                    }
                    r.setStatus(request.getStatus());
                })
                .collect(Collectors.toList()));
        return new EventRequestStatusUpdateResult(
                mapper.toRequestsDto(requestRepository.findByEventIdAndStatus(eventId, Status.CONFIRMED)),
                mapper.toRequestsDto(requestRepository.findByEventIdAndStatus(eventId, Status.REJECTED)));
    }
}