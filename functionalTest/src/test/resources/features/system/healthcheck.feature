Feature: HealthCheck and Status functionality

  Scenario: Status endpoint should return success when system is running
    When the status endpoint is invoked
    Then the response should be received with HTTP status code 200
    And the response should be a success status response

#  Scenario: Healthcheck endpoint should return success when all system dependencies are up and running
#    When the healthcheck endpoint is invoked
#    Then the response should be received with HTTP status code 200
#    And the response should contain status for the following application dependencies
#      | Database    |
#      | Application |