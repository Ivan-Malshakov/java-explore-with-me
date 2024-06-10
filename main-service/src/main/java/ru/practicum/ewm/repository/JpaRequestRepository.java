package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.Status;

import java.util.List;
import java.util.Optional;


public interface JpaRequestRepository extends JpaRepository<Request, Integer> {

    Optional<Request> findByIdAndRequesterId(int id, int requesterId);

    List<Request> findByRequesterId(int requesterId);

    List<Request> findByRequesterIdAndEventId(int requesterId, int eventId);

    List<Request> findByEventIdAndStatus(int id, Status status);

    List<Request> findByEventId(int id);
}