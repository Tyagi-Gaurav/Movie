Feature: Logged in admin user should be able to access timezone records and users

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

  Scenario: Authenticated admin user should be able to add the timezone records for other users
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the userId for the user is recorded
    And a user creates a new account and performs login with user name '<random>' and role 'ADMIN'
    And the authenticated user attempts to create a new timezone for the regular user
      | name       | city      | gmtOffset |
      | Africa     | Mauritius | 2         |
      | Asia/India | Delhi     | 5         |
    Then the response should be received with HTTP status code 204
    And the regular user attempts to login again
    And the authenticated user attempts to read the timezones
    Then the response should be received with HTTP status code 200
    And the timezone read response contains the following timezones
      | name       | city      | gmtOffset |
      | Africa     | Mauritius | 2         |
      | Asia/India | Delhi     | 5         |

  Scenario: Authenticated admin user should be able to edit the timezone records for other users
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
    When the admin user attempts to update the timezone with name: 'Asia/India' to
      | name       | city      | gmtOffset |
      | Asia/India | Bangalore | 7         |
    Then the response should be received with HTTP status code 200
    And the regular user attempts to login again
    And the authenticated user attempts to read the timezones
    And the timezone read response contains the following timezones
      | name       | city      | gmtOffset |
      | Africa     | Mauritius | 2         |
      | Asia/India | Bangalore | 7         |

  Scenario: Authenticated user should be able to read the timezone records for only self
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new timezone
      | name       | city      | gmtOffset |
      | Africa     | Mauritius | 2         |
      | Asia/India | Delhi     | 5         |
      | America    | New York  | -5        |
    Then the response should be received with HTTP status code 204
    And the userId for the user is recorded
    And a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new timezone
      | name       | city     | gmtOffset |
      | Australia  | Brisbane | 7         |
      | Asia/India | Mumbai   | 5         |
      | America    | Boston   | -10       |
    Then the response should be received with HTTP status code 204
    And the authenticated user attempts to read the timezones for the previous user
    Then the response should be received with HTTP status code 403
