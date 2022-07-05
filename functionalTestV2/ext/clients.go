package ext

import (
	"encoding/json"
	"log"
	"net/http"
	"strings"
)

type WebClient struct {
}

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

func (h WebClient) ExecuteGet(url string) (*http.Response, error) {
	//log.Println("GET :", url)
	resp, err := http.Get(url)

	if err != nil {
		log.Fatalln("Failure occurred: ", err)
	}

	return resp, nil
}

func (h WebClient) executePost(url string, contentType string, body string) (*http.Response, error) {
	//log.Printf("POST %v, ContentType: %v, body: %v", url, contentType, body)
	resp, err := http.Post(url, contentType, strings.NewReader(body))

	if err != nil {
		log.Fatalln("Failure occurred: ", err)
	}

	return resp, nil
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

func (testLoginRes TestLoginResource) Login(url string, body TestLoginRequestDTO) (*http.Response, error) {
	u, err := json.Marshal(body)

	if err != nil {
		panic(err)
	}

	bodyAsString := string(u)
	var h = &WebClient{}
	return h.executePost(url, "application/vnd.login.v1+json", bodyAsString)
}
