package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Comment;
import ru.practicum.ewm.utils.OffsetBasedPageRequest;

import java.util.List;

public interface JpaCommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByAuthor_IdOrderByCreatedAsc(int authorId, OffsetBasedPageRequest pageable);

    List<Comment> findByAuthor_IdOrderByCreatedDesc(int authorId, OffsetBasedPageRequest pageable);

    List<Comment> findByAuthor_IdOrderByEventIdAsc(int authorId, OffsetBasedPageRequest pageable);

    List<Comment> findByAuthor_IdOrderByEventIdDesc(int authorId, OffsetBasedPageRequest pageable);

    List<Comment> findByEvent_IdOrderByCreatedAsc(int eventId, OffsetBasedPageRequest pageable);

    List<Comment> findByEvent_IdOrderByCreatedDesc(int eventId, OffsetBasedPageRequest pageable);

    List<Comment> findByEvent_IdOrderByEventIdAsc(int eventId, OffsetBasedPageRequest pageable);

    List<Comment> findByEvent_IdOrderByEventIdDesc(int eventId, OffsetBasedPageRequest pageable);
}