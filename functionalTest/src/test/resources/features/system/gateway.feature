Feature: Should not be able to access user and movie apps outside API gateway

  Scenario: Should not be able to create account by calling movie service directly
    Given a user attempts to create a new account with following details using movie service directly
      | firstName | lastName | userName | role | password |
      | bcssdf    | defdsfdf | <random> | USER | <random> |
    Then the response should be received with HTTP status code 404