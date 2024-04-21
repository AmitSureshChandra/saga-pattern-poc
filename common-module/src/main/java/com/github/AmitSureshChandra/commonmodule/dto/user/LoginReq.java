package com.github.AmitSureshChandra.commonmodule.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginReq {
    private String username;
    private String password;
}
