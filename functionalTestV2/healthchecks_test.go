package main

import (
	"fmt"
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/Movie/functionalTest/util"
	"github.com/stretchr/testify/require"
)

func TestHealthCheck(t *testing.T) {
	apps := []string{"contentUpload"}

	for _, app := range apps {
		appConfig := config.Configs[app]

		var h = &ext.WebClient{}
		resp, err := h.ExecuteGet(appConfig.HealthCheckUrl())
		util.PanicOnError(err)

		require.Equal(t, 200, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", 200, resp.StatusCode))
	}
}

func TestStatus(t *testing.T) {
	apps := []string{"contentUpload"}

	for _, app := range apps {
		appConfig := config.Configs[app]

		var h = &ext.WebClient{}
		resp, err := h.ExecuteGet(appConfig.StatusUrl())
		util.PanicOnError(err)

		require.Equal(t, 200, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", 200, resp.StatusCode))
	}
}
