@Migrated
Feature: User should be able to upload video against a movie

  Scenario: Upload new video for a movie
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | First Blood | 2000         | 7.8    | ACTION | MOVIE       | 12A       | true        |
    And the response should be received with HTTP status code 200
    And the movie-id of the movie is recorded
    When the user attempts to upload video for the movie - '/data/SmallVideo.ts'
    And the response should be received with HTTP status code 200
    And the size of video returned should be 1769472
