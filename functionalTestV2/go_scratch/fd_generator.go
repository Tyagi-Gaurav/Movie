package main

import "fmt"

func main() {
	roi := 5.35
	rdmaturity := 222000.0
	principal := 0.0
	total := 0.0

	for i := 1; i <= 10; i++ {
		principal = rdmaturity + total

		interest := (principal * roi) / 100
		total = principal + interest

		fmt.Printf("End of Year: %d, Principal: %9.2f, Interest: %9.2f, Total : %9.2f\n", i, principal, interest, total)
	}

	fmt.Printf("Final Principal: %9.2f", total)
}
