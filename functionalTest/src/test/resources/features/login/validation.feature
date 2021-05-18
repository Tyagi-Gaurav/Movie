Feature: Validating inputs

  Scenario Outline: Return 400 status code when input does not satisfy constraints
    Given a user attempts to create a new account with following details
      | firstName | lastName | userName   | role   | password   |
      | bc        | def      | <username> | <role> | <password> |
    Then the response should be received with HTTP status code 400
    Examples:
      | username              | password                    | role  |
      | Usr                   | ddfdsffdf                   | USER  |
      | gdfgjszghjghsgdgsjdgj | ddfdsffdf                   | USER  |
      | user                  | hfhdsfhdsfkhjhsdfkddd345345 | USER  |
      | user                  | hsyy                        | USER  |
      | user                  | user123                     | user  |
      | user                  | user123                     | OTHER |