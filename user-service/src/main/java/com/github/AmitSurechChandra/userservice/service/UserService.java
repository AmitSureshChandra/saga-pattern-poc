package com.github.AmitSurechChandra.userservice.service;

import com.github.AmitSureshChandra.commonmodule.dto.user.SignupReq;
import com.github.AmitSureshChandra.commonmodule.dto.user.UserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    Map<String, UserDto> users = new HashMap<>();

    @PostConstruct
    void init() {
        users.put(
                "ak00029", new UserDto("ak00029", "akumar00029@gmail.com", "+91 1234567809", "Pass#123.")
        );

        users.put(
                "dj2456", new UserDto("dj2456", "dj@example.com", "+91 7237145707", "Pass#123.")
        );
    }

    public UserDto addUser(SignupReq loginReq) {
        var userDto = new UserDto();
        userDto.setUsername(loginReq.getUsername());
        userDto.setEmail(loginReq.getEmail());
        userDto.setPassword(loginReq.getPassword());
        users.put(loginReq.getUsername(), userDto);
        return userDto;
    }

    public boolean existsUserByUsernameAndPassword(String username, String password) {
        var user = users.get(username);
        return user.getPassword().equals(password);
    }
}
