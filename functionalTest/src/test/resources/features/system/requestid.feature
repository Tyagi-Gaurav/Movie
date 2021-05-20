Feature: Should generate request Id for each request

  Scenario: Should generate request ID and return it in response
    When the status endpoint is invoked
    Then the response should be received with HTTP status code 200
    And the response contains the requestId header in response
