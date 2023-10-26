package com.greasecake.keycloak_auth_service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationConfiguration {
    @Value("${keycloak.auth-server-url}")
    private String identityProviderAuthServerUrl;
    @Value("${keycloak.realm}")
    private String identityProviderRealm;
    @Value("${keycloak.client-id}")
    private String identityProviderClient;
    @Value("${keycloak.client-secret}")
    private String identityProviderCredentialsSecret;
}
