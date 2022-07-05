package ext

import (
	"encoding/json"
	"net/http"
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

func (testAccountRes TestAccountCreateResource) CreateAccount(url string, body TestAccountCreateRequestDTO) (*http.Response, error) {
	u, err := json.Marshal(body)

	if err != nil {
		panic(err)
	}

	bodyAsString := string(u)
	var h = &WebClient{}
	return h.executePost(url, "application/vnd+account.create.v1+json", bodyAsString)
}
