package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.utils.OffsetBasedPageRequest;

import java.util.List;


public interface JpaCompilationRepository extends JpaRepository<Compilation, Integer> {

    List<Compilation> findByPinned(boolean pinned, OffsetBasedPageRequest pageable);
}