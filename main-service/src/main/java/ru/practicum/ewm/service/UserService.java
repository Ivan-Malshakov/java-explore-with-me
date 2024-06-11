package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.NewUserRequest;
import ru.practicum.ewm.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto saveUser(NewUserRequest userRequest);

    List<UserDto> getUsers(List<Integer> ids, int from, int size);

    void removeUser(int id);
}