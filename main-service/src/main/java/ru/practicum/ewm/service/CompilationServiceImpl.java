package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.client.stats.StatsClient;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.dto.stats.ViewStats;
import ru.practicum.ewm.exception.DataNotFoundException;
import ru.practicum.ewm.mapper.EventAndCompilationMapper;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.repository.JpaCompilationRepository;
import ru.practicum.ewm.repository.JpaEventsRepository;
import ru.practicum.ewm.utils.OffsetBasedPageRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final JpaEventsRepository eventsRepository;
    private final EventAndCompilationMapper mapper;
    private final JpaCompilationRepository compilationRepository;
    private final StatsClient statsClient;

    @Override
    @Transactional
    public CompilationDto saveCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = mapper.toModelCompilation(newCompilationDto);
        compilation.setEvents(eventsRepository.findAllById(compilation.getEvents().stream()
                .map(Event::getId).collect(Collectors.toList())));
        return mapper.toDtoCompilation(compilationRepository.save(compilation));
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(int compId, UpdateCompilationRequest updateCompilationRequest) {
        return mapper.toDtoCompilation(compilationRepository.save(changeCompilation(updateCompilationRequest,
                compilationRepository.findById(compId)
                        .orElseThrow(() ->
                                new DataNotFoundException("Compilation with id = " + compId + " not found")))));
    }

    @Override
    public CompilationDto getCompilation(int compId) {
        CompilationDto compilationDto = mapper.toDtoCompilation(compilationRepository.findById(compId)
                .orElseThrow(() -> new DataNotFoundException("Compilation with id = " + compId + " not found")));
        List<String> uris = getUris(compilationDto.getEvents());
        if (!uris.isEmpty()) {
            compilationDto.setEvents(getViews(uris, compilationDto.getEvents()));
        }
        return compilationDto;
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        List<CompilationDto> compilationsDto;
        if (pinned == null) {
            compilationsDto = mapper.toDtoCompilations(compilationRepository.findAll(
                    new OffsetBasedPageRequest(from, size)).toList());
        } else {
            compilationsDto = mapper.toDtoCompilations(compilationRepository.findByPinned(pinned,
                    new OffsetBasedPageRequest(from, size)));
        }
        return compilationsDto.stream()
                .peek(c -> {
                    List<String> uris = getUris(c.getEvents());
                    if (!uris.isEmpty()) {
                        c.setEvents(getViews(uris, c.getEvents()));
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeCompilation(int compId) {
        if (compilationRepository.findById(compId).isEmpty()) {
            throw new DataNotFoundException("Compilation with id = " + compId + " not found");
        }
        compilationRepository.deleteById(compId);
    }

    private Compilation changeCompilation(UpdateCompilationRequest request, Compilation compilation) {
        if (request.getEvents() != null) {
            compilation.setEvents(eventsRepository.findAllById(request.getEvents()));
        }
        if (request.getTitle() != null) {
            compilation.setTitle(request.getTitle());
        }
        if (request.getPinned() != null) {
            compilation.setPinned(request.getPinned());
        }
        return compilation;
    }

    private List<EventShortDto> getViews(List<String> uri, List<EventShortDto> events) {
        Map<String, Integer> views = statsClient.getStats(LocalDateTime.now().minusYears(1000)
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        uri, true).stream()
                .collect(Collectors.toMap(ViewStats::getUri, ViewStats::getHits));
        List<EventShortDto> results = new ArrayList<>();
        for (EventShortDto event : events) {
            event.setViews(views.getOrDefault("/events/" + event.getId(), 0));
            results.add(event);
        }
        return results;
    }

    private List<String> getUris(List<EventShortDto> events) {
        return events.stream()
                .map(i -> "/events/" + i.getId())
                .collect(Collectors.toList());
    }
}