package main

import (
	"fmt"
	"math"
)

type ErrNegativeSqrt float64

func (e ErrNegativeSqrt) Error() string {
	return fmt.Sprintf("cannot Sqrt negative number: %f", float64(e))
}

func sqrt(x float64) (float64, error) {
	//defer fmt.Println("Calculated Sqrt")

	if x < 0 {
		return 0, ErrNegativeSqrt(x)
	}

	z := 1.0
	//println("Expected Answer: ", math.Sqrt(x))
	for i := 0; i < 20; i++ {
		if math.Abs(x-z*z) > 0.0001 {
			z -= (z*z - x) / (2 * z)
			//println("Guess: ", z)
		}
	}
	return z, nil
}

func main() {
	fmt.Println(sqrt(2))
	fmt.Println(sqrt(-2))
}
