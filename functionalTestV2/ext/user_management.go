package ext

import (
	"encoding/json"
	"net/http"
)

type TestUserManagementResource struct {
	Token string
}

func (testAccountRes TestUserManagementResource) CreateAccountUsingAdminResource(
	pathResolver func(string) string,
	body TestAccountCreateRequestDTO) (*http.Response, error) {
	url := pathResolver("/manage")
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
