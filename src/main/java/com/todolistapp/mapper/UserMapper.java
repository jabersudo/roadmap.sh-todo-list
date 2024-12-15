package com.todolistapp.mapper;


import com.todolistapp.model.entity.Users;
import com.todolistapp.model.payload.request.UserLoginRequest;
import com.todolistapp.model.payload.request.UserRegistrationRequest;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
        builder = @Builder(disableBuilder = true),
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    Users userMapper(UserRegistrationRequest userRegistrationRequest);

    Users userMapper(UserLoginRequest request);
}
