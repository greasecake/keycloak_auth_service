package com.greasecake.keycloak_auth_service;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    KeycloakService keycloakService;

    @PostMapping("/create")
    public Optional<AccessTokenResponse> create(@RequestBody LoginRequest loginRequest) {
        return keycloakService.createToken(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("/refresh")
    public Optional<AccessTokenResponse> refresh(@RequestBody String refreshToken) {
        return keycloakService.refreshToken(refreshToken);
    }
}
