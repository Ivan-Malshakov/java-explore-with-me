package ru.practicum.ewm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.utils.OffsetBasedPageRequest;

import java.util.List;

public interface JpaUsersRepository extends JpaRepository<User, Integer> {

    Page<User> findByIdIn(List<Integer> ids, OffsetBasedPageRequest pageable);
}