Feature: Logged in admin user should be able to access timezone records and users

#  Scenario: UnAuthenticated admin user should not be allowed to access the system
#    Given the admin user performs the login
#    And an unauthenticated admin user intends to create a new timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | x            | y            | 1             |
#    When the admin user attempts to create a new timezone
#    Then a failure response is returned with HTTP status code 403
#
#  Scenario: Authenticated admin user should be able to create a new timezone
#    Given the admin user performs the login
#    And the authenticated admin user intends to create a new timezone
#    | timezoneName | timezoneCity | GMTDifference |
#    | x            | y            | 1             |
#    When the admin user attempts to create a new timezone
#    Then a success response is returned with HTTP status code 204
#
#  Scenario: Authenticated admin user should be able to read the timezone records for all users
#    Given a user creates a new account with name 'user1' and performs a login
#    And the authenticated user intends to create a new timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | x            | y            | 1             |
#    And a user creates a new account with name 'user2' and performs a login
#    And the authenticated user intends to create a new timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | a            | b            | 2             |
#    Then a success response is returned with HTTP status code 204
#    And the admin user performs the login
#    When the authenticated admin user attempts to read the timezones for 'user1'
#    Then a success response is returned with HTTP status code 200
#    And the response contains the following timezones
#      | timezoneName | timezoneCity | GMTDifference |
#      | x            | y            | 1             |
#
#  Scenario: Authenticated admin user should be able to delete the timezone records for all users
#    Given a user creates a new account with name 'user1' and performs a login
#    And the authenticated user intends to create a new timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | x            | y            | 1             |
#    And a user creates a new account with name 'user2' and performs a login
#    And the authenticated user intends to create a new timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | a            | b            | 2             |
#    Then a success response is returned with HTTP status code 204
#    And the admin user performs the login
#    And the authenticated admin user attempts to delete the timezones for 'user1'
#      | x            | y            | 1             |
#    Then a success response is returned with HTTP status code 200
#    When the authenticated admin user attempts to read the timezones for 'user1'
#    Then a success response is returned with HTTP status code 200
#    And the response should be empty

  # More tests for admin able to CRUD users