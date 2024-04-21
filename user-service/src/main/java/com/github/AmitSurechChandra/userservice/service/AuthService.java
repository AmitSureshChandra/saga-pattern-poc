package com.github.AmitSurechChandra.userservice.service;

import com.github.AmitSureshChandra.commonmodule.dto.user.LoginReq;
import com.github.AmitSureshChandra.commonmodule.dto.user.LoginResp;
import com.github.AmitSureshChandra.commonmodule.dto.user.SignupReq;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public LoginResp login(LoginReq loginReq) {
        var loginResp = new LoginResp();

        // check pass
        if(userService.existsUserByUsernameAndPassword(loginReq.getUsername(), loginReq.getPassword())){

        }


        loginResp.setToken(jwtService.generateToken(loginReq.getUsername()));
        return loginResp;
    }

    public LoginResp signup(SignupReq signupDto) {
        return new LoginResp(jwtService.generateToken(userService.addUser(signupDto).getUsername()));
    }
}
