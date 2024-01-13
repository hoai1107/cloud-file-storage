package org.example.fileservice.service;

import org.example.fileservice.dto.request.UserLoginDTO;
import org.example.fileservice.dto.request.UserRegisterDTO;
import org.example.fileservice.dto.response.JwtTokenDTO;
import org.example.fileservice.dto.response.UserDTO;
import org.example.fileservice.exception.BadRequestException;
import org.example.fileservice.mapper.UserMapper;
import org.example.fileservice.model.User;
import org.example.fileservice.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public UserDTO register(UserRegisterDTO userRegisterDTO) {
        User user = UserMapper.INSTANCE.userRegisterDTOtoUser(userRegisterDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.save(user);
        return UserMapper.INSTANCE.usertoUserDTO(user);
    }

    public JwtTokenDTO login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByUsername(userLoginDTO.username());
        if(user == null || !passwordEncoder.matches(userLoginDTO.password(), user.getPassword())){
            throw new BadRequestException("Invalid credentials");
        }

        return new JwtTokenDTO(jwtService.generateToken(user));
    }
}
