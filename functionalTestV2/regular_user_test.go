package main

import (
	"fmt"
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/Movie/functionalTest/util"
	"github.com/google/uuid"
	"github.com/stretchr/testify/require"
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
	uploadMovieResp, err := contentUploadResource.UploadMovie(ext.DefaultContent, contentUploadConfig.CreateUrlV2(),
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
	uploadMovieResp, err := contentUploadResource.UploadMovie(ext.DefaultContent, contentUploadConfig.CreateUrlV2(), nil)
	util.PanicOnError(err)
	util.ExpectStatus(t, uploadMovieResp, 200)

	//the authenticated user attempts to create the same movie again
	uploadMovieResp, err = contentUploadResource.UploadMovie(ext.DefaultContent, contentUploadConfig.CreateUrlV2(), nil)
	util.PanicOnError(err)
	util.ExpectStatus(t, uploadMovieResp, 403)
}

func TestAuthenticatedUserShouldBeAbleToReadMovieRecords(t *testing.T) {
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

	//the authenticated user attempts to create a new movies
	contents := []ext.TestContentUploadRequestDTO{
		{Name: "First Blood", YearProduced: 1990, Rating: 7.8, Genre: "ACTION", ContentType: "MOVIE", AgeRating: "12A", IsShareable: true},
		{Name: "Die Hard", YearProduced: 1980, Rating: 8.9, Genre: "ACTION", ContentType: "MOVIE", AgeRating: "12A", IsShareable: true},
		{Name: "The President", YearProduced: 2001, Rating: 6.3, Genre: "ROMANCE", ContentType: "MOVIE", AgeRating: "15", IsShareable: false},
	}

	err = contentUploadResource.UploadMovies(contents,
		contentUploadConfig.CreateUrlV2(), nil)
	util.PanicOnError(err)

	//the authenticated user attempts to read the movies
	readMoviesResp, err := contentUploadResource.ReadMovies(contentUploadConfig.CreateUrlV2())
	util.PanicOnError(err)
	util.ExpectStatus(t, readMoviesResp, 200)

	//Check response matches contents
	movies := ext.ToMovieReadDTO(readMoviesResp)
	require.ElementsMatch(t, contents, ToMovies(movies.Movies))
}

func TestAuthenticatedUserShouldBeAbleToDeleteMovieRecords(t *testing.T) {
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

	//the authenticated user attempts to create a new movies
	contents := []ext.TestContentUploadRequestDTO{
		{Name: "First Blood", YearProduced: 1990, Rating: 7.8, Genre: "ACTION", ContentType: "MOVIE", AgeRating: "12A", IsShareable: true},
		{Name: "Die Hard", YearProduced: 1980, Rating: 8.9, Genre: "ACTION", ContentType: "MOVIE", AgeRating: "12A", IsShareable: true},
		{Name: "The President", YearProduced: 2001, Rating: 6.3, Genre: "ROMANCE", ContentType: "MOVIE", AgeRating: "15", IsShareable: false},
	}

	err = contentUploadResource.UploadMovies(contents,
		contentUploadConfig.CreateUrlV2(), nil)
	util.PanicOnError(err)

	//the user attempts to delete the movie with name: 'First Blood' , yearProduced: '1990' and rating: '7.8'
	movieId := getMovieWithDetails(contentUploadResource, contentUploadConfig.CreateUrlV2(), "First Blood", 1990, 7.8)

	deleteMovieResp, err := contentUploadResource.DeleteMovie(contentUploadConfig.CreateUrlV2(), movieId)
	util.PanicOnError(err)
	util.ExpectStatus(t, deleteMovieResp, 200)

	//the authenticated user attempts to read the movies
	readMoviesResp, err := contentUploadResource.ReadMovies(contentUploadConfig.CreateUrlV2())
	util.PanicOnError(err)
	util.ExpectStatus(t, readMoviesResp, 200)

	//Check response matches contents
	movies := ext.ToMovieReadDTO(readMoviesResp)
	require.ElementsMatch(t, contents[1:], ToMovies(movies.Movies))
}

func TestAuthenticatedUserShouldBeAbleToUpdateeMovieRecords(t *testing.T) {
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

	//the authenticated user attempts to create a new movies
	contents := []ext.TestContentUploadRequestDTO{
		{Name: "First Blood", YearProduced: 1990, Rating: 7.8, Genre: "ACTION", ContentType: "MOVIE", AgeRating: "12A", IsShareable: true},
		{Name: "Die Hard", YearProduced: 1980, Rating: 8.9, Genre: "ACTION", ContentType: "MOVIE", AgeRating: "12A", IsShareable: true},
		{Name: "The President", YearProduced: 2001, Rating: 6.3, Genre: "ROMANCE", ContentType: "MOVIE", AgeRating: "15", IsShareable: false},
	}

	err = contentUploadResource.UploadMovies(contents,
		contentUploadConfig.CreateUrlV2(), nil)
	util.PanicOnError(err)

	//the user attempts to delete the movie with name: 'First Blood' , yearProduced: '1990' and rating: '7.8'
	movieId := getMovieWithDetails(contentUploadResource, contentUploadConfig.CreateUrlV2(), "First Blood", 1990, 7.8)

	deleteMovieResp, err := contentUploadResource.DeleteMovie(contentUploadConfig.CreateUrlV2(), movieId)
	util.PanicOnError(err)
	util.ExpectStatus(t, deleteMovieResp, 200)

	//the authenticated user attempts to read the movies
	readMoviesResp, err := contentUploadResource.ReadMovies(contentUploadConfig.CreateUrlV2())
	util.PanicOnError(err)
	util.ExpectStatus(t, readMoviesResp, 200)

	//Check response matches contents
	movies := ext.ToMovieReadDTO(readMoviesResp)
	require.ElementsMatch(t, contents[1:], ToMovies(movies.Movies))
}

func ToMovies(contents []ext.TestMovieDetailDTO) []ext.TestContentUploadRequestDTO {
	movieRequestDetails := []ext.TestContentUploadRequestDTO{}
	for _, content := range contents {
		movieDetail := ext.TestContentUploadRequestDTO{
			Name:         content.Name,
			YearProduced: content.YearProduced,
			Rating:       content.Rating,
			Genre:        content.Genre,
			ContentType:  content.ContentType,
			AgeRating:    content.AgeRating,
			IsShareable:  content.IsShareable,
		}
		movieRequestDetails = append(movieRequestDetails, movieDetail)
	}
	return movieRequestDetails
}

func getMovieWithDetails(res ext.TestContentUploadResource,
	urlResolver util.URLResolver,
	movieName string, yearProduced int, rating float32) uuid.UUID {

	readMoviesResp, err := res.ReadMovies(urlResolver)
	util.PanicOnError(err)

	movies := ext.ToMovieReadDTO(readMoviesResp)

	for _, movie := range movies.Movies {
		if movie.Name == movieName && movie.Rating == rating && movie.YearProduced == yearProduced {
			return movie.Id
		}
	}

	panic(fmt.Sprintf("No movie found for %v, %v, %v ", movieName, yearProduced, rating))
}
