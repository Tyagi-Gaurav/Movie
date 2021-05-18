Feature: User management by the admin user

  Scenario: Admin user should be able to create other users
    Given a user creates a new account and performs login with user name '<random>' and role 'ADMIN'
    When the authenticated admin user creates another user with user name '<random>' and role 'USER'
    Then the response should be received with HTTP status code 204
    And the regular user attempts to login again
    Then the response should be received with HTTP status code 200
    And the user login response contains an authorisation token

  Scenario: Admin user should be able to delete other users
    Given a user creates a new account and performs login with user name '<random>' and role 'ADMIN'
    And the authenticated admin user creates another user with user name '<random>' and role 'USER'
    Then the response should be received with HTTP status code 204
    And the authenticated admin user creates another user with user name '<random>' and role 'USER'
    Then the response should be received with HTTP status code 204
    And the regular user attempts to login again
    Then the response should be received with HTTP status code 200
    And the user login response contains an authorisation token
    And the userId for the user is recorded
    And the admin user attempts to login again
    When the authenticated admin user deletes the previously created regular user
    Then the response should be received with HTTP status code 200
    And the regular user attempts to login again
    Then the response should be received with HTTP status code 401
