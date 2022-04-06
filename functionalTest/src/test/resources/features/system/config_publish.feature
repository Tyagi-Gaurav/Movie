Feature: Configuration Publish functionality

  Scenario: Configuration should be published from Content Upload application
    When the configuration is published for content upload application
    Then the response should be received with HTTP status code 200
    And any sensitive data should be protected

  Scenario: Configuration should be published from Users application
    When the configuration is published for user application
    Then the response should be received with HTTP status code 200
    And any sensitive data should be protected
    