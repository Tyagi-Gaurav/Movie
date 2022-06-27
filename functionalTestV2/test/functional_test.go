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

	var h = &ext.HealthCheck{}
	url := fmt.Sprintf("%v://%v:%d/api/healthcheck", appConfig.Scheme, appConfig.Host, appConfig.Port)
	log.Println("Calling url", url)
	resp, err := h.ExecuteGet(url)
	//resp, err := http.Get(url)

	if err != nil {
		log.Fatalln(err)
	}

	fmt.Println(resp)
	//Invoke HTTP host:port/api/healthcheck
	//Get Result - Check Status
}

func TestIntMinTableDriven(t *testing.T) {
	var tests = []struct {
		a, b int
		want int
	}{
		{0, 1, 0},
	}

	for _, tt := range tests {
		fmt.Println(tt)
	}
}
