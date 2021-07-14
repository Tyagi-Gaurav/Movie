Feature: Logged in user should be able to upload movie with video

  #Scenario: Upload a video against existing movie
  Scenario: Authenticated user should be able to upload a video stream against a movie
    #Given a user creates a new account and performs login with user name '<random>' and role 'USER'
#    And the authenticated user attempts to create a new movie
#      | name        | yearProduced | rating | video           |
#      | First Blood | 2000         | 7.8    | sample_test.mp4 |
#    And the response should be received with HTTP status code 204
#    When the admin user attempts to update the movie with name: 'First Blood' to
    When the authenticated user attempts to upload a new video "sample_test.mp4" for movie: 'First Blood'
    Then the response should be received with HTTP status code 204

  #Scenario: Upload a movie along with movie metadata.
  #Scenario: Sequence a video.
  #Scenario: Delete a particular video.
