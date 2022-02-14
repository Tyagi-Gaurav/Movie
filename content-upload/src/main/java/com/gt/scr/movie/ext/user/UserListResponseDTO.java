package com.gt.scr.movie.ext.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize
@JsonDeserialize
public record UserListResponseDTO(List<UserDetailsResponseDTO> userDetails) {}
