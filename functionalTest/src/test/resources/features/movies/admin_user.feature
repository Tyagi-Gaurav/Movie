Feature: Logged in admin user should be able to access movie records and users

  Scenario: Authenticated admin user should be able to create a new movie
    Given a user creates a new account and performs login with user name '<random>' and role 'ADMIN'
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating |
      | First Blood | 2000         | 7.8    |
    Then the response should be received with HTTP status code 204

  Scenario: Authenticated admin user should be able to read the movie records for all users
    Given a user creates a new account and performs login with user name '<random>' and role 'ADMIN'
    And a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating |
      | First Blood | 1990         | 7.8    |
      | Die Hard    | 1980         | 8.9    |
    Then the response should be received with HTTP status code 204
    And the userId for the user is recorded
    And the admin user attempts to login again
    When the authenticated admin user attempts to read the movies for user
    Then the response should be received with HTTP status code 200
    And the movie read response contains the following records
      | name        | yearProduced | rating |
      | First Blood | 1990         | 7.8    |
      | Die Hard    | 1980         | 8.9    |

  Scenario: Authenticated admin user should be able to delete the movie records for all users
    Given a user creates a new account and performs login with user name '<random>' and role 'ADMIN'
    And a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating |
      | First Blood | 1990         | 7.8    |
      | Die Hard    | 1980         | 8.9    |
    Then the response should be received with HTTP status code 204
    And the userId for the user is recorded
    And the admin user attempts to login again
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
    And a user creates a new account and performs login with user name '<random>' and role 'ADMIN'
    And the authenticated user attempts to create a new movie for the regular user
      | name        | yearProduced | rating |
      | First Blood | 1990         | 7.8    |
      | Die Hard    | 1980         | 8.9    |
    Then the response should be received with HTTP status code 204
    And the regular user attempts to login again
    And the authenticated user attempts to read the movies
    Then the response should be received with HTTP status code 200
    And the movie read response contains the following records
      | name        | yearProduced | rating |
      | First Blood | 1990         | 7.8    |
      | Die Hard    | 1980         | 8.9    |

  Scenario: Authenticated admin user should be able to edit the movie records for other users
    Given a user creates a new account and performs login with user name '<random>' and role 'ADMIN'
    And a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new movie
      | name        | yearProduced | rating |
      | First Blood | 1990         | 7.8    |
      | Die Hard    | 1980         | 8.9    |
    Then the response should be received with HTTP status code 204
    And the userId for the user is recorded
    And the admin user attempts to login again
    When the authenticated admin user attempts to read the movies for user
    Then the response should be received with HTTP status code 200
    When the admin user attempts to update the movie with name: 'First Blood' to
      | name         | yearProduced | rating |
      | Second Blood | 2010         | 7.8    |
    Then the response should be received with HTTP status code 200
    And the regular user attempts to login again
    And the authenticated user attempts to read the movies
    And the movie read response contains the following records
      | name         | yearProduced | rating |
      | Second Blood | 2010         | 7.8    |
      | Die Hard     | 1980         | 8.9    |

#  Scenario: Authenticated user should be able to read the movie records for only self
#    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
#    And the authenticated user attempts to create a new movie
#      | name          | yearProduced | rating |
#      | First Blood   | 1990         | 7.8    |
#      | Die Hard      | 1980         | 8.9    |
#      | The President | 2001         | 6.3    |
#    Then the response should be received with HTTP status code 204
#    And the userId for the user is recorded
#    And a user creates a new account and performs login with user name '<random>' and role 'USER'
#    And the authenticated user attempts to create a new movie
#      | name               | yearProduced | rating |
#      | Second Blood       | 1990         | 7.8    |
#      | Die Easy           | 1980         | 8.9    |
#      | The Prime Minister | 2001         | 6.3    |
#    Then the response should be received with HTTP status code 204
#    And the authenticated user attempts to read the movies for the previous user
#    Then the response should be received with HTTP status code 403
