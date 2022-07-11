package main

import (
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/Movie/functionalTest/util"
)

func TestAdminUsersShouldBeAbleToCreateOtherUsers(t *testing.T) {
	appConfig := config.Configs["user"]
	loginResource := &ext.TestLoginResource{}

	//the global admin user logs into the system
	resp, err := loginResource.LoginWith(t, config.GlobalCredentials.UserName, config.GlobalCredentials.Password, appConfig.CreateUrlV2())
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 200)

	globalLoginResponse := ext.ToLoginResponseDTO(resp)

	defer resp.Body.Close()
	//Initialise user management resource with token of global user
	userMgtResource := &ext.TestUserManagementResource{Token: globalLoginResponse.Token}

	//Create another user with random user name and 'USER' role
	userName, password := util.RandomString(6), util.RandomString(6)
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
	defer resp.Body.Close()
	util.ExpectStatus(t, resp, 200)

	globalLoginResponse := ext.ToLoginResponseDTO(resp)

	//Initialise user management resource with token of global user
	userMgtResource := &ext.TestUserManagementResource{Token: globalLoginResponse.Token}

	//Create another user with random user name and 'ADMIN' role
	userName, password := util.RandomString(6), util.RandomString(6)
	accountCreateRequest := ext.AccountCreateWithRole(userName, password, "ADMIN")
	resp, err := userMgtResource.CreateAccountUsingAdminResource(appConfig.CreateUrlV2(), accountCreateRequest)
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 204)

	//Login with the new Admin user
	resp = loginResource.EnsureSuccessLogin(t, userName, password, appConfig.CreateUrlV2())

	loginResponseDto := ext.ToLoginResponseDTO(resp)

	if loginResponseDto.Token == "" {
		panic("Expected token back in response")
	}
}

func TestAdminUsersShouldBeAbleToDeleteOtherUsers(t *testing.T) {
	appConfig := config.Configs["user"]
	loginResource := &ext.TestLoginResource{}

	//the global admin user logs into the system
	resp, err := loginResource.LoginWith(t, config.GlobalCredentials.UserName, config.GlobalCredentials.Password, appConfig.CreateUrlV2())
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 200)
	defer resp.Body.Close()

	globalLoginResponse := ext.ToLoginResponseDTO(resp)

	//Initialise user management resource with token of global user
	userMgtResource := &ext.TestUserManagementResource{Token: globalLoginResponse.Token}

	//Create another user1 with random user name and 'USER' role
	userName1, password1 := util.RandomString(6), util.RandomString(6)
	accountCreateRequest := ext.AccountCreateWithRole(userName1, password1, "USER")
	resp, err = userMgtResource.CreateAccountUsingAdminResource(appConfig.CreateUrlV2(), accountCreateRequest)
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 204)

	//Login with the new user1
	loginResponse1 := loginResource.EnsureSuccessLogin(t, userName1, password1, appConfig.CreateUrlV2())
	defer loginResponse1.Body.Close()

	//Create another user2 with random user name and 'USER' role
	userName2, password2 := util.RandomString(6), util.RandomString(6)
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

func TestAdminUsersShouldBeAbleToViewOtherUsers(t *testing.T) {
	appConfig := config.Configs["user"]
	loginResource := &ext.TestLoginResource{}

	//the global admin user logs into the system
	resp, err := loginResource.LoginWith(t, config.GlobalCredentials.UserName, config.GlobalCredentials.Password, appConfig.CreateUrlV2())
	util.PanicOnError(err)

	globalLoginResponse := ext.ToLoginResponseDTO(resp)

	defer resp.Body.Close()
	util.ExpectStatus(t, resp, 200)

	//Initialise user management resource with token of global user
	userMgtResource := &ext.TestUserManagementResource{Token: globalLoginResponse.Token}

	//Create another user1 with random user name and 'USER' role
	userName1, password1 := util.RandomString(6), util.RandomString(6)
	accountCreateRequest := ext.AccountCreateWithRole(userName1, password1, "USER")
	resp, err = userMgtResource.CreateAccountUsingAdminResource(appConfig.CreateUrlV2(), accountCreateRequest)
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 204)

	//Login with the new user1
	loginResponse1 := loginResource.EnsureSuccessLogin(t, userName1, password1, appConfig.CreateUrlV2())
	defer loginResponse1.Body.Close()

	//Create another user2 with random user name and 'USER' role
	userName2, password2 := util.RandomString(6), util.RandomString(6)

	//Create another user1 with random user name and 'USER' role
	accountCreateRequest = ext.AccountCreateWithRole(userName2, password2, "USER")
	resp, err = userMgtResource.CreateAccountUsingAdminResource(appConfig.CreateUrlV2(), accountCreateRequest)
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 204)

	//Login with the new user2
	loginResponse2 := loginResource.EnsureSuccessLogin(t, userName2, password2, appConfig.CreateUrlV2())
	defer loginResponse2.Body.Close()

	//Global Admin User retrieves a list of all users
	resp, err = userMgtResource.RetrieveListOfAllUsers(appConfig.CreateUrlV2())
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 200)
}
