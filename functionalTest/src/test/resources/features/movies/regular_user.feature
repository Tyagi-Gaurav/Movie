Feature: Logged in users should be able to access movie records

  Scenario: User should NOT be able to create a new movie without proper auth header
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie without passing auth header
      | name        | yearProduced | rating |
      | First Blood | 2000         | 7.8    |
    Then the response should be received with HTTP status code 401

  Scenario: Authenticated user should be able to create a new movie
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating |
      | First Blood | 2000         | 7.8    |
    Then the response should be received with HTTP status code 204

  Scenario: Authenticated user should NOT be able to create duplicate movies
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating |
      | First Blood | 2000         | 7.8    |
    Then the response should be received with HTTP status code 204
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating |
      | First Blood | 2000         | 7.8    |
    Then the response should be received with HTTP status code 403

  Scenario: Authenticated user should be able to read the movie records
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name          | yearProduced | rating |
      | First Blood   | 1990         | 7.8    |
      | Die Hard      | 1980         | 8.9    |
      | The President | 2001         | 6.3    |
    When the authenticated user attempts to read the movies
    Then the response should be received with HTTP status code 200
    And the movie read response contains the following records
      | name          | yearProduced | rating |
      | First Blood   | 1990         | 7.8    |
      | Die Hard      | 1980         | 8.9    |
      | The President | 2001         | 6.3    |

  Scenario: Authenticated user should be able to delete the movie records
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name          | yearProduced | rating |
      | First Blood   | 1990         | 7.8    |
      | Die Hard      | 1980         | 8.9    |
      | The President | 2001         | 6.3    |
    When the user attempts to delete the movie with name: 'First Blood' , yearProduced: '1990' and rating: '7.8'
    Then the response should be received with HTTP status code 200
    And the authenticated user attempts to read the movies
    Then the response should be received with HTTP status code 200
    And the movie read response contains the following records
      | name          | yearProduced | rating |
      | Die Hard      | 1980         | 8.9    |
      | The President | 2001         | 6.3    |

  Scenario: Authenticated user should be able to update the movie records
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name          | yearProduced | rating |
      | First Blood   | 1990         | 7.8    |
      | Die Hard      | 1980         | 8.9    |
      | The President | 2001         | 6.3    |
    When the user attempts to update the movie with name: 'Die Hard' to
      | name     | yearProduced | rating |
      | Die Hard | 1989         | 9.2    |
    Then the response should be received with HTTP status code 200
    And the authenticated user attempts to read the movies
    Then the response should be received with HTTP status code 200
    And the movie read response contains the following records
      | name          | yearProduced | rating |
      | First Blood   | 1990         | 7.8    |
      | Die Hard      | 1989         | 9.2    |
      | The President | 2001         | 6.3    |

  Scenario: Authenticated user should be able to update single fields for the movie records
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name          | yearProduced | rating |
      | First Blood   | 1990         | 7.8    |
      | Die Hard      | 1980         | 8.9    |
      | The President | 2001         | 6.3    |
    When the user attempts to update the movie with name: 'First Blood' to 'Second Blood'
    Then the response should be received with HTTP status code 200
    And the authenticated user attempts to read the movies
    Then the response should be received with HTTP status code 200
    And the movie read response contains the following records
      | name          | yearProduced | rating |
      | Second Blood  | 1990         | 7.8    |
      | Die Hard      | 1980         | 8.9    |
      | The President | 2001         | 6.3    |

  Scenario: Authenticated user should be able to read the movie records for only self
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name          | yearProduced | rating |
      | First Blood   | 1990         | 7.8    |
      | Die Hard      | 1980         | 8.9    |
      | The President | 2001         | 6.3    |
    Then the response should be received with HTTP status code 204
    And the userId for the user is recorded
    And a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name               | yearProduced | rating |
      | Second Blood       | 1990         | 7.8    |
      | Die Easy           | 1980         | 8.9    |
      | The Prime Minister | 2001         | 6.3    |
    Then the response should be received with HTTP status code 204
    And the authenticated user attempts to read the movies for the previous user
    Then the response should be received with HTTP status code 403