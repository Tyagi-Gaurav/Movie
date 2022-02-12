Feature: Status functionality

  Scenario: Status endpoint should return success when system is running
    When the status endpoint is invoked
    Then the response should be received with HTTP status code 200
    And the response should be a success status response

  Scenario: Healthcheck endpoint should return success when movie is running
    When the healthcheck endpoint for movie is invoked
    Then the response should be received with HTTP status code 200
    And the response for movie healthcheck should be a success response

#  Scenario: Healthcheck endpoint should return success when user is running
#    When the healthcheck endpoint for user is invoked
#    Then the response should be received with HTTP status code 200
#    And the response for user healthcheck should be a success response