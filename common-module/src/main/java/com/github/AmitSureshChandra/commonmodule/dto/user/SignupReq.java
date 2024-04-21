package com.github.AmitSureshChandra.commonmodule.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupReq {
    private String username;
    private String email;
    private String mobile;
    private String password;
}