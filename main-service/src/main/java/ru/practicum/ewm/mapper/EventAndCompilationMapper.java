package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.Event;

import java.util.List;

@Mapper(componentModel = "spring", uses = EventAndCompilationMapper.class)
public interface EventAndCompilationMapper {

    @Mapping(target = "eventDate", source = "eventDto.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "category.id", source = "eventDto.category")
    @Mapping(target = "requestModeration", source = "eventDto.requestModeration", defaultValue = "true")
    @Mapping(target = "paid", source = "eventDto.paid", defaultValue = "false")
    @Mapping(target = "participantLimit", source = "eventDto.participantLimit", defaultValue = "0")
    @Mapping(target = "state", constant = "PENDING")
    Event toModelEvent(NewEventDto eventDto);

    @Mapping(target = "eventDate", source = "event.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "createdOn", source = "event.createdOn", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "publishedOn", source = "event.publishedOn", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "confirmedRequests", expression = "java(event.getRequests()!= null ? " +
            "event.getRequests().stream().filter(r -> r.getStatus() == ru.practicum.ewm.model.Status.CONFIRMED)" +
            ".collect(java.util.stream.Collectors.toSet()).size():0)")
    @Mapping(target = "views", expression = "java(event.getViews()!= null ? " +
            "event.getViews():0)")
    EventFullDto toFullDtoEvent(Event event);

    @Mapping(target = "eventDate", source = "event.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "confirmedRequests", expression = "java(event.getRequests()!= null ? " +
            "event.getRequests().stream().filter(r -> r.getStatus() == ru.practicum.ewm.model.Status.CONFIRMED)" +
            ".collect(java.util.stream.Collectors.toSet()).size():0)")
    @Mapping(target = "views", expression = "java(event.getViews()!= null ? " +
            "event.getViews():0)")
    EventShortDto toShortDtoEvent(Event event);

    List<EventFullDto> toDtoFullEvents(List<Event> events);

    List<EventShortDto> toDtoShortEvents(List<Event> events);

    @Mapping(target = "events", expression = "java(newCompilationDto.getEvents() != null ? " +
            "newCompilationDto.getEvents().stream().map(i -> new ru.practicum.ewm.model.Event(i))" +
            ".collect(java.util.stream.Collectors.toList()):new ArrayList<Event>())")
    @Mapping(target = "pinned", source = "newCompilationDto.pinned", defaultValue = "false")
    Compilation toModelCompilation(NewCompilationDto newCompilationDto);

    CompilationDto toDtoCompilation(Compilation compilation);

    List<CompilationDto> toDtoCompilations(List<Compilation> compilations);
}