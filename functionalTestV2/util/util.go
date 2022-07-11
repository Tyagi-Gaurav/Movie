package util

import (
	"fmt"
	"math/rand"
	"net/http"
	"testing"
	"time"

	"github.com/stretchr/testify/require"
)

type URLResolver func(string) string

func init() {
	rand.Seed(time.Now().UnixNano())
}

func RandomString(minSize int) string {
	result := ""
	charset := "abcdefghijklmnopqrstuvwxyz"
	for i := 0; i < minSize; i++ {
		result = result + string(charset[rand.Intn(len(charset))])
	}

	return result
}

func PanicOnError(err error) {
	if err != nil {
		panic(err)
	}
}

func ExpectStatus(t *testing.T, resp *http.Response, expectedStatusCode int) {
	require.Equal(t, expectedStatusCode, resp.StatusCode, fmt.Sprintf("Failed. expected: %d, actual: %d", expectedStatusCode,
		resp.StatusCode))
}
