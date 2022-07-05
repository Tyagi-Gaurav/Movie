package main

import (
	"fmt"
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/Movie/functionalTest/util"
	"github.com/stretchr/testify/require"
)

func TestRequestId(t *testing.T) {
	appConfig := config.Configs["movie"]

	var h = &ext.WebClient{}
	resp, err := h.ExecuteGet(appConfig.StatusUrl())
	util.PanicOnError(err)

	require.Equal(t, 200, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", 200, resp.StatusCode))
	require.True(t, resp.Header["Requestid"] != nil, "request Id header not found")
}

func TestMetrics(t *testing.T) {
	appConfig := config.Configs["movie"]

	var h = &ext.WebClient{}
	resp, err := h.ExecuteGet(appConfig.MetricsUrl())
	util.PanicOnError(err)

	require.Equal(t, 200, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", 200, resp.StatusCode))
	require.True(t, resp.Header["Requestid"] != nil, "request Id header not found")
}
