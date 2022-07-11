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

	//the global admin user logs into the system
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
	loginResource.EnsureSuccessLogin(t, userName, password, appConfig.CreateUrlV2())
}

func TestAdminUsersShouldBeAbleToCreateOtherAdminUsers(t *testing.T) {
	appConfig := config.Configs["user"]
	loginResource := &ext.TestLoginResource{}

	//the global admin user logs into the system
	resp := loginResource.EnsureSuccessLogin(t, config.GlobalCredentials.UserName,
		config.GlobalCredentials.Password, appConfig.CreateUrlV2())

	bodyAsByte, _ := ioutil.ReadAll(resp.Body)
	loginResponse := &ext.TestLoginResponseDTO{}
	json.Unmarshal(bodyAsByte, loginResponse)

	defer resp.Body.Close()
	util.ExpectStatus(t, resp, 200)

	userName := util.RandomString(6)
	password := util.RandomString(6)

	//Create another user with random user name and 'ADMIN' role
	userMgtResource := &ext.TestUserManagementResource{Token: loginResponse.Token}
	accountCreateRequest := ext.AccountCreateWithRole(userName, password, "ADMIN")
	resp, err := userMgtResource.CreateAccountUsingAdminResource(appConfig.CreateUrlV2(), accountCreateRequest)
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 204)

	//Login with the new Admin user
	resp = loginResource.EnsureSuccessLogin(t, userName, password, appConfig.CreateUrlV2())

	bodyAsByte, _ = ioutil.ReadAll(resp.Body)
	loginResponse = &ext.TestLoginResponseDTO{}
	err = json.Unmarshal(bodyAsByte, loginResponse)
	util.PanicOnError(err)

	if loginResponse.Token == "" {
		panic("Expected token back in response")
	}
}

func TestAdminUsersShouldBeAbleToDeleteOtherUsers(t *testing.T) {
	appConfig := config.Configs["user"]
	loginResource := &ext.TestLoginResource{}

	//the global admin user logs into the system
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

	userName1 := util.RandomString(6)
	password1 := util.RandomString(6)

	//Create another user1 with random user name and 'USER' role
	userMgtResource := &ext.TestUserManagementResource{Token: loginResponse.Token}
	accountCreateRequest := ext.AccountCreateWithRole(userName1, password1, "USER")
	resp, err = userMgtResource.CreateAccountUsingAdminResource(appConfig.CreateUrlV2(), accountCreateRequest)
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 204)

	//Login with the new user1
	loginResponse1 := loginResource.EnsureSuccessLogin(t, userName1, password1, appConfig.CreateUrlV2())
	defer loginResponse1.Body.Close()

	//Create another user2 with random user name and 'USER' role
	userName2 := util.RandomString(6)
	password2 := util.RandomString(6)

	//Create another user1 with random user name and 'USER' role
	accountCreateRequest = ext.AccountCreateWithRole(userName2, password2, "USER")
	resp, err = userMgtResource.CreateAccountUsingAdminResource(appConfig.CreateUrlV2(), accountCreateRequest)
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 204)

	//Login with the new user2
	loginResponse2 := loginResource.EnsureSuccessLogin(t, userName2, password2, appConfig.CreateUrlV2())
	defer loginResponse2.Body.Close()

	//Global Admin User deletes user1
	loginResponseDTO1 := ext.ToLoginResponseDTO(loginResponse1) //Extract UserId from Login Response
	resp, err = userMgtResource.DeleteUserAccount(loginResponseDTO1.Id, appConfig.CreateUrlV2())
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 200)

	//Ensure that user1 cannot login now
	resp3, err := loginResource.LoginWith(t, userName1, password1, appConfig.CreateUrlV2())
	util.PanicOnError(err)
	util.ExpectStatus(t, resp3, 401)
}
