Feature: Logged in users should be able to access timezone records

#  Scenario: UnAuthenticated user should not be allowed to access the system
#    Given an unauthenticated user intends to create a new timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | x            | y            | 1             |
#    When the user attempts to create a new timezone
#    Then a failure response is returned with HTTP status code 403

  Scenario: Authenticated user should be able to create a new timezone
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new timezone
      | name   | city      | gmtOffset |
      | Africa | Mauritius | 2         |
    Then the response should be received with HTTP status code 204

#  Scenario: Authenticated user should be able to read the timezone records
#    Given a user creates a new account with name <random> and performs a login
#    And the authenticated user intends to create a new timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | x            | y            | 1             |
#    And the user attempts to create a new timezone
#    Then a success response is returned with HTTP status code 204
#    And the authenticated user intends to create a new timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | a            | b            | 2             |
#    And the user attempts to create a new timezone
#    Then a success response is returned with HTTP status code 204
#    And the authenticated user intends to read the timezones
#    When the authenticated user attempts to read the timezones
#    Then a success response is returned with HTTP status code 200
#    And the response contains the following timezones
#      | timezoneName | timezoneCity | GMTDifference |
#      | a            | b            | 2             |
#      | x            | y            | 1             |
#
#  Scenario: Authenticated user should be able to delete the timezone records
#    Given a user creates a new account with name <random> and performs a login
#    And the authenticated user intends to create a new timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | x            | y            | 1             |
#    And the user attempts to create a new timezone
#    Then a success response is returned with HTTP status code 204
#    And the authenticated user intends to create a new timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | a            | b            | 2             |
#    And the user intends to delete the timezone
#    And the user attempts to delete the timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | a            | b            | 2             |
#    Then a success response is returned with HTTP status code 204
#    And the authenticated user intends to read the timezones
#    When the authenticated user attempts to read the timezones
#    Then a success response is returned with HTTP status code 200
#    And the response contains the following timezones
#      | timezoneName | timezoneCity | GMTDifference |
#      | x            | y            | 1             |
#
#  Scenario: Authenticated user should be able to read the timezone records for only self
#    Given a user creates a new account with name 'user1' and performs a login
#    And the authenticated user intends to create a new timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | x            | y            | 1             |
#    And a user creates a new account with name 'user2' and performs a login
#    And the authenticated user intends to create a new timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | a            | b            | 2             |
#    Then a success response is returned with HTTP status code 204
#    And the authenticated user intends to read the timezones
#    When the authenticated user attempts to read the timezones
#    Then a success response is returned with HTTP status code 200
#    And the response contains the following timezones
#      | timezoneName | timezoneCity | GMTDifference |
#      | a            | b            | 2             |
