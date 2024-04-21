package com.github.AmitSurechChandra.userservice.controller;

import com.github.AmitSurechChandra.userservice.service.AuthService;
import com.github.AmitSureshChandra.commonmodule.dto.user.LoginReq;
import com.github.AmitSureshChandra.commonmodule.dto.user.LoginResp;
import com.github.AmitSureshChandra.commonmodule.dto.user.SignupReq;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    LoginResp signup(@RequestBody @Valid SignupReq loginReq) {
        return authService.signup(loginReq);
    }

    @PostMapping("/login")
    LoginResp login(@RequestBody @Valid LoginReq loginReq) {
        return authService.login(loginReq);
    }
}
