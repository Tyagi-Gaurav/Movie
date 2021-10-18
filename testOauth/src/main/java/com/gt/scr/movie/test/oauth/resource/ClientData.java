package com.gt.scr.movie.test.oauth.resource;

import java.util.List;

public record ClientData(List<String> redirectUris,
                         String clientId,
                         String clientSecret) {}
