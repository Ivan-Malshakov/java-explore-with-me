package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.NewUserRequest;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.model.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserMapper {
    User toModelUser(NewUserRequest userRequest);

    UserDto toDtoUser(User user);

    List<UserDto> toDtoUsers(List<User> users);
}