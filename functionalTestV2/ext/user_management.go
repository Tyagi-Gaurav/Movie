package ext

import (
	"encoding/json"
	"fmt"
	"net/http"

	"github.com/Movie/functionalTest/util"
)

var resourcePath = "/manage"

type TestUserManagementResource struct {
	Token string
}

func (testAccountRes TestUserManagementResource) CreateAccountUsingAdminResource(
	pathResolver func(string) string,
	body TestAccountCreateRequestDTO) (*http.Response, error) {
	url := pathResolver(resourcePath)
	u, err := json.Marshal(body)

	if err != nil {
		panic(err)
	}

	bodyAsString := string(u)
	headers := make(map[string]string)

	headers["Authorization"] = "Bearer " + testAccountRes.Token
	headers["Content-Type"] = "application/vnd.user.add.v1+json"

	var h = &WebClient{}
	return h.executePostWithHeaders(url, bodyAsString, headers)
}

func (testAccountRes TestUserManagementResource) DeleteUserAccount(
	userId string,
	pathResolver func(string) string) (*http.Response, error) {
	resourcePathWithQuery := fmt.Sprintf("%v?userId=%v", resourcePath, userId)
	url := pathResolver(resourcePathWithQuery)

	headers := make(map[string]string)

	headers["Authorization"] = "Bearer " + testAccountRes.Token
	headers["Content-Type"] = "application/vnd.user.delete.v1+json"

	var h = &WebClient{}
	return h.executeDeleteWithHeaders(url, headers)
}

func (testAccountRes TestUserManagementResource) RetrieveListOfAllUsers(urlResolver util.URLResolver) (*http.Response, error) {
	fullURL := urlResolver(resourcePath)

	headers := make(map[string]string)

	headers["Authorization"] = "Bearer " + testAccountRes.Token
	headers["Content-Type"] = "application/vnd.user.read.v1+json"
	headers["Accept"] = "application/vnd.user.read.v1+json"

	var h = &WebClient{}
	return h.executeGetWithHeaders(fullURL, headers)
}
