package com.gt.scr.movie.test.oauth.resource;

public record TokenDetails(String access_token,
                           String token_type,
                           long expires_in,
                           String refresh_token,
                           String example_parameter) {
}
