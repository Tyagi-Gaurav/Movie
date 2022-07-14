@Migrated
Feature: Metrics functionality

  Scenario: Content Upload metrics endpoint should return success when system is running
    When the content upload metrics endpoint is invoked
    Then the response should be received with HTTP status code 200
