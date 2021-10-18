package com.gt.scr.movie.test.oauth.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/test/oauth")
public class OauthResource {

    private static final Logger LOG = LoggerFactory.getLogger(OauthResource.class);

    @Autowired
    private Map<String, ClientData> clientDataMap;

    @GetMapping(consumes = "application/vnd.oauth.get.v1+json",
            produces = "application/vnd.oauth.get.v1+json",
            value = "/authorize")
    public Mono<ClientData> getClient(@RequestParam(value = "client_id") String clientId,
                                      @RequestParam(value = "response_type") String responseType,
                                      @RequestParam(value = "response_mode") String responseMode,
                                      @RequestParam(value = "redirect_uri") String redirectUri,
                                      @RequestParam(value = "scope") String scope,
                                      @RequestParam(value = "state") String state) {
        LOG.info("ClientId: {}, ResponseType: {}, ResponseMode: {}, RedirectURI: {}, Scope: {}, State: {}",
                clientId, responseType, responseMode, redirectUri, scope, state);

        Optional<ClientData> clientData = Optional.ofNullable(clientDataMap.get(clientId));
        return clientData.filter(cd -> cd.redirectUris().contains(redirectUri))
                .map(Mono::just)
                .orElse(Mono.error(() -> new IllegalCallerException("Invalid client id: " + clientId)));
    }

    @PostMapping(value = "/token",
            consumes = "application/vnd.oauth.post.token.v1+json",
            produces = "application/vnd.oauth.post.token.v1+json")
    public Mono<TokenDetails> oauthToken(@RequestParam(value = "grant_type") String grantType,
                                         @RequestParam(value = "username") String username,
                                         @RequestParam(value = "password") String password) {
        LOG.info("GrantType: {}, userName: {}, Password: {}",
                grantType, username, password);

        return Mono.just(new TokenDetails(
                UUID.randomUUID().toString(),
                "example",
                3600,
                UUID.randomUUID().toString(),
                "example_parameter"
        ));
    }
}
