Feature: Logged in admin user should be able to access movie records and users

  Scenario: Authenticated admin user should be able to create a new movie
    Given the global admin user logs into the system
    And the authenticated admin user creates another user with user name '<random>' and role 'ADMIN'
    Then the response should be received with HTTP status code 204
    And the admin user attempts to login again
    Then the response should be received with HTTP status code 200
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | First Blood | 2000         | 7.8    | ACTION | MOVIE       | 12A       | true        |
    Then the response should be received with HTTP status code 200

  Scenario: Authenticated admin user should be able to read the movie records for all users
    Given the global admin user logs into the system
    And a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | First Blood | 1990         | 7.8    | ACTION | MOVIE       | 12A       | true        |
      | Die Hard    | 1980         | 8.9    | ACTION | MOVIE       | 15        | false       |
    Then the response should be received with HTTP status code 200
    And the userId for the user is recorded
    And the global admin user logs into the system
    When the authenticated admin user attempts to read the movies for user
    Then the response should be received with HTTP status code 200
    And the movie read response contains the following records
      | name        | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | First Blood | 1990         | 7.8    | ACTION | MOVIE       | 12A       | true        |
      | Die Hard    | 1980         | 8.9    | ACTION | MOVIE       | 15        | false       |

  Scenario: Authenticated admin user should be able to delete the movie records for all users
    Given the global admin user logs into the system
    And a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | First Blood | 1990         | 7.8    | ACTION | MOVIE       | 12A       | true        |
      | Die Hard    | 1980         | 8.9    | ACTION | MOVIE       | 15        | false       |
    Then the response should be received with HTTP status code 200
    And the userId for the user is recorded
    And the global admin user logs into the system
    When the authenticated admin user attempts to delete all the movies for user
    Then the response should be received with HTTP status code 200
    And the authenticated admin user attempts to read the movies for user
    Then the response should be received with HTTP status code 200
    And the movie read response should be empty
    And the regular user attempts to login again
    And the authenticated user attempts to read the movies
    And the movie read response should be empty

  Scenario: Authenticated admin user should be able to add the movie records for other users
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the userId for the user is recorded
    And the global admin user logs into the system
    And the authenticated user attempts to create a new movie for the regular user
      | name        | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | First Blood | 1990         | 7.8    | ACTION | MOVIE       | 12A       | true        |
      | Die Hard    | 1980         | 8.9    | ACTION | MOVIE       | 15        | false       |
    Then the response should be received with HTTP status code 200
    And the regular user attempts to login again
    And the authenticated user attempts to read the movies
    Then the response should be received with HTTP status code 200
    And the movie read response contains the following records
      | name        | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | First Blood | 1990         | 7.8    | ACTION | MOVIE       | 12A       | true        |
      | Die Hard    | 1980         | 8.9    | ACTION | MOVIE       | 15        | false       |

  Scenario: Authenticated admin user should be able to edit the movie records for other users
    Given the global admin user logs into the system
    And a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | First Blood | 1990         | 7.8    | ACTION | MOVIE       | 12A       | true        |
      | Die Hard    | 1980         | 8.9    | ACTION | MOVIE       | 15        | false       |
    Then the response should be received with HTTP status code 200
    And the userId for the user is recorded
    And the global admin user logs into the system
    When the authenticated admin user attempts to read the movies for user
    Then the response should be received with HTTP status code 200
    When the admin user attempts to update the movie with name: 'First Blood' to
      | name         | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | Second Blood | 2010         | 7.8    | ACTION | MOVIE       | 12A       | true        |
    Then the response should be received with HTTP status code 200
    And the regular user attempts to login again
    And the authenticated user attempts to read the movies
    And the movie read response contains the following records
      | name         | yearProduced | rating | genre  | contentType | ageRating | isShareable |
      | Second Blood | 2010         | 7.8    | ACTION | MOVIE       | 12A       | true        |
      | Die Hard     | 1980         | 8.9    | ACTION | MOVIE       | 15        | false       |
