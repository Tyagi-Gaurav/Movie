package main

import (
	"log"
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
)

func TestHealthCheck(t *testing.T) {
	apps := []string{"movie"}

	for _, app := range apps {
		appConfig := config.Configs[app]

		var h = &ext.WebClient{}
		resp, err := h.ExecuteGet(appConfig.HealthCheckUrl())

		if err != nil {
			log.Fatalln("Error Occurred: ", err)
		}

		if resp.StatusCode != 200 {
			log.Fatalf("Failed. expected: %d, actual: %d", 200, resp.StatusCode)
		}
	}
}
