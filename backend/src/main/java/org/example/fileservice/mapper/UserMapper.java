package org.example.fileservice.mapper;

import org.example.fileservice.dto.request.UserRegisterDTO;
import org.example.fileservice.dto.response.UserDTO;
import org.example.fileservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userRegisterDTOtoUser(UserRegisterDTO user);
    UserDTO usertoUserDTO(User user);
}
