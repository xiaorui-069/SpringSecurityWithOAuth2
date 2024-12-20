package com.stevens.clientserver;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Objects;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@RestController
public class Controller {

    private final RestClient restClient;
    private final OAuth2AuthorizedClientService authorizedClientService;
    Controller(RestClient restClient, OAuth2AuthorizedClientService authorizedClientService) {
        this.restClient = restClient;
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/hello")
    public String hello() {
        String endpoint = "http://localhost:8000/hello";
        return restClient.get()
                .uri(endpoint)
                .attributes(clientRegistrationId("my-client"))
                .retrieve()
                .body(String.class);
    }

    @GetMapping("/user")
    public Map<String, String> user(Authentication authentication) {
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient("my-client", authentication.getName());
        return Map.of("access_token", authorizedClient.getAccessToken().getTokenValue(),
                "refresh_token", Objects.requireNonNull(authorizedClient.getRefreshToken()).getTokenValue());
    }
}
