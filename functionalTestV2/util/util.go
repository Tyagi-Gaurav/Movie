package util

import (
	"math/rand"
	"time"
)

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
