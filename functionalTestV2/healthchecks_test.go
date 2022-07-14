package main

import (
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/Movie/functionalTest/util"
)

func TestHealthCheck(t *testing.T) {
	apps := []string{"contentUpload"}

	for _, app := range apps {
		appConfig := config.Configs[app]

		var h = &ext.WebClient{}
		resp, err := h.ExecuteGet(appConfig.HealthCheckUrl())
		util.PanicOnError(err)
		util.ExpectStatus(t, resp, 200)
	}
}

func TestStatus(t *testing.T) {
	apps := []string{"contentUpload"}

	for _, app := range apps {
		appConfig := config.Configs[app]

		var h = &ext.WebClient{}
		resp, err := h.ExecuteGet(appConfig.StatusUrl())
		util.PanicOnError(err)
		util.ExpectStatus(t, resp, 200)
	}
}
