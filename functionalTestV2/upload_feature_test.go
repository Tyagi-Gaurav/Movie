package main

import (
	"os"
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/Movie/functionalTest/util"
	"github.com/stretchr/testify/require"
)

func TestUploadNewVideoForMovie(t *testing.T) {
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
	uploadMovieResp, err := contentUploadResource.UploadMovie(ext.DefaultContent, contentUploadConfig.CreateUrlV2(), nil)
	util.PanicOnError(err)
	util.ExpectStatus(t, uploadMovieResp, 200)
	uploadResponseDTO := ext.ToUploadResponseDTO(uploadMovieResp)

	//the user attempts to upload video for the movie - '/data/SmallVideo.ts'
	fileName := "data/SmallVideo.ts"
	data, err := os.ReadFile(fileName)
	util.PanicOnError(err)

	videoUploadResp, err := contentUploadResource.UploadVideoForMovie(uploadResponseDTO.MovieId, fileName, data,
		contentUploadConfig.CreateUrlV2())

	//And the response should be received with HTTP status code 200
	util.PanicOnError(err)
	util.ExpectStatus(t, videoUploadResp, 200)

	//And the size of video returned should be 1769472
	videoUploadResponseDTO := ext.ToVideoUploadResponseDTO(videoUploadResp)
	require.Equal(t, 1744264, videoUploadResponseDTO.Size, "Expected Video Upload size does not match")
}
