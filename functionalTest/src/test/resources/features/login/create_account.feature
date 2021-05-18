Feature: Users should be able to create an account

  Scenario: User should be able to create a new account
    Given a user attempts to create a new account with following details
     | firstName | lastName | userName | role |
     | bc        | def      | <random> | USER |
    Then the response should be received with HTTP status code 204

#  Scenario: User should NOT be able to create a new account when another account with same username exists
#    Given a user intends to create a new account
#    When the user attempts to create a new account
#    Then a failure response is returned with HTTP status code 400

  Scenario: User should be able to login after creating a new account
    Given a user attempts to create a new account with following details
      | firstName | lastName | userName | role |
      | bc        | def      | <random> | USER |
    Then the response should be received with HTTP status code 204
    When the user attempts to login using the new credentials
    Then the response should be received with HTTP status code 200
    And the user login response contains an authorisation token

  Scenario: User should be not be allowed to login without a valid username and password
    Given a user attempts to create a new account with following details
      | firstName | lastName | userName | role |
      | bc        | def      | <random> | USER |
    Then the response should be received with HTTP status code 204
    When the user attempts to login using random password
    Then the response should be received with HTTP status code 401
