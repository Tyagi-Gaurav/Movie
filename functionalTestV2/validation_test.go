package main

import (
	"fmt"
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/stretchr/testify/require"
)

func TestValidationCreateAccount(t *testing.T) {
	type data struct {
		username    string
		password    string
		role        string
		dob         string
		homeCountry string
	}

	testData := []data{
		{"Usr", "ddfdsffdf", "USER", "19/03/1972", "IND"},
		{"gdfgjszghjghsgdgsjdgj", "ddfdsffdf", "USER", "19/03/1972", "AUS"},
		{"user", "hfhdsfhdsfkhjhsdfkddd345345", "USER", "19/03/1972", "AUT"},
		{"user", "hsyy", "USER", "19/03/1972", "BWA"},
		{"user", "user123", "user", "19/03/1972", "BEL"},
		{"user", "user123", "OTHER", "19/03/1972", "CAD"},
		{"user", "dsfdsf", "USER", "1/03/1972", "SRI"},
		{"user", "eeet", "USER", "01/3/1972", "PAK"},
		{"user", "rtert", "USER", "01/3/66", "BAN"},
		{"user", "rtryy", "USER", "01/03/1966", "IN"},
	}

	appConfig := config.Configs["user"]

	for _, test := range testData {
		var h = &ext.TestAccountCreateResource{}

		input := ext.TestAccountCreateRequestDTO{
			UserName:    test.username,
			Password:    test.password,
			FirstName:   "bcdsf",
			LastName:    "def",
			DateOfBirth: test.dob,
			Gender:      "FEMALE",
			HomeCountry: test.homeCountry,
			Role:        test.role,
		}

		resp, err := h.CreateAccount(appConfig.CreateUrlV2(), input)

		if err != nil {
			t.Error("Error Occurred: ", err)
		}
		expectedStatusCode := 400

		require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
			resp.StatusCode))
	}
}
