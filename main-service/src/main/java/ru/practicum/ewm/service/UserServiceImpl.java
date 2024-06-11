package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.NewUserRequest;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.exception.DataNotFoundException;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.repository.JpaUsersRepository;
import ru.practicum.ewm.utils.OffsetBasedPageRequest;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final JpaUsersRepository repository;
    private final UserMapper mapper;

    @Override
    @Transactional
    public UserDto saveUser(NewUserRequest userRequest) {
        return mapper.toDtoUser(repository.save(mapper.toModelUser(userRequest)));
    }

    @Override
    public List<UserDto> getUsers(List<Integer> ids, int from, int size) {
        List<User> users;
        if (ids != null) {
            users = repository.findByIdIn(ids, new OffsetBasedPageRequest(from, size)).toList();
        } else {
            users = repository.findAll(new OffsetBasedPageRequest(from, size)).toList();
        }
        return mapper.toDtoUsers(users);
    }

    @Override
    @Transactional
    public void removeUser(int id) {
        if (repository.findById(id).isEmpty()) {
            throw new DataNotFoundException("User with id = " + id + " not found");
        }
        repository.deleteById(id);
    }
}