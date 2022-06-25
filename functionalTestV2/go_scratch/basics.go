package main

import (
	"fmt"
	"math"
	"math/rand"
	"runtime"
)

var (
	name     = "Prince"
	age      = 32
	location = "Don"
)

const PI = 3.14

const (
	statusOK = 200
)

func runningOs() {
	fmt.Println("Go is running on..")
	switch os := runtime.GOOS; os {
	case "darwin":
		fmt.Println("OS X.")
	case "linux":
		fmt.Println("Linux.")
	default:
		fmt.Printf("%s.\n", os)
	}
}

//In GO, a name is exported if it begins with a capital letter. For example, Pi Exported

//Short assignment := (Not available outside a function)
func main() {
	name1, location1 := "Prince Oberoi", "Don"
	age1 := 33
	fmt.Printf("%s (%d) of %s\n", name1, age1, location1)

	//Variable can contain any type of function
	action := func() {}

	action()

	fmt.Println("My favourite number is", rand.Intn(10))

	a, b := swap("hello", "world")
	fmt.Println(a, b)
	fmt.Println(split(18))

	for i := 0; i < 3; i++ {
		fmt.Printf("%d", i)
	}

	//If with a short statement
	if v := math.Pow(2, 2); v < 8 {
		println("Power: ", v)
	}

	println(sqrt(5))
	runningOs()

	i, j := 42, 2701
	p := &i
	fmt.Println(*p)
	*p = 21
	fmt.Println(j)
}

func swap(x, y string) (string, string) {
	return y, x
}

//In Go, only constants are immutable. Arguments are passed by value.
func split(sum int) (x, y int) { //Naming return values
	x = sum * 4 / 9
	y = sum - x
	return
}

func sqrt(x float64) float64 {
	defer fmt.Println("Calculated Sqrt")
	z := 1.0
	//println("Expected Answer: ", math.Sqrt(x))
	for i := 0; i < 20; i++ {
		if math.Abs(x-z*z) > 0.0001 {
			z -= (z*z - x) / (2 * z)
			//println("Guess: ", z)
		}
	}
	return z
}
