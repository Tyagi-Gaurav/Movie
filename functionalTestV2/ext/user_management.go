package ext

import (
	"encoding/json"
	"fmt"
	"net/http"
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

	var h = &WebClient{}
	return h.executePostWithHeaders(url, "application/vnd.user.add.v1+json", bodyAsString, headers)
}

func (testAccountRes TestUserManagementResource) DeleteUserAccount(
	userId string,
	pathResolver func(string) string) (*http.Response, error) {
	resourcePathWithQuery := fmt.Sprintf("%v?userId=%v", resourcePath, userId)
	url := pathResolver(resourcePathWithQuery)

	headers := make(map[string]string)

	headers["Authorization"] = "Bearer " + testAccountRes.Token

	var h = &WebClient{}
	return h.executeDeleteWithHeaders(url, "application/vnd.user.delete.v1+json", headers)
}
