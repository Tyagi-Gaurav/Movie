package ext

import (
	"encoding/json"
	"io/ioutil"
	"net/http"
	"testing"

	"github.com/Movie/functionalTest/util"
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

func ToLoginResponseDTO(resp *http.Response) *TestLoginResponseDTO {
	defer resp.Body.Close()

	bytes, err := ioutil.ReadAll(resp.Body)
	util.PanicOnError(err)
	testLoginResponseDTO := &TestLoginResponseDTO{}
	json.Unmarshal(bytes, testLoginResponseDTO)
	return testLoginResponseDTO
}

func (testLoginRes TestLoginResource) login(pathResolver func(string) string,
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

func (testLoginRes TestLoginResource) LoginWith(t *testing.T,
	userName string, password string,
	pathResolver func(string) string) (*http.Response, error) {
	loginRequest := TestLoginRequestDTO{
		UserName: userName,
		Password: password,
	}

	return testLoginRes.login(pathResolver, loginRequest)
}

func (testLoginRes TestLoginResource) EnsureSuccessLogin(
	t *testing.T,
	userName string, password string,
	pathResolver func(string) string) *http.Response {
	loginRequest := TestLoginRequestDTO{
		UserName: userName,
		Password: password,
	}

	resp, err := testLoginRes.login(pathResolver, loginRequest)

	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 200)

	return resp
}
