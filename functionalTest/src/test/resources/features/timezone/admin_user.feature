Feature: Logged in admin user should be able to access timezone records and users

#  Scenario: UnAuthenticated admin user should not be allowed to access the system
#    Given the admin user performs the login
#    And an unauthenticated admin user intends to create a new timezone
#      | timezoneName | timezoneCity | GMTDifference |
#      | x            | y            | 1             |
#    When the admin user attempts to create a new timezone
#    Then a failure response is returned with HTTP status code 403

  Scenario: Authenticated admin user should be able to create a new timezone
    Given a user creates a new account and performs login with user name '<random>' and role 'ADMIN'
    And the authenticated user attempts to create a new timezone
      | name   | city      | gmtOffset |
      | Africa | Mauritius | 2         |
    Then the response should be received with HTTP status code 204

  Scenario: Authenticated admin user should be able to read the timezone records for all users
    Given a user creates a new account and performs login with user name '<random>' and role 'ADMIN'
    And a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new timezone
      | name       | city      | gmtOffset |
      | Africa     | Mauritius | 2         |
      | Asia/India | Delhi     | 5         |
    Then the response should be received with HTTP status code 204
    And the userId for the user is recorded
    And the admin user attempts to login again
    When the authenticated admin user attempts to read the timezones for user
    Then the response should be received with HTTP status code 200
    And the timezone read response contains the following timezones
      | name       | city      | gmtOffset |
      | Africa     | Mauritius | 2         |
      | Asia/India | Delhi     | 5         |

  Scenario: Authenticated admin user should be able to delete the timezone records for all users
    Given a user creates a new account and performs login with user name '<random>' and role 'ADMIN'
    And a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new timezone
      | name       | city      | gmtOffset |
      | Africa     | Mauritius | 2         |
      | Asia/India | Delhi     | 5         |
    Then the response should be received with HTTP status code 204
    And the userId for the user is recorded
    And the admin user attempts to login again
    When the authenticated admin user attempts to delete all the timezones for user
    Then the response should be received with HTTP status code 200
    And the authenticated admin user attempts to read the timezones for user
    Then the response should be received with HTTP status code 200
    And the timezone read response should be empty
    And the regular user attempts to login again
    And the authenticated user attempts to read the timezones
    And the timezone read response should be empty

  # More tests for admin able to CRUD users

#  This test should be creating users by admin and storing their password.
#  Then login through A, create timezone.
#  Then login through B, create timezone.
#  Then read timezone with userId= Option and it should not be able to read it.

#  Scenario: Authenticated user should be able to read the timezone records for only self
#    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
#    And the authenticated user attempts to create a new timezone
#      | name       | city      | gmtOffset |
#      | Africa     | Mauritius | 2         |
#      | Asia/India | Delhi     | 5         |
#      | America    | New York  | -5        |
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