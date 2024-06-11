package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.model.Request;

import java.util.List;


@Mapper(componentModel = "spring", uses = RequestMapper.class)
public interface RequestMapper {

    @Mapping(target = "created", source = "request.created", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "event", source = "request.event.id")
    @Mapping(target = "requester", source = "request.requester.id")
    ParticipationRequestDto toRequestDto(Request request);

    List<ParticipationRequestDto> toRequestsDto(List<Request> requests);
}