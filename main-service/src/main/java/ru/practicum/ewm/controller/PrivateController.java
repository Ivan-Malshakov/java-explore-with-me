package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.model.SortComment;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PrivateController {
    private final EventService eventService;
    private final RequestService requestService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{userId}/events")
    EventFullDto saveEvent(@PathVariable @Positive int userId,
                           @RequestBody @Valid NewEventDto eventDto) {
        log.info("Create new event {}, userId = {}", eventDto, userId);
        return eventService.saveEvent(userId, eventDto);
    }

    @GetMapping(value = "/{userId}/events/{eventId}")
    EventFullDto getEvent(@PathVariable @Positive int userId, @PathVariable @Positive int eventId) {
        log.info("Get event with id = {}, userId = {}", eventId, userId);
        return eventService.getEventToUser(userId, eventId);
    }

    @GetMapping(value = "/{userId}/events")
    List<EventShortDto> getEvents(@PathVariable @Positive int userId,
                                  @RequestParam(defaultValue = "0") @Min(0) int from,
                                  @RequestParam(defaultValue = "10") @Min(1) int size) {
        log.info("Get events userId = {}, from = {}, size = {}", userId, from, size);
        return eventService.getShortEvents(userId, from, size);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}")
    EventFullDto updateEvent(@PathVariable @Positive int userId,
                             @PathVariable @Positive int eventId,
                             @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) {
        log.info("Update event with eventId = {}, userId = {}, request = {}", eventId, userId, updateEventUserRequest);
        return eventService.updateEventUser(userId, eventId, updateEventUserRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{userId}/requests")
    ParticipationRequestDto saveRequest(@PathVariable @Positive int userId,
                                        @RequestParam @Positive int eventId) {
        log.info("Create new request with eventId = {}, userId = {}", eventId, userId);
        return requestService.saveRequest(userId, eventId);
    }

    @PatchMapping(value = "/{userId}/requests/{requestId}/cancel")
    ParticipationRequestDto cancelRequest(@PathVariable @Positive int userId,
                                          @PathVariable @Positive int requestId) {
        log.info("Cancel request with requestId = {}, userId = {}", requestId, userId);
        return requestService.cancelRequest(userId, requestId);
    }

    @GetMapping(value = "/{userId}/requests")
    List<ParticipationRequestDto> getRequests(@PathVariable @Positive int userId) {
        log.info("Get requests with userId = {}", userId);
        return requestService.getRequestsToUser(userId);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}/requests")
    EventRequestStatusUpdateResult confirmedOrRejectedRequests(@PathVariable @Positive int userId,
                                                               @PathVariable @Positive int eventId,
                                                               @RequestBody @Valid EventRequestStatusUpdateRequest request) {
        log.info("Confirmed/Rejected request with userId = {}, eventId = {}, request = {}", userId, eventId, request);
        return requestService.confirmedOrRejectedRequests(userId, eventId, request);
    }

    @GetMapping(value = "/{userId}/events/{eventId}/requests")
    List<ParticipationRequestDto> getRequestsToEvent(@PathVariable @Positive int userId,
                                                     @PathVariable @Positive int eventId) {
        log.info("Get requests with userId = {}, eventId = {}", userId, eventId);
        return requestService.getRequestsToEvent(userId, eventId);
    }

    @PostMapping(value = "/{userId}/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto saveComment(@PathVariable @Positive int userId,
                                  @PathVariable @Positive int eventId,
                                  @RequestBody @Valid NewCommentDto commentDto) {
        log.info("Create comment by user with id = " + userId + " for event with id = " + eventId
                + " comment = " + commentDto);
        return eventService.addComment(commentDto, eventId, userId);
    }

    @PatchMapping(value = "/{userId}/comments/{commentId}")
    public CommentDto updateComment(@PathVariable @Positive int userId,
                                    @PathVariable @Positive int commentId,
                                    @RequestBody @Valid NewCommentDto commentDto) {
        log.info("Update comment by user with id = " + userId + " for comment with id = " + commentId
                + " comment = " + commentDto);
        return eventService.updateComment(commentDto, commentId, userId);
    }

    @DeleteMapping(value = "/{userId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeComment(@PathVariable @Positive int userId,
                              @PathVariable @Positive int commentId) {
        log.info("Remove comment with id = {} by user with id = {}", commentId, userId);
        eventService.removeCommentToUser(commentId, userId);
    }

    @GetMapping(value = "/{userId}/comments")
    public List<CommentDto> getComments(@PathVariable @Positive int userId,
                                        @RequestParam(defaultValue = "0") @Min(0) int from,
                                        @RequestParam(defaultValue = "10") @Min(1) int size,
                                        @RequestParam(defaultValue = "DESCCREATEDDATA") SortComment sortComment) {
        log.info("Get comments user with id = " + userId + "from = " + from
                + "size = " + size + "sort" + sortComment);
        return eventService.getCommentToAuthor(userId, from, size, sortComment);
    }
}