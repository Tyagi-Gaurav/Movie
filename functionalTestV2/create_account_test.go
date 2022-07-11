package main

import (
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
		util.ExpectStatus(t, resp, 204)
	}
}

func TestDuplicateUserAccountShouldReturnError(t *testing.T) {
	appConfig := config.Configs["user"]
	var h = &ext.TestAccountCreateResource{}

	input := ext.AccountCreateWith(util.RandomString(6), util.RandomString(6), "FEMALE")

	resp, err := h.CreateAccount(appConfig.CreateUrlV2(), input)

	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 204)

	//Creating the account again should fail with 403
	resp, err = h.CreateAccount(appConfig.CreateUrlV2(), input)

	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 403)
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

	util.ExpectStatus(t, resp, 204)

	resp = loginResource.EnsureSuccessLogin(t, userName, password, appConfig.CreateUrlV2())
	defer resp.Body.Close()

	loginResponseDto := ext.ToLoginResponseDTO(resp)
	require.True(t, len(loginResponseDto.Token) > 0, "Expected login response token")
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
	util.ExpectStatus(t, resp, 204)

	resp, err = loginResource.LoginWith(t, userName, "randomPassword", appConfig.CreateUrlV2())
	util.PanicOnError(err)
	defer resp.Body.Close()
	util.ExpectStatus(t, resp, 401)
}
