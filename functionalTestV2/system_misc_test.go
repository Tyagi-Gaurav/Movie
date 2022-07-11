package main

import (
	"testing"

	"github.com/Movie/functionalTest/config"
	"github.com/Movie/functionalTest/ext"
	"github.com/Movie/functionalTest/util"
	"github.com/stretchr/testify/require"
)

func TestRequestId(t *testing.T) {
	appConfig := config.Configs["contentUpload"]

	var h = &ext.WebClient{}
	resp, err := h.ExecuteGet(appConfig.StatusUrl())
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 200)
	require.True(t, resp.Header["Requestid"] != nil, "request Id header not found")
}

func TestMetrics(t *testing.T) {
	appConfig := config.Configs["contentUpload"]

	var h = &ext.WebClient{}
	resp, err := h.ExecuteGet(appConfig.MetricsUrl())
	util.PanicOnError(err)
	util.ExpectStatus(t, resp, 200)
	require.True(t, resp.Header["Requestid"] != nil, "request Id header not found")
}
