Feature: Logged in users should be able to access movie records

  @Migrated
  Scenario: User should NOT be able to create a new movie without proper auth header
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie without passing auth header
      | name        | yearProduced | rating | genre    | contentType | ageRating | isShareable |
      | First Blood | 2000         | 7.8    | THRILLER | MOVIE       | 12A       | true        |
    Then the response should be received with HTTP status code 401

  @Migrated
  Scenario: Authenticated user should be able to create a new movie
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | First Blood | 2000         | 7.8    | ACTION | MOVIE       | 12A       | true        |
    Then the response should be received with HTTP status code 200
    And the response should contain a movieId in a UUID format

  @Migrated
  Scenario: Authenticated user should NOT be able to create duplicate movies
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | First Blood | 2000         | 7.8    | ACTION | MOVIE       | 12A       | true        |
    Then the response should be received with HTTP status code 200
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | First Blood | 2000         | 7.8    | ACTION | MOVIE       | 12A       | true        |
    Then the response should be received with HTTP status code 403

  @Migrated
  Scenario: Authenticated user should be able to read the movie records
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name          | yearProduced | rating | genre   | contentType | ageRating | isShareable |
      | First Blood   | 1990         | 7.8    | ACTION  | MOVIE       | 12A       | true        |
      | Die Hard      | 1980         | 8.9    | ACTION  | MOVIE       | 12A       | true        |
      | The President | 2001         | 6.3    | ROMANCE | MOVIE       | 15        | false       |
    When the authenticated user attempts to read the movies
    Then the response should be received with HTTP status code 200
    And the movie read response contains the following records
      | name          | yearProduced | rating | genre   | contentType | ageRating | isShareable |
      | First Blood   | 1990         | 7.8    | ACTION  | MOVIE       | 12A       | true        |
      | Die Hard      | 1980         | 8.9    | ACTION  | MOVIE       | 12A       | true        |
      | The President | 2001         | 6.3    | ROMANCE | MOVIE       | 15        | false       |

  @Migrated
  Scenario: Authenticated user should be able to delete the movie records
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name          | yearProduced | rating | genre   | contentType | ageRating | isShareable |
      | First Blood   | 1990         | 7.8    | ACTION  | MOVIE       | 12A       | true        |
      | Die Hard      | 1980         | 8.9    | ACTION  | MOVIE       | 12A       | true        |
      | The President | 2001         | 6.3    | ROMANCE | MOVIE       | 15        | false       |
    When the user attempts to delete the movie with name: 'First Blood' , yearProduced: '1990' and rating: '7.8'
    Then the response should be received with HTTP status code 200
    And the authenticated user attempts to read the movies
    Then the response should be received with HTTP status code 200
    And the movie read response contains the following records
      | name          | yearProduced | rating | genre   | contentType | ageRating | isShareable |
      | Die Hard      | 1980         | 8.9    | ACTION  | MOVIE       | 12A       | true        |
      | The President | 2001         | 6.3    | ROMANCE | MOVIE       | 15        | false       |

  @Migrated
  Scenario: Authenticated user should be able to update the movie records
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name          | yearProduced | rating | genre   | contentType | ageRating | isShareable |
      | First Blood   | 1990         | 7.8    | ACTION  | MOVIE       | 12A       | true        |
      | Die Hard      | 1980         | 8.9    | ACTION  | MOVIE       | 12A       | true        |
      | The President | 2001         | 6.3    | ROMANCE | MOVIE       | 15        | false       |
    When the user attempts to update the movie with name: 'Die Hard' to
      | name     | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | Die Hard | 1989         | 9.2    | ACTION | MOVIE       | 12A       | true        |
    Then the response should be received with HTTP status code 200
    And the authenticated user attempts to read the movies
    Then the response should be received with HTTP status code 200
    And the movie read response contains the following records
      | name          | yearProduced | rating | genre   | contentType | ageRating | isShareable |
      | First Blood   | 1990         | 7.8    | ACTION  | MOVIE       | 12A       | true        |
      | Die Hard      | 1989         | 9.2    | ACTION  | MOVIE       | 12A       | true        |
      | The President | 2001         | 6.3    | ROMANCE | MOVIE       | 15        | false       |

  @Migrated
  Scenario: Authenticated user should be able to update single fields for the movie records
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name          | yearProduced | rating | genre   | contentType | ageRating | isShareable |
      | First Blood   | 1990         | 7.8    | ACTION  | MOVIE       | 12A       | true        |
      | Die Hard      | 1980         | 8.9    | ACTION  | MOVIE       | 12A       | true        |
      | The President | 2001         | 6.3    | ROMANCE | MOVIE       | 15        | false       |
    When the user attempts to update the movie with name: 'First Blood' to 'Second Blood'
    Then the response should be received with HTTP status code 200
    And the authenticated user attempts to read the movies
    Then the response should be received with HTTP status code 200
    And the movie read response contains the following records
      | name          | yearProduced | rating | genre   | contentType | ageRating | isShareable |
      | Second Blood  | 1990         | 7.8    | ACTION  | MOVIE       | 12A       | true        |
      | Die Hard      | 1980         | 8.9    | ACTION  | MOVIE       | 12A       | true        |
      | The President | 2001         | 6.3    | ROMANCE | MOVIE       | 15        | false       |

  Scenario: Authenticated user should be able to read the movie records for only self
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name          | yearProduced | rating | genre   | contentType | ageRating | isShareable |
      | First Blood   | 1990         | 7.8    | ACTION  | MOVIE       | 12A       | true        |
      | Die Hard      | 1980         | 8.9    | ACTION  | MOVIE       | 12A       | true        |
      | The President | 2001         | 6.3    | ROMANCE | MOVIE       | 15        | false       |
    Then the response should be received with HTTP status code 200
    And the userId for the user is recorded
    And a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name               | yearProduced | rating | genre   | contentType | ageRating | isShareable |
      | Second Blood       | 1990         | 7.8    | ACTION  | MOVIE       | 12A       | true        |
      | Die Easy           | 1980         | 8.9    | ACTION  | MOVIE       | 12A       | true        |
      | The Prime Minister | 2001         | 6.3    | ROMANCE | MOVIE       | 15        | false       |
    Then the response should be received with HTTP status code 200
    And the authenticated user attempts to read the movies for the previous user
    Then the response should be received with HTTP status code 403