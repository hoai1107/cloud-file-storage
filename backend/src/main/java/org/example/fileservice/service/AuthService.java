package org.example.fileservice.service;

import org.example.fileservice.dto.request.UserLoginDTO;
import org.example.fileservice.dto.request.UserRegisterDTO;
import org.example.fileservice.dto.response.UserDTO;
import org.example.fileservice.exception.BadRequestException;
import org.example.fileservice.mapper.UserMapper;
import org.example.fileservice.model.User;
import org.example.fileservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO register(UserRegisterDTO userRegisterDTO) {
        User user = UserMapper.INSTANCE.userRegisterDTOtoUser(userRegisterDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.save(user);
        return UserMapper.INSTANCE.usertoUserDTO(user);
    }

    public void login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByUsername(userLoginDTO.username());
        if(user == null || !passwordEncoder.matches(userLoginDTO.password(), user.getPassword())){
            throw new BadRequestException("Invalid credentials");
        }
    }
}
