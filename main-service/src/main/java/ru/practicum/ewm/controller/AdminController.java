package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.model.State;
import ru.practicum.ewm.service.CategoryService;
import ru.practicum.ewm.service.CompilationService;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final CompilationService compilationService;

    @PostMapping(value = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    UserDto saveUser(@RequestBody @Valid NewUserRequest userRequest) {
        log.info("Create user {}", userRequest);
        return userService.saveUser(userRequest);
    }

    @DeleteMapping(value = "/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeUser(@PathVariable @Positive int userId) {
        log.info("Remove user with id = {}", userId);
        userService.removeUser(userId);
    }

    @GetMapping(value = "/users")
    List<UserDto> getUsers(@RequestParam(required = false) List<Integer> ids,
                           @RequestParam(defaultValue = "0") @Min(0) int from,
                           @RequestParam(defaultValue = "10") @Min(1) int size) {
        log.info("Get users by ids = {}, from = {}, size = {}", ids, from, size);
        return userService.getUsers(ids, from, size);
    }

    @PostMapping(value = "/categories")
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDto saveCategory(@RequestBody @Valid NewCategoryDto categoryDto) {
        log.info("Create new category {}", categoryDto);
        return categoryService.saveCategory(categoryDto);
    }

    @PatchMapping(value = "/categories/{catId}")
    CategoryDto updateCategory(@RequestBody @Valid NewCategoryDto categoryDto,
                               @PathVariable @Positive int catId) {
        log.info("Update category with id = {}: {}", catId, categoryDto);
        return categoryService.updateCategory(catId, categoryDto);
    }

    @DeleteMapping(value = "/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeCategory(@PathVariable @Positive int catId) {
        log.info("Remove category with id = {}", catId);
        categoryService.removeCategory(catId);
    }

    @GetMapping(value = "/events")
    List<EventFullDto> getEvents(@RequestParam(required = false) List<Integer> users,
                                 @RequestParam(required = false) List<State> states,
                                 @RequestParam(required = false) List<Integer> categories,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                 LocalDateTime rangeStart,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                 LocalDateTime rangeEnd,
                                 @RequestParam(defaultValue = "0") @Min(0) int from,
                                 @RequestParam(defaultValue = "10") @Min(1) int size) {
        log.info("Get events users = {}, states = {}, categories = {}, rangeStart = {}, rangeEnd = {}, from = {} " +
                "size = {}", users, states, categories, rangeStart, rangeEnd, from, size);
        return eventService.getFullEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping(value = "/events/{eventId}")
    EventFullDto updateEvent(@PathVariable @Positive int eventId,
                             @RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Update event with id = {}, request = {}", eventId, updateEventAdminRequest);
        return eventService.updateEventAdmin(eventId, updateEventAdminRequest);
    }

    @PostMapping(value = "/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    CompilationDto saveCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        log.info("Create compilation {}", compilationDto);
        return compilationService.saveCompilation(compilationDto);
    }

    @PatchMapping(value = "/compilations/{compId}")
    CompilationDto updateCompilation(@RequestBody @Valid UpdateCompilationRequest compilationDto,
                                     @PathVariable @Positive int compId) {
        log.info("Update compilation with id = {}, request = {}", compId, compilationDto);
        return compilationService.updateCompilation(compId, compilationDto);
    }

    @DeleteMapping(value = "/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeCompilation(@PathVariable @Positive int compId) {
        log.info("Remove compilation with id = {}", compId);
        compilationService.removeCompilation(compId);
    }

    @DeleteMapping(value = "comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeComment(@PathVariable @Positive int commentId) {
        log.info("Remove comment by a admin with commentId = {}", commentId);
        eventService.removeCommentToAdmin(commentId);
    }
}