@Migrated
Feature: Validating inputs

  Scenario Outline: Return 400 status code when input does not satisfy constraints
    Given a user attempts to create a new account with following details
      | firstName | lastName | userName   | role   | password   | dateOfBirth | gender | homeCountry   |
      | bcdsf     | def      | <username> | <role> | <password> | <dob>       | FEMALE | <homeCountry> |
    Then the response should be received with HTTP status code 400
    Examples:
      | username              | password                    | role  | dob        | homeCountry |
      | Usr                   | ddfdsffdf                   | USER  | 19/03/1972 | IND         |
      | gdfgjszghjghsgdgsjdgj | ddfdsffdf                   | USER  | 19/03/1972 | AUS         |
      | user                  | hfhdsfhdsfkhjhsdfkddd345345 | USER  | 19/03/1972 | AUT         |
      | user                  | hsyy                        | USER  | 19/03/1972 | BWA         |
      | user                  | user123                     | user  | 19/03/1972 | BEL         |
      | user                  | user123                     | OTHER | 19/03/1972 | CAD         |
      | user                  | dsfdsf                      | USER  | 1/03/1972  | SRI         |
      | user                  | eeet                        | USER  | 01/3/1972  | PAK         |
      | user                  | rtert                       | USER  | 01/03/66   | BAN         |
      | user                  | rtryy                       | USER  | 01/03/1966 | IN          |