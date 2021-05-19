Feature: Logged in users should be able to access timezone records

  Scenario: Authenticated user should be able to create a new timezone
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new timezone
      | name   | city      | gmtOffset |
      | Africa | Mauritius | 2         |
    Then the response should be received with HTTP status code 204

  Scenario: Authenticated user should NOT be able to create a duplicate timezones
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new timezone
      | name   | city      | gmtOffset |
      | Africa | Mauritius | 2         |
    Then the response should be received with HTTP status code 204
    And the authenticated user attempts to create a new timezone
      | name   | city      | gmtOffset |
      | Africa | Mauritius | 2         |
    Then the response should be received with HTTP status code 403

  Scenario: Authenticated user should be able to read the timezone records
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new timezone
      | name       | city      | gmtOffset |
      | Africa     | Mauritius | 2         |
      | Asia/India | Delhi     | 5         |
      | America    | New York  | -5        |
    When the authenticated user attempts to read the timezones
    Then the response should be received with HTTP status code 200
    And the timezone read response contains the following timezones
      | name       | city      | gmtOffset |
      | Africa     | Mauritius | 2         |
      | Asia/India | Delhi     | 5         |
      | America    | New York  | -5        |

  Scenario: Authenticated user should be able to delete the timezone records
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new timezone
      | name       | city      | gmtOffset |
      | Africa     | Mauritius | 2         |
      | Asia/India | Delhi     | 5         |
      | America    | New York  | -5        |
    When the user attempts to delete the timezone with name: 'Asia/India' , city: 'Delhi' and gmtOffset: '5'
    Then the response should be received with HTTP status code 200
    And the authenticated user attempts to read the timezones
    Then the response should be received with HTTP status code 200
    And the timezone read response contains the following timezones
      | name    | city      | gmtOffset |
      | Africa  | Mauritius | 2         |
      | America | New York  | -5        |

  Scenario: Authenticated user should be able to update the timezone records
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new timezone
      | name       | city      | gmtOffset |
      | Africa     | Mauritius | 2         |
      | Asia/India | Delhi     | 5         |
      | America    | New York  | -5        |
    When the user attempts to update the timezone with name: 'Asia/India' to
      | name       | city      | gmtOffset |
      | Asia/India | Bangalore | 7         |
    Then the response should be received with HTTP status code 200
    And the authenticated user attempts to read the timezones
    Then the response should be received with HTTP status code 200
    And the timezone read response contains the following timezones
      | name       | city      | gmtOffset |
      | Africa     | Mauritius | 2         |
      | Asia/India | Bangalore | 7         |
      | America    | New York  | -5        |

  Scenario: Authenticated user should be able to update single fields for the timezone records
    Given a user creates a new account and performs login with user name '<random>' and role 'USER'
    And the authenticated user attempts to create a new timezone
      | name       | city      | gmtOffset |
      | Africa     | Mauritius | 2         |
      | Asia/India | Delhi     | 5         |
      | America    | New York  | -5        |
    When the user attempts to update the timezone with name: 'Asia/India' to 'Asia/Bangladesh'
    Then the response should be received with HTTP status code 200
    And the authenticated user attempts to read the timezones
    Then the response should be received with HTTP status code 200
    And the timezone read response contains the following timezones
      | name            | city      | gmtOffset |
      | Africa          | Mauritius | 2         |
      | Asia/Bangladesh | Delhi     | 5         |
      | America         | New York  | -5        |
