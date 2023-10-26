package com.greasecake.keycloak_auth_service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.keycloak.OAuth2Constants.*;
import static org.keycloak.OAuth2Constants.REFRESH_TOKEN;

@Component
public class KeycloakService {
    @Autowired
    ApplicationConfiguration applicationConfiguration;
    Logger log = LoggerFactory.getLogger(KeycloakService.class);

    public Optional<AccessTokenResponse> createToken(String username, String password) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(applicationConfiguration.getIdentityProviderAuthServerUrl())
                .realm(applicationConfiguration.getIdentityProviderRealm())
                .grantType(Constants.PASSWORD)
                .username(username)
                .password(password)
                .clientId(applicationConfiguration.getIdentityProviderClient())
                .clientSecret(applicationConfiguration.getIdentityProviderCredentialsSecret())
                .build();

        return Optional.ofNullable(keycloak.tokenManager().getAccessToken());
    }

    public Optional<AccessTokenResponse> refreshToken(String refreshToken) {
        String url = String.format("%s/realms/%s/protocol/openid-connect/token",
                applicationConfiguration.getIdentityProviderAuthServerUrl(),
                applicationConfiguration.getIdentityProviderRealm());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>() {{
            add(GRANT_TYPE, Constants.REFRESH_TOKEN);
            add(CLIENT_ID, applicationConfiguration.getIdentityProviderClient());
            add(CLIENT_SECRET, applicationConfiguration.getIdentityProviderCredentialsSecret());
            add(REFRESH_TOKEN, refreshToken);
        }};

        try {
            ResponseEntity<AccessTokenResponse> response = new RestTemplate().postForEntity(
                    url,
                    new HttpEntity<>(map, headers),
                    AccessTokenResponse.class);
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException e) {
            log.error("Invalid refresh token");
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
