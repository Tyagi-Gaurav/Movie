package main

import (
	"encoding/json"
	"io/ioutil"
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/Movie/functionalTest/util"
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

	bodyAsByte, _ := ioutil.ReadAll(resp.Body)
	loginResponse := &ext.TestLoginResponseDTO{}
	json.Unmarshal(bodyAsByte, loginResponse)

	defer resp.Body.Close()
	util.ExpectStatus(t, resp, 200)

	userName := util.RandomString(6)
	password := util.RandomString(6)

	//Create another user with random user name and 'USER' role
	userMgtResource := &ext.TestUserManagementResource{Token: loginResponse.Token}
	accountCreateRequest := ext.AccountCreateWith(userName, password, "MALE")
	resp, err = userMgtResource.CreateAccountUsingAdminResource(appConfig.CreateUrlV2(), accountCreateRequest)
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 204)

	//Login with the new user
	loginRequest = ext.TestLoginRequestDTO{
		UserName: userName,
		Password: password,
	}

	resp, err = loginResource.Login(appConfig.CreateUrlV2(), loginRequest)
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 200)
}
