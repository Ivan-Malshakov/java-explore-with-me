package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.State;
import ru.practicum.ewm.utils.OffsetBasedPageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JpaEventsRepository extends JpaRepository<Event, Integer> {
    List<Event> findByInitiatorId(int initiatorId, OffsetBasedPageRequest pageable);

    List<Event> findByInitiatorIdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(List<Integer> users,
                                                                                                List<State> states,
                                                                                                List<Integer> categories,
                                                                                                LocalDateTime rangeStart,
                                                                                                LocalDateTime rangeEnd,
                                                                                                OffsetBasedPageRequest
                                                                                                        pageable);

    List<Event> findByStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(List<State> states,
                                                                                List<Integer> categories,
                                                                                LocalDateTime rangeStart,
                                                                                LocalDateTime rangeEnd,
                                                                                OffsetBasedPageRequest pageable);

    List<Event> findByInitiatorIdInAndStateInAndEventDateAfterAndEventDateBefore(List<Integer> users,
                                                                                 List<State> states,
                                                                                 LocalDateTime rangeStart,
                                                                                 LocalDateTime rangeEnd,
                                                                                 OffsetBasedPageRequest pageable);

    List<Event> findByStateInAndEventDateAfterAndEventDateBefore(List<State> states,
                                                                 LocalDateTime rangeStart,
                                                                 LocalDateTime rangeEnd,
                                                                 OffsetBasedPageRequest pageable);

    Optional<Event> findByIdAndInitiatorId(int id, int initiatorId);

    @Query("Select e from Event e Where e.id = :id AND e.state = ru.practicum.ewm.model.State.PUBLISHED")
    Optional<Event> findByIdAndState(int id);

    @Query("Select e from Event e Where e.eventDate > :rangeStart AND e.eventDate < :rangeEnd" +
            " AND e.state = ru.practicum.ewm.model.State.PUBLISHED")
    List<Event> findByStatePublishedAndEventDateAfterAndEventDateBefore(LocalDateTime rangeStart,
                                                                        LocalDateTime rangeEnd);

    @Query("Select e from Event e Where e.category.id IN :categories AND e.eventDate > :rangeStart " +
            "AND e.eventDate < :rangeEnd AND e.state = ru.practicum.ewm.model.State.PUBLISHED")
    List<Event> findByStatePublishedAndCategoryIdInAndEventDateAfterAndEventDateBefore(List<Integer> categories,
                                                                                       LocalDateTime rangeStart,
                                                                                       LocalDateTime rangeEnd);

    @Query("Select e from Event e Where (upper(e.annotation) like CONCAT('%',UPPER(:text),'%') " +
            "or upper(e.description) like CONCAT('%',UPPER(:text),'%')) AND e.eventDate > :rangeStart " +
            "AND e.eventDate < :rangeEnd AND e.state = ru.practicum.ewm.model.State.PUBLISHED")
    List<Event> findByStatePublishedAndTextAndEventDateAfterAndEventDateBefore(String text,
                                                                               LocalDateTime rangeStart,
                                                                               LocalDateTime rangeEnd);

    @Query("Select e from Event e Where e.category.id IN :categories " +
            "AND (upper(e.annotation) like CONCAT('%',UPPER(:text),'%') " +
            "or upper(e.description) like CONCAT('%',UPPER(:text),'%')) " +
            "AND e.eventDate > :rangeStart AND e.eventDate < :rangeEnd " +
            "AND e.state = ru.practicum.ewm.model.State.PUBLISHED")
    List<Event> findByStatePublishedFullFilter(String text, List<Integer> categories,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd);

    @Query("Select e from Event e Where e.eventDate > :rangeStart AND e.eventDate < :rangeEnd" +
            " AND e.state = ru.practicum.ewm.model.State.PUBLISHED AND e.paid = :paid")
    List<Event> findByStatePublishedAndEventDateAfterAndEventDateBefore(LocalDateTime rangeStart,
                                                                        LocalDateTime rangeEnd, Boolean paid);

    @Query("Select e from Event e Where e.category.id IN :categories AND e.eventDate > :rangeStart " +
            "AND e.eventDate < :rangeEnd AND e.state = ru.practicum.ewm.model.State.PUBLISHED AND e.paid = :paid")
    List<Event> findByStatePublishedAndCategoryIdInAndEventDateAfterAndEventDateBefore(List<Integer> categories,
                                                                                       LocalDateTime rangeStart,
                                                                                       LocalDateTime rangeEnd,
                                                                                       Boolean paid);

    @Query("Select e from Event e Where (upper(e.annotation) like CONCAT('%',UPPER(:text),'%') " +
            "or upper(e.description) like CONCAT('%',UPPER(:text),'%')) AND e.eventDate > :rangeStart " +
            "AND e.eventDate < :rangeEnd AND e.state = ru.practicum.ewm.model.State.PUBLISHED AND e.paid = :paid")
    List<Event> findByStatePublishedAndTextAndEventDateAfterAndEventDateBefore(String text,
                                                                               LocalDateTime rangeStart,
                                                                               LocalDateTime rangeEnd,
                                                                               Boolean paid);

    @Query("Select e from Event e Where e.category.id IN :categories " +
            "AND (upper(e.annotation) like CONCAT('%',UPPER(:text),'%') " +
            "or upper(e.description) like CONCAT('%',UPPER(:text),'%')) " +
            "AND e.eventDate > :rangeStart AND e.eventDate < :rangeEnd " +
            "AND e.state = ru.practicum.ewm.model.State.PUBLISHED AND e.paid = :paid")
    List<Event> findByStatePublishedFullFilter(String text, List<Integer> categories,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                               Boolean paid);

}