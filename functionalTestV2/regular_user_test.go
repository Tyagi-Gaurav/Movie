package main

import (
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/Movie/functionalTest/util"
)

func TestUserShouldNotBeAbleToCreateMoviesWithoutAuthHeader(t *testing.T) {
	userConfig := config.Configs["user"]
	contentUploadConfig := config.Configs["contentUpload"]
	acctResource := ext.TestAccountCreateResource{}
	loginResource := ext.TestLoginResource{}

	//A user creates a new account and performs login with user name '<random>' and role 'USER'
	userName, password := util.RandomString(6), util.RandomString(6)
	accountCreateRequest := ext.AccountCreateWith(userName, password, "FEMALE")

	resp, err := acctResource.CreateAccount(userConfig.CreateUrlV2(), accountCreateRequest)
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 204)

	loginResponse := loginResource.EnsureSuccessLogin(t, userName, password, userConfig.CreateUrlV2())
	defer resp.Body.Close()

	loginRespDto := ext.ToLoginResponseDTO(loginResponse)
	contentUploadResource := ext.TestContentUploadResource{Token: loginRespDto.Token}

	//the authenticated user attempts to create a new movie without auth header
	uploadMovieResp, err := contentUploadResource.UploadMovie(contentUploadConfig.CreateUrlV2(),
		func(header map[string]string) map[string]string {
			delete(header, "Authorization")
			return header
		})
	util.PanicOnError(err)
	util.ExpectStatus(t, uploadMovieResp, 401)
}

func TestUserShouldNotBeAbleToCreateDuplicateMovies(t *testing.T) {
	userConfig := config.Configs["user"]
	contentUploadConfig := config.Configs["contentUpload"]
	acctResource := ext.TestAccountCreateResource{}
	loginResource := ext.TestLoginResource{}

	//A user creates a new account and performs login with user name '<random>' and role 'USER'
	userName, password := util.RandomString(6), util.RandomString(6)
	accountCreateRequest := ext.AccountCreateWith(userName, password, "FEMALE")

	resp, err := acctResource.CreateAccount(userConfig.CreateUrlV2(), accountCreateRequest)
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 204)

	loginResponse := loginResource.EnsureSuccessLogin(t, userName, password, userConfig.CreateUrlV2())
	defer resp.Body.Close()

	loginRespDto := ext.ToLoginResponseDTO(loginResponse)
	contentUploadResource := ext.TestContentUploadResource{Token: loginRespDto.Token}

	//the authenticated user attempts to create a new movie
	uploadMovieResp, err := contentUploadResource.UploadMovie(contentUploadConfig.CreateUrlV2(), nil)
	util.PanicOnError(err)
	util.ExpectStatus(t, uploadMovieResp, 200)

	//the authenticated user attempts to create the same movie again
	uploadMovieResp, err = contentUploadResource.UploadMovie(contentUploadConfig.CreateUrlV2(), nil)
	util.PanicOnError(err)
	util.ExpectStatus(t, uploadMovieResp, 403)
}
