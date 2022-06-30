package main

import (
	"fmt"
	"log"
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
)

func TestHealthCheck(t *testing.T) {
	appConfig := config.Configs["AppA"]

	var h = &ext.WebClient{}
	url := fmt.Sprintf("%v://%v:%d/api/healthcheck", appConfig.Scheme, appConfig.Host, appConfig.Port)
	log.Println("Calling url", url)
	resp, err := h.ExecuteGet(url)

	if err != nil {
		log.Fatalln("Error Occurred: ", err)
	}

	if resp.StatusCode != 200 {
		log.Fatalf("Failed. expected: %d, actual: %d", 200, resp.StatusCode)
	}

	fmt.Println(resp)
}
