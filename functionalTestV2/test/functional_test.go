package main

import (
	"fmt"
	"testing"
)

func TestIntMinBasic(t *testing.T) {

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
