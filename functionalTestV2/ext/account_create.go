package ext

import (
	"encoding/json"
	"net/http"

	"github.com/Movie/functionalTest/util"
)

type TestAccountCreateRequestDTO struct {
	UserName    string `json:"userName"`
	Password    string `json:"password"`
	FirstName   string `json:"firstName"`
	LastName    string `json:"lastName"`
	DateOfBirth string `json:"dateOfBirth"`
	Gender      string `json:"gender"`
	HomeCountry string `json:"homeCountry"`
	Role        string `json:"role"`
}

type TestAccountCreateResource struct {
}

func AccountCreateWith(userName string, password string, gender string) TestAccountCreateRequestDTO {
	return TestAccountCreateRequestDTO{
		UserName:    userName,
		Password:    password,
		FirstName:   util.RandomString(6),
		LastName:    util.RandomString(8),
		DateOfBirth: "19/03/1972",
		Gender:      gender,
		HomeCountry: "AUS",
		Role:        "USER",
	}
}

func (testAccountRes TestAccountCreateResource) CreateAccount(
	pathResolver func(string) string,
	body TestAccountCreateRequestDTO) (*http.Response, error) {
	url := pathResolver("/account/create")
	u, err := json.Marshal(body)

	if err != nil {
		panic(err)
	}

	bodyAsString := string(u)
	var h = &WebClient{}
	return h.executePost(url, "application/vnd+account.create.v1+json", bodyAsString)
}
