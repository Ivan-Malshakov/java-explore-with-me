package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto saveCompilation(NewCompilationDto newCompilationDto);

    CompilationDto updateCompilation(int compId, UpdateCompilationRequest updateCompilationRequest);

    CompilationDto getCompilation(int compId);

    List<CompilationDto> getCompilations(Boolean pinned, int from, int size);

    void removeCompilation(int compId);
}