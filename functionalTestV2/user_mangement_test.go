package main

import (
	"fmt"
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/Movie/functionalTest/util"
	"github.com/stretchr/testify/require"
)

func TestAdminUsersShouldBeAbleToCreateOtherUsers(t *testing.T) {
	appConfig := config.Configs["user"]
	loginResource := &ext.TestLoginResource{}

	loginRequest := ext.TestLoginRequestDTO{
		UserName: config.GlobalCredentials.UserName,
		Password: config.GlobalCredentials.Password,
	}

	resp, err := loginResource.Login(appConfig.CreateUrlV2(), loginRequest)
	util.PanicOnError(err)

	defer resp.Body.Close()

	expectedStatusCode := 200
	require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
		resp.StatusCode))

	// userName := util.RandomString(6)
	// password := util.RandomString(6)

	//Create another user with random user name and 'USER' role
	// accountCreateRequest := ext.TestAccountCreateRequestDTO{
	// 	UserName:    userName,
	// 	Password:    password,
	// 	FirstName:   "bcssdf",
	// 	LastName:    "defdsfdf",
	// 	DateOfBirth: "19/03/1972",
	// 	Gender:      "FEMALE",
	// 	HomeCountry: "AUS",
	// 	Role:        "USER",
	// }
}
