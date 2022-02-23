Feature: Audit events should be generated and be accessible to admin users only

  Scenario: When user adds a movie a MOVIE_CREATE event should be generated and accessible to admin
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating |
      | First Blood | 2000         | 7.8    |
    Then the response should be received with HTTP status code 200

