Feature: Status functionality

  Scenario: Status endpoint should return success when system is running
    When the status endpoint is invoked
    Then the response should be received with HTTP status code 200
    And the response should be a success status response