package com.samaniasoft.toproleplay.infra.security.jwt;

import lombok.Data;

@Data
class JwtLoginInput {
    private String username;
    private String password;
}
