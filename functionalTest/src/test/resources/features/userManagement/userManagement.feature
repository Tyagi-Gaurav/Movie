@Migrated
Feature: User management by the admin user

  Scenario: Admin user should be able to create other regular users
    Given the global admin user logs into the system
    When the authenticated admin user creates another user with user name '<random>' and role 'USER'
    Then the response should be received with HTTP status code 204
    And the regular user attempts to login again
    Then the response should be received with HTTP status code 200
    And the user login response contains an authorisation token

  Scenario: Admin user should be able to create other admin users
    Given the global admin user logs into the system
    When the authenticated admin user creates another user with user name '<random>' and role 'ADMIN'
    Then the response should be received with HTTP status code 204
    And the admin user attempts to login again
    Then the response should be received with HTTP status code 200
    And the user login response contains an authorisation token

  Scenario: Admin user should be able to delete other users
    Given the global admin user logs into the system
    And the authenticated admin user creates another user with user name '<random>' and role 'USER'
    Then the response should be received with HTTP status code 204
    And the authenticated admin user creates another user with user name '<random>' and role 'USER'
    Then the response should be received with HTTP status code 204
    And the regular user attempts to login again
    Then the response should be received with HTTP status code 200
    And the user login response contains an authorisation token
    And the userId for the user is recorded
    And the global admin user logs into the system
    When the authenticated admin user deletes the previously created regular user
    Then the response should be received with HTTP status code 200
    And the regular user attempts to login again
    Then the response should be received with HTTP status code 401

  Scenario: Admin user should be able to view all other users
    Given the global admin user logs into the system
    And the authenticated admin user creates another user with user name '<random>' and role 'USER'
    Then the response should be received with HTTP status code 204
    And the authenticated admin user creates another user with user name '<random>' and role 'USER'
    Then the response should be received with HTTP status code 204
    And the global admin user logs into the system
    When the authenticated admin user retrieves a list of all users
    Then the response should be received with HTTP status code 200