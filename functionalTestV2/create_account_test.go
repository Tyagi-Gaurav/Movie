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

		input := ext.AccountCreateWith(util.RandomString(6), util.RandomString(6), data)

		resp, err := h.CreateAccount(appConfig.CreateUrlV2(), input)

		util.PanicOnError(err)
		expectedStatusCode := 204

		require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
			resp.StatusCode))
	}
}

func TestDuplicateUserAccountShouldReturnError(t *testing.T) {
	appConfig := config.Configs["user"]
	var h = &ext.TestAccountCreateResource{}

	input := ext.AccountCreateWith(util.RandomString(6), util.RandomString(6), "FEMALE")

	resp, err := h.CreateAccount(appConfig.CreateUrlV2(), input)

	util.PanicOnError(err)
	expectedStatusCode := 204

	require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
		resp.StatusCode))

	//Creating the account again should fail with 403
	resp, err = h.CreateAccount(appConfig.CreateUrlV2(), input)

	util.PanicOnError(err)
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

	accountCreateRequest := ext.AccountCreateWith(userName, password, "FEMALE")

	resp, err := acctResource.CreateAccount(appConfig.CreateUrlV2(), accountCreateRequest)
	util.PanicOnError(err)

	expectedStatusCode := 204
	require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
		resp.StatusCode))

	loginRequest := ext.TestLoginRequestDTO{
		UserName: userName,
		Password: password,
	}

	resp, err = loginResource.Login(appConfig.CreateUrlV2(), loginRequest)
	util.PanicOnError(err)

	defer resp.Body.Close()

	expectedStatusCode = 200
	require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
		resp.StatusCode))

	loginResponse := &ext.TestLoginResponseDTO{}
	err = json.NewDecoder(resp.Body).Decode(loginResponse)
	util.PanicOnError(err)

	require.True(t, len(loginResponse.Token) > 0, "Expected login response token")
}

func TestUserShouldNotBeAbleToLoginWithoutValidUserNamePassword(t *testing.T) {
	appConfig := config.Configs["user"]
	acctResource := &ext.TestAccountCreateResource{}
	loginResource := &ext.TestLoginResource{}
	userName := util.RandomString(6)
	password := util.RandomString(6)

	accountCreateRequest := ext.AccountCreateWith(userName, password, "FEMALE")

	resp, err := acctResource.CreateAccount(appConfig.CreateUrlV2(), accountCreateRequest)
	util.PanicOnError(err)
	expectedStatusCode := 204
	require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
		resp.StatusCode))

	loginRequest := ext.TestLoginRequestDTO{
		UserName: userName,
		Password: "randomPassword",
	}

	resp, err = loginResource.Login(appConfig.CreateUrlV2(), loginRequest)
	util.PanicOnError(err)

	defer resp.Body.Close()

	expectedStatusCode = 401
	require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
		resp.StatusCode))
}
