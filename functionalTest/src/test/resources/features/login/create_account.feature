Feature: Users should be able to create an account

  Scenario Outline: User should be able to create a new account
    Given a user attempts to create a new account with following details
      | firstName | lastName | userName | role | password | dateOfBirth | gender        | homeCountry |
      | bcssdf    | defdsfdf | <random> | USER | <random> | 19/03/1972  | <GenderValue> | AUS         |
    Then the response should be received with HTTP status code 204
    Examples:
      | GenderValue |
      | FEMALE      |
      | MALE        |

  Scenario: User should NOT be able to create a new account when another account with same username exists
    Given a user attempts to create a new account with following details
      | firstName | lastName | userName | role | password | dateOfBirth | gender | homeCountry |
      | bcdsfdsf  | ssdfdef  | <random> | USER | <random> | 19/03/1972  | FEMALE | AUS         |
    And the response should be received with HTTP status code 204
    And the userName is captured
    Given a user attempts to create a new account with following details
      | firstName | lastName | userName   | role | password | dateOfBirth | gender | homeCountry |
      | bcsdf     | desdff   | <captured> | USER | <random> | 19/03/1972  | FEMALE | AUS         |
    Then the response should be received with HTTP status code 403

  Scenario Outline: User should be able to login after creating a new account
    Given a user attempts to create a new account with following details
      | firstName | lastName | userName | role | password | dateOfBirth | gender        | homeCountry |
      | bcsdf     | defdsf   | <random> | USER | <random> | 19/03/1972  | <GenderValue> | AUS         |
    And the response should be received with HTTP status code 204
    When the user attempts to login using the new credentials
    Then the response should be received with HTTP status code 200
    And the user login response contains an authorisation token
    Examples:
      | GenderValue |
      | FEMALE      |
      | MALE        |

  Scenario: User should be not be allowed to login without a valid username and password
    Given a user attempts to create a new account with following details
      | firstName | lastName | userName | role | password | dateOfBirth | gender | homeCountry |
      | dsfdsf    | sdfsdf   | <random> | USER | <random> | 19/03/1972  | FEMALE | AUS         |
    And the response should be received with HTTP status code 204
    When the user attempts to login using random password
    Then the response should be received with HTTP status code 401
