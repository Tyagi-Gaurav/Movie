package ext

import (
	"encoding/json"
	"net/http"
)

type TestLoginRequestDTO struct {
	UserName string `json:"userName"`
	Password string `json:"password"`
}

type TestLoginResponseDTO struct {
	Token string `json:"token"`
	Id    string `json:"id"`
}

type TestLoginResource struct {
}

func (testLoginRes TestLoginResource) Login(pathResolver func(string) string,
	body TestLoginRequestDTO) (*http.Response, error) {
	url := pathResolver("/login")
	u, err := json.Marshal(body)

	if err != nil {
		panic(err)
	}

	bodyAsString := string(u)
	var h = &WebClient{}
	return h.executePost(url, "application/vnd.login.v1+json", bodyAsString)
}
