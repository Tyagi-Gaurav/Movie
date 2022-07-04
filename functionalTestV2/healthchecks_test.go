package main

import (
	"fmt"
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/stretchr/testify/require"
)

func TestHealthCheck(t *testing.T) {
	apps := []string{"movie"}

	for _, app := range apps {
		appConfig := config.Configs[app]

		var h = &ext.WebClient{}
		resp, err := h.ExecuteGet(appConfig.HealthCheckUrl())

		if err != nil {
			t.Error("Error Occurred: ", err)
		}

		require.Equal(t, 200, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", 200, resp.StatusCode))
	}
}

func TestStatus(t *testing.T) {
	apps := []string{"movie"}

	for _, app := range apps {
		appConfig := config.Configs[app]

		var h = &ext.WebClient{}
		resp, err := h.ExecuteGet(appConfig.StatusUrl())

		if err != nil {
			t.Error("Error Occurred: ", err)
		}

		require.Equal(t, 200, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", 200, resp.StatusCode))
	}
}
