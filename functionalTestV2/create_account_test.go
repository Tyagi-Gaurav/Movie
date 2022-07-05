package main

import (
	"encoding/json"
	"fmt"
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/Movie/functionalTest/util"
	"github.com/stretchr/testify/require"
)

func TestUserShouldBeAbleToCreateNewAccount(t *testing.T) {
	gender := []string{"MALE", "FEMALE"}
	appConfig := config.Configs["user"]

	for _, data := range gender {
		var h = &ext.TestAccountCreateResource{}

		input := ext.TestAccountCreateRequestDTO{
			UserName:    util.RandomString(6),
			Password:    util.RandomString(6),
			FirstName:   "bcssdf",
			LastName:    "defdsfdf",
			DateOfBirth: "19/03/1972",
			Gender:      data,
			HomeCountry: "AUS",
			Role:        "USER",
		}

		resp, err := h.CreateAccount(appConfig.CreateUrl("/account/create"), input)

		if err != nil {
			panic(err)
		}
		expectedStatusCode := 204

		require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
			resp.StatusCode))
	}
}

func TestDuplicateUserAccountShouldReturnError(t *testing.T) {
	appConfig := config.Configs["user"]
	var h = &ext.TestAccountCreateResource{}

	input := ext.TestAccountCreateRequestDTO{
		UserName:    util.RandomString(6),
		Password:    util.RandomString(6),
		FirstName:   "bcssdf",
		LastName:    "defdsfdf",
		DateOfBirth: "19/03/1972",
		Gender:      "FEMALE",
		HomeCountry: "AUS",
		Role:        "USER",
	}

	resp, err := h.CreateAccount(appConfig.CreateUrl("/account/create"), input)

	if err != nil {
		panic(err)
	}
	expectedStatusCode := 204

	require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
		resp.StatusCode))

	//Creating the account again should fail with 403
	resp, err = h.CreateAccount(appConfig.CreateUrl("/account/create"), input)

	if err != nil {
		panic(err)
	}
	expectedStatusCode = 403

	require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
		resp.StatusCode))
}

func TestLoginAfterSuccessfulAccountCreation(t *testing.T) {
	appConfig := config.Configs["user"]
	acctResource := &ext.TestAccountCreateResource{}
	loginResource := &ext.TestLoginResource{}
	userName := util.RandomString(6)
	password := util.RandomString(6)

	accountCreateRequest := ext.TestAccountCreateRequestDTO{
		UserName:    userName,
		Password:    password,
		FirstName:   "bcssdf",
		LastName:    "defdsfdf",
		DateOfBirth: "19/03/1972",
		Gender:      "FEMALE",
		HomeCountry: "AUS",
		Role:        "USER",
	}

	resp, err := acctResource.CreateAccount(appConfig.CreateUrl("/account/create"), accountCreateRequest)

	if err != nil {
		panic(err)
	}
	expectedStatusCode := 204
	require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
		resp.StatusCode))

	loginRequest := ext.TestLoginRequestDTO{
		UserName: userName,
		Password: password,
	}

	resp, err = loginResource.Login(appConfig.CreateUrl("/login"), loginRequest)

	if err != nil {
		panic(err)
	}

	defer resp.Body.Close()

	expectedStatusCode = 200
	require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
		resp.StatusCode))

	loginResponse := &ext.TestLoginResponseDTO{}
	err = json.NewDecoder(resp.Body).Decode(loginResponse)

	if err != nil {
		panic(err)
	}

	require.True(t, len(loginResponse.Token) > 0, "Expected login response token")
}
